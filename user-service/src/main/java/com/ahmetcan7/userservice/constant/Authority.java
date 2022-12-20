package com.ahmetcan7.userservice.constant;

public class Authority {
    public static final String[] USER_AUTHORITIES = { "ROLE_USER" };
    public static final String[] HR_AUTHORITIES = { "ROLE_USER", "ROLE_HR" };
    public static final String[] MANAGER_AUTHORITIES = { "ROLE_USER", "ROLE_HR","ROLE_MANAGER" };
    public static final String[] ADMIN_AUTHORITIES = { "ROLE_USER", "ROLE_HR","ROLE_MANAGER","ROLE_ADMIN" };
    public static final String[] SUPER_ADMIN_AUTHORITIES = { "ROLE_USER", "ROLE_HR","ROLE_MANAGER","ROLE_ADMIN","ROLE_SUPER_ADMIN" };
}
