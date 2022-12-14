package com.ahmetcan7.fileservice.service;

import com.ahmetcan7.fileservice.exception.FileExistSameNameException;
import com.ahmetcan7.fileservice.exception.FileUploadException;
import com.ahmetcan7.fileservice.exception.NotAnImageFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.ahmetcan7.fileservice.constant.FileConstant.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.MediaType.*;

@Service
@Slf4j
public class FileService {

    public String saveImage(MultipartFile image) {
        if(!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(image.getContentType())) {
            throw new NotAnImageFileException(image.getOriginalFilename() + NOT_AN_IMAGE_FILE);
        }
        try {
            Path imageFolder = Paths.get(IMAGE_FOLDER).toAbsolutePath().normalize();
            Path imageFolderAndFile = Paths.get(IMAGE_FOLDER+FORWARD_SLASH+image.getOriginalFilename()).toAbsolutePath().normalize();
            if(!Files.exists(imageFolderAndFile)) {
                Files.createDirectories(imageFolder);
                log.info(DIRECTORY_CREATED + imageFolder);
            }else{
                throw new FileExistSameNameException(FILE_EXIST_ERROR);
            }
            Files.copy(image.getInputStream(), imageFolder.resolve(image.getOriginalFilename()), REPLACE_EXISTING);
            log.info(FILE_SAVED_IN_FILE_SYSTEM + image.getOriginalFilename());
        }catch (IOException e){
            throw new FileUploadException(FILE_UPLOAD_ERROR);
        }

        return FILE_SERVER + image.getOriginalFilename();
    }

}
