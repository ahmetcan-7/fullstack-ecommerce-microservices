package com.ahmetcan7.userservice.controller;

import static com.ahmetcan7.userservice.constant.FileConstant.*;
import static com.ahmetcan7.userservice.constant.RequestConstant.*;

import com.ahmetcan7.userservice.dto.*;
import com.ahmetcan7.userservice.exception.HttpResponse;
import com.ahmetcan7.userservice.model.User;
import com.ahmetcan7.userservice.model.UserPrincipal;
import com.ahmetcan7.userservice.service.UserService;
import com.ahmetcan7.userservice.util.AuthenticationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterUserRequest user)  {
        userService.register(user);
        return ResponseEntity.ok(REGISTER_RES);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginUserRequest user) {
        authenticationHelper.authenticate(user.getEmail(), user.getPassword());
        User loginUser = userService.findUserByEmail(user.getEmail());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = authenticationHelper.getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
    }

    @PostMapping("/validateToken")
    public ResponseEntity<UserDto> validateToken(@RequestParam String token) {
        return ResponseEntity.ok(userService.validateToken(token));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody AddUserRequest user)  {
        userService.addNewUser(user);
        return ResponseEntity.ok(ADD_USER_RES);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserRequest user)  {
        userService.updateUser(user);
        return ResponseEntity.ok(UPDATE_USER_RES);
    }

    @GetMapping("/find/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @GetMapping("/resetpassword/{email}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable String email) {
        userService.resetPassword(email);
        return new ResponseEntity<>(new HttpResponse(OK.value(), OK, OK.getReasonPhrase().toUpperCase(),
                RESET_PASSWORD_RES + email), OK);
    }

    @DeleteMapping("/delete/{email}")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable String email) throws IOException {
        userService.deleteUser(email);
        return new ResponseEntity<>(new HttpResponse(OK.value(), OK, OK.getReasonPhrase().toUpperCase(),
                DELETE_USER_RES), OK);
    }

    @PostMapping("/updateProfileImage")
    public ResponseEntity<User> updateProfileImage(@RequestParam String email,
                                                   @RequestParam MultipartFile profileImage) {
        return ResponseEntity.ok(userService.updateProfileImage(email,profileImage));
    }

    @GetMapping(path = "/image/{email}/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable String email, @PathVariable String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER + email + FORWARD_SLASH + fileName));
    }

    @GetMapping(path = "/image/profile/{email}", produces = IMAGE_JPEG_VALUE)
    public byte[] getTempProfileImage(@PathVariable String email) throws IOException {
        URL url = new URL(TEMP_PROFILE_IMAGE_BASE_URL + email);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = url.openStream()) {
            int bytesRead;
            byte[] chunk = new byte[1024];
            while((bytesRead = inputStream.read(chunk)) > 0) {
                byteArrayOutputStream.write(chunk, 0, bytesRead);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

}
