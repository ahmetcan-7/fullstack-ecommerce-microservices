package com.ahmetcan7.userservice.service;

import com.ahmetcan7.amqp.RabbitMQMessageProducer;
import com.ahmetcan7.amqp.dto.EmailRequest;
import com.ahmetcan7.userservice.dto.*;
import com.ahmetcan7.userservice.enumeration.Role;
import com.ahmetcan7.userservice.exception.*;
import com.ahmetcan7.userservice.model.User;
import com.ahmetcan7.userservice.model.UserPrincipal;
import com.ahmetcan7.userservice.repository.UserRepository;
import com.ahmetcan7.userservice.util.JWTTokenProvider;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import static com.ahmetcan7.userservice.constant.FileConstant.*;
import static com.ahmetcan7.userservice.constant.SecurityConstant.AUTHORITIES;
import static com.ahmetcan7.userservice.constant.UserConstant.*;
import static com.ahmetcan7.userservice.enumeration.Role.ROLE_USER;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.MediaType.*;

@Service
@RequiredArgsConstructor
@Transactional
@Qualifier("userDetailsService")
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final LoginAttemptService loginAttemptService;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;
    private final JWTTokenProvider jwtTokenProvider;
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = findUserByEmail(email);
        validateLoginAttempt(user);
        user.setLastLoginDateDisplay(user.getLastLoginDate());
        user.setLastLoginDate(new Date());
        userRepository.save(user);
        UserPrincipal userPrincipal = new UserPrincipal(user);
        log.info(FOUND_USER_BY_EMAIL + email);
        return userPrincipal;
    }

    public User register(RegisterUserRequest user)  {
        validateEmail( user.getEmail());
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setJoinDate(new Date());
        newUser.setPassword(encodePassword(user.getPassword()));
        newUser.setActive(true);
        newUser.setNotLocked(true);
        newUser.setRole(ROLE_USER.name());
        newUser.setAuthorities(ROLE_USER.getAuthorities());
        newUser.setProfileImageUrl(getTemporaryProfileImageUrl(user.getEmail()));
        userRepository.save(newUser);
        return newUser;
    }

    public User addNewUser(AddUserRequest user)  {
        validateEmail( user.getEmail());
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setJoinDate(new Date());
        newUser.setPassword(encodePassword(user.getPassword()));
        newUser.setActive(user.isActive());
        newUser.setNotLocked(user.isNonLocked());
        newUser.setRole(getRoleEnumName(user.getRole()).name());
        newUser.setAuthorities(getRoleEnumName(user.getRole()).getAuthorities());
        newUser.setProfileImageUrl(getTemporaryProfileImageUrl(user.getEmail()));
        userRepository.save(newUser);
        return newUser;
    }

    public UserDto validateToken(String token) {
        DecodedJWT decodedJWT =  jwtTokenProvider.decodeToken(token);
        String userId = decodedJWT.getClaim("userId").asString();
        List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(decodedJWT);

        return new UserDto(userId,authorities);
    }

    public MeDto getMe(String token) {
        DecodedJWT decodedJWT =  jwtTokenProvider.decodeToken(token);
        List<String> roles = decodedJWT.getClaim(AUTHORITIES).asList(String.class);
        String userId = decodedJWT.getClaim("userId").asString();
        String firstName = decodedJWT.getClaim("firstName").asString();
        String lastName = decodedJWT.getClaim("lastName").asString();
        String email = decodedJWT.getClaim("email").asString();

        return MeDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .userId(userId)
                .roles(roles)
                .build();

    }

    public User updateUser(UpdateUserRequest user){
        User currentUser = findUserByEmail(user.getEmail());
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setActive(user.isActive());
        currentUser.setNotLocked(user.isNonLocked());
        currentUser.setRole(getRoleEnumName(user.getRole()).name());
        currentUser.setAuthorities(getRoleEnumName(user.getRole()).getAuthorities());
        userRepository.save(currentUser);
        saveProfileImage(currentUser, user.getProfileImage());
        return currentUser;
    }

    public void deleteUser(String email) {
        User user = findUserByEmail(email);
        Path userFolder = Paths.get(USER_FOLDER + user.getEmail()).toAbsolutePath().normalize();
        try {
            FileUtils.deleteDirectory(new File(userFolder.toString()));
        } catch (IOException e) {
            throw new FileDeleteException(FILE_DELETE_ERROR);
        }
        userRepository.deleteById(user.getId());
    }

    public void resetPassword(String email){
        User user = findUserByEmail(email);
        if (user == null) {
            throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL + email);
        }
        String password = generatePassword();
        user.setPassword(encodePassword(password));
        userRepository.save(user);
        log.info("New user password: " + password);
        sendEmail(user, password);
    }

    private void sendEmail(User user, String password) {
        String text = "Hello " + user.getFirstName() + ", \n \n Your new account password is: " + password + "\n \n The Support Team";
        String subject = "Ahmet Can, AC - New Password";
        EmailRequest emailRequest = new EmailRequest(text, user.getEmail(),subject);
        rabbitMQMessageProducer.publish(
                emailRequest,
                "notification.exchange",
                "send.email.routing-key"
        );
    }

    public User updateProfileImage(String email,MultipartFile profileImage){
        User user = findUserByEmail(email);
        saveProfileImage(user, profileImage);
        return user;
    }

    private void validateEmail(String email) {
        Optional<User> userByNewEmail = userRepository.findUserByEmail(email);

        if (userByNewEmail.isPresent()) {
            throw new EmailExistException(EMAIL_ALREADY_EXISTS);
        }
    }

    private void validateLoginAttempt(User user) {
        if(user.isNotLocked()) {
            if(loginAttemptService.hasExceededMaxAttempts(user.getEmail())) {
                user.setNotLocked(false);
            } else {
                user.setNotLocked(true);
            }
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getEmail());
        }
    }

    private void saveProfileImage(User user, MultipartFile profileImage) {
        if(!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(profileImage.getContentType())) {
            throw new NotAnImageFileException(profileImage.getOriginalFilename() + NOT_AN_IMAGE_FILE);
        }
        try {
            Path userFolder = Paths.get(USER_FOLDER + user.getEmail()).toAbsolutePath().normalize();
            if(!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
                log.info(DIRECTORY_CREATED + userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + user.getEmail() + DOT + JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(), userFolder.resolve(user.getEmail() + DOT + JPG_EXTENSION), REPLACE_EXISTING);
            user.setProfileImageUrl(setProfileImageUrl(user.getEmail()));
            userRepository.save(user);
            log.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
        }catch (IOException e){
            throw new FileUploadException(FILE_UPLOAD_ERROR);
        }

    }

    private String setProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_IMAGE_PATH + username + FORWARD_SLASH
                + username + DOT + JPG_EXTENSION).toUriString();
    }

    private String getTemporaryProfileImageUrl(String email) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + email).toUriString();
    }
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(()-> new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL));
    }
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private Role getRoleEnumName(String role) {
        return Role.valueOf(role.toUpperCase());
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    public User getUserById(UUID id){
        return userRepository.getById(id);
    }

}
