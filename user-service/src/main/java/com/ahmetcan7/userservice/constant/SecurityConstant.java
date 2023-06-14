package com.ahmetcan7.userservice.constant;

public class SecurityConstant {
    public static final long EXPIRATION_TIME =(24*60*60*1000)*2;// 2 days expressed in milliseconds

    public static final long REFRESH_TOKEN_EXP =(24*60*60*1000)*24; // 24 days expressed in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String Company_LLC = "Ahmet Can, LLC";
    public static final String Company_ADMINISTRATION = "E Commerce Portal";
    public static final String AUTHORITIES = "authorities";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = {"/user/login", "/user/register", "/user/image/**", "/user/resetpassword/**"
            ,"/user/token/refresh"};
    public static final String[] PUBLIC_URLS_WITH_ONLY_MAIN = {"/user/image", "/user/resetpassword"};
}
