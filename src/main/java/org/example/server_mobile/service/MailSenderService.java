package org.example.server_mobile.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.example.server_mobile.dto.request.MailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MailSenderService {
    JavaMailSender mailSender;

    @NonFinal
    @Value("${spring.mail.username}")
    protected String mailFrom;

    public void send(MailRequest mailRequest) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailFrom);
        mailMessage.setTo(mailRequest.getEmail());
        mailMessage.setSubject("Activation code");
        mailMessage.setText(mailRequest.getMessage());

        mailSender.send(mailMessage);
        log.info("Mail sent to {}", mailRequest.getEmail());
        log.info("Mail sent success {}", mailRequest.getMessage());
    }
    public void send(String mailRequest) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailFrom);
        mailMessage.setTo(mailRequest);
        mailMessage.setSubject("New pass");
        mailMessage.setText("0123456789");

        mailSender.send(mailMessage);

    }

}
