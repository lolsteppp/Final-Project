package com.didarovamedina.university.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class DidarovaMedinaEmailService {

    private final JavaMailSender mailSender;

    @Async
    public CompletableFuture<Void> sendEnrollmentEmail(String to, String name, String course) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject("Enrollment Confirmation");
            msg.setText("Hello " + name + ", enrolled in: " + course);
            mailSender.send(msg);
            log.info("Enrollment email sent to {}", to);
        } catch (Exception e) {
            log.error("Email error: {}", e.getMessage());
        }
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Void> sendGradeNotification(String to, Double grade) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject("Grade Updated");
            msg.setText("Your grade: " + grade);
            mailSender.send(msg);
            log.info("Grade email sent to {}", to);
        } catch (Exception e) {
            log.error("Grade email error: {}", e.getMessage());
        }
        return CompletableFuture.completedFuture(null);
    }
}