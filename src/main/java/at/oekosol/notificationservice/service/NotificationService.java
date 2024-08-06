package at.oekosol.notificationservice.service;

import at.oekosol.notificationservice.util.EmailTemplateProcessor;
import at.oekosol.sharedlibrary.events.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final EmailService emailService;
    private final DashboardNotificationService dashboardNotificationService;
    private final EmailTemplateProcessor emailTemplateProcessor;

    @KafkaListener(topics = "user-created", groupId = "notification-group")
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        log.info("Received User Created Event: {}", event);

        // Send email notification
        Mono.just(event)
                .flatMap(this::sendEmailNotification)
                .doOnError(e -> log.error("Error sending email notification", e))
                .subscribe();

        // Send dashboard notification
        Mono.just(event)
                .flatMap(dashboardNotificationService::sendDashboardNotification)
                .doOnError(e -> log.error("Error sending dashboard notification", e))
                .subscribe();
    }

    private Mono<Void> sendEmailNotification(UserCreatedEvent event) {
        Map<String, Object> model = new HashMap<>();
        model.put("email", event.email());
        model.put("firstName", event.firstName());
        model.put("lastName", event.lastName());
        model.put("customAttribute", event.attributes());
        String emailContent = emailTemplateProcessor.processTemplate("templates/welcome-email-template.html", model);
        return emailService.sendEmail(event.email(), "Welcome to Oekosol", emailContent);
    }
}
