package com.ahmetcan7.userservice.constant;

public class EmailConstant {
    public static final String SIMPLE_MAIL_TRANSFER_PROTOCOL = "smtps";
    public static final String USERNAME = "sender username";
    public static final String PASSWORD = "sender password";
    public static final String FROM_EMAIL = "support@ahmetcan.com";
    public static final String CC_EMAIL = "";
    public static final String EMAIL_SUBJECT = "Ahmet Can, AC - New Password";
    public static final String GMAIL_SMTP_SERVER = "smtp.gmail.com";
    public static final String SMTP_HOST = "mail.smtp.host";
    public static final String SMTP_AUTH = "mail.smtp.auth";
    public static final String SMTP_PORT = "mail.smtp.port";
    public static final int DEFAULT_PORT = 465;
    public static final String SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required";

    public static final String EMAIL_SENDING_ERROR = "Something went wrong when sending email";
}
