package org.ra.atomidtesttask.application.message.security;

import lombok.RequiredArgsConstructor;
import org.ra.atomidtesttask.application.SecurityService;
import org.ra.atomidtesttask.application.message.service.MessageService;
import org.ra.atomidtesttask.domain.topic.Message;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("messageSecurity")
@RequiredArgsConstructor
public class MessageSecurityService implements SecurityService<UUID> {
    private final MessageService messageService;

    @Override
    public boolean userHasAccessToView(UUID entityId, String username) {
        return true;
    }

    @Override
    public boolean userHasAccessToModify(UUID entityId, String username) {
        Message message = messageService.findById(entityId);
        return message.getUser().getUsername().equals(username);
    }
}
