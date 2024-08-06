package at.oekosol.notificationservice.service;

import at.oekosol.sharedlibrary.events.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class DashboardNotificationService {

    public Mono<Void> sendDashboardNotification(UserCreatedEvent event) {
        // Simulate dashboard notification logic
        return Mono.fromRunnable(() -> {
            log.info("Dashboard notification sent to user: {}", event.email());
        });
    }
}
