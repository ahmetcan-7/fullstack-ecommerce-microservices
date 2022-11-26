package com.ahmetcan7.userservice.service;

import com.ahmetcan7.userservice.dto.AddUserRequest;
import com.ahmetcan7.userservice.dto.RegisterUserRequest;
import com.ahmetcan7.userservice.dto.UpdateProfileImageRequest;
import com.ahmetcan7.userservice.dto.UpdateUserRequest;
import com.ahmetcan7.userservice.enumeration.Role;
import com.ahmetcan7.userservice.exception.*;
import com.ahmetcan7.userservice.model.User;
import com.ahmetcan7.userservice.model.UserPrincipal;
import com.ahmetcan7.userservice.repository.UserRepository;
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

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;

import static com.ahmetcan7.userservice.constant.FileConstant.*;
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
    private final EmailService emailService;
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            log.error(NO_USER_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
        } else {
            validateLoginAttempt(user);
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            log.info(FOUND_USER_BY_USERNAME + username);
            return userPrincipal;
        }
    }

    public User register(RegisterUserRequest user)  {
        validateNewUsernameAndEmail(user.getUsername(), user.getEmail());
        User newUser = new User();
        newUser.setUserId(generateUserId());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setJoinDate(new Date());
        newUser.setPassword(encodePassword(user.getPassword()));
        newUser.setActive(true);
        newUser.setNotLocked(true);
        newUser.setRole(ROLE_USER.name());
        newUser.setAuthorities(ROLE_USER.getAuthorities());
        newUser.setProfileImageUrl(getTemporaryProfileImageUrl(user.getUsername()));
        userRepository.save(newUser);
        // emailService.sendNewPasswordEmail(user.getFirstName(), user.getPassword(), user.getEmail());
        return newUser;
    }

    public User addNewUser(AddUserRequest user)  {
        validateNewUsernameAndEmail(user.getUsername(), user.getEmail());
        User newUser = new User();
        newUser.setUserId(generateUserId());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setJoinDate(new Date());
        newUser.setPassword(encodePassword(user.getPassword()));
        newUser.setActive(user.isActive());
        newUser.setNotLocked(user.isNonLocked());
        newUser.setRole(getRoleEnumName(user.getRole()).name());
        newUser.setAuthorities(getRoleEnumName(user.getRole()).getAuthorities());
        newUser.setProfileImageUrl(getTemporaryProfileImageUrl(user.getUsername()));
        userRepository.save(newUser);
        // emailService.sendNewPasswordEmail(user.getFirstName(), user.getPassword(), user.getEmail());
        return newUser;
    }

    public User updateUser(UpdateUserRequest user){
        User currentUser = validateNewUsernameAndEmail(user.getCurrentUsername(), user.getUsername(), user.getEmail());
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setUsername(user.getUsername());
        currentUser.setEmail(user.getEmail());
        currentUser.setActive(user.isActive());
        currentUser.setNotLocked(user.isNonLocked());
        currentUser.setRole(getRoleEnumName(user.getRole()).name());
        currentUser.setAuthorities(getRoleEnumName(user.getRole()).getAuthorities());
        userRepository.save(currentUser);
        saveProfileImage(currentUser, user.getProfileImage());
        return currentUser;
    }

    public void deleteUser(String username) {
        User user = userRepository.findUserByUsername(username);
        Path userFolder = Paths.get(USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
        try {
            FileUtils.deleteDirectory(new File(userFolder.toString()));
        } catch (IOException e) {
            throw new FileDeleteException(FILE_DELETE_ERROR);
        }
        userRepository.deleteById(user.getId());
    }

    public void resetPassword(String email) throws MessagingException, EmailNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL + email);
        }
        String password = generatePassword();
        user.setPassword(encodePassword(password));
        userRepository.save(user);
        log.info("New user password: " + password);
        // emailService.sendNewPasswordEmail(user.getFirstName(), password, user.getEmail());
    }

    public User updateProfileImage(UpdateProfileImageRequest updateProfileImageRequest){
        User user = validateNewUsernameAndEmail(updateProfileImageRequest.getUsername(), null, null);
        saveProfileImage(user, updateProfileImageRequest.getProfileImage());
        return user;
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) {
        User userByNewUsername = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(newEmail);

        User currentUser = findUserByUsername(currentUsername);
        if (currentUser == null) {
          throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
        }
        if (userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
          throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
        }
        if (userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
          throw new EmailExistException(EMAIL_ALREADY_EXISTS);
        }
        return currentUser;
    }

    private User validateNewUsernameAndEmail(String newUsername, String newEmail) {
        User userByNewUsername = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(newEmail);
        if (userByNewUsername != null) {
            throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
        }
        if (userByNewEmail != null) {
            throw new EmailExistException(EMAIL_ALREADY_EXISTS);
        }
        return null;
    }

    private void validateLoginAttempt(User user) {
        if(user.isNotLocked()) {
            if(loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
                user.setNotLocked(false);
            } else {
                user.setNotLocked(true);
            }
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }

    private void saveProfileImage(User user, MultipartFile profileImage) {
        if(!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(profileImage.getContentType())) {
            throw new NotAnImageFileException(profileImage.getOriginalFilename() + NOT_AN_IMAGE_FILE);
        }
        try {
            Path userFolder = Paths.get(USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
            if(!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
                log.info(DIRECTORY_CREATED + userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + user.getUsername() + DOT + JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(), userFolder.resolve(user.getUsername() + DOT + JPG_EXTENSION), REPLACE_EXISTING);
            user.setProfileImageUrl(setProfileImageUrl(user.getUsername()));
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

    private String getTemporaryProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username).toUriString();
    }
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }
    private Role getRoleEnumName(String role) {
        return Role.valueOf(role.toUpperCase());
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

}
