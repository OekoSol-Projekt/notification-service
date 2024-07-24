package at.oekosol.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmailService {

    private JavaMailSender mailSender;

    public Mono<Void> sendExistingUserInvite(String email, Long companyId) {
        return Mono.fromRunnable(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Invitation to Join Company");
            message.setText("You have been invited to join the company. Click here to join: <link>");
            mailSender.send(message);
        });
    }

    public Mono<Void> sendNewUserInvite(String email, Long companyId) {
        return Mono.fromRunnable(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Invitation to Create Account and Join Company");
            message.setText("You have been invited to join the company. Click here to create an account and join: <link>");
            mailSender.send(message);
        });
    }
}
