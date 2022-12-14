package com.ahmetcan7.fileservice.constant;

public class FileConstant {
    public static final String IMAGE_FOLDER = System.getProperty("user.home") + "/ecommerce-product-images/";
    public static final String DIRECTORY_CREATED = "Created directory for: ";
    public static final String FILE_SAVED_IN_FILE_SYSTEM = "Saved file in file system by name: ";
    public static final String FORWARD_SLASH = "/";
    public static final String NOT_AN_IMAGE_FILE = " is not an image file. Please upload an image file";
    public static final String FILE_UPLOAD_ERROR = "Something went wrong when update file";

    public static final String FILE_EXIST_ERROR = "File is already exist with the same name, please change file name!!";
    public static final String FILE_SERVER = "http://localhost:8889/file/image/";
}
