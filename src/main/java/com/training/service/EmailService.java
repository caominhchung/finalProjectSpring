package com.training.service;

import javax.mail.MessagingException;

import com.training.dto.MailDto;


public interface EmailService {
    public void sendEmail(MailDto mail,String htmlTemplate) throws MessagingException;
}
