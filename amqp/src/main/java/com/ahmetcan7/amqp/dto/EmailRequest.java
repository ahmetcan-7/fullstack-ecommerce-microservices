package com.ahmetcan7.amqp.dto;

public class EmailRequest {
    private String text;
    private String email;
    private String subject;

    public EmailRequest() {
    }

    public EmailRequest(String text, String email, String subject) {
        this.text = text;
        this.email = email;
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
