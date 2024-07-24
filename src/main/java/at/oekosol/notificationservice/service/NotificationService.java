package at.oekosol.notificationservice.service;

import at.oekosol.sharedlibrary.events.UserInvitationEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private EmailService emailService;

    @KafkaListener(topics = "UserInvitationTopic", groupId = "group_id")
    public void handleUserInvitationEvent(UserInvitationEvent event) {
        for (String email : event.getExistingUsers()) {
            emailService.sendExistingUserInvite(email, event.getCompanyId())
                    .subscribe();
            // Also send dashboard notification (implementation depends on your setup)
        }
        for (String email : event.getNewUsers()) {
            emailService.sendNewUserInvite(email, event.getCompanyId())
                    .subscribe();
        }
    }

}
