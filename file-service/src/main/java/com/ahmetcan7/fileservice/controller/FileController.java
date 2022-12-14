package com.ahmetcan7.fileservice.controller;

import com.ahmetcan7.fileservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.ahmetcan7.fileservice.constant.FileConstant.FORWARD_SLASH;
import static com.ahmetcan7.fileservice.constant.FileConstant.IMAGE_FOLDER;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    @PostMapping("/saveImage")
    public ResponseEntity<String> saveImage(@RequestParam MultipartFile image) {
        return ResponseEntity.ok(fileService.saveImage(image));
    }

    @GetMapping(path = "/image/{imageName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable String imageName) throws IOException {
        return Files.readAllBytes(Paths.get(IMAGE_FOLDER  + FORWARD_SLASH + imageName));
    }
}
