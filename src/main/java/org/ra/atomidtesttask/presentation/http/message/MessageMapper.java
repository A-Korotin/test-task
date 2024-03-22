package org.ra.atomidtesttask.presentation.http.message;

import org.ra.atomidtesttask.application.message.dto.MessageUpdateDto;
import org.ra.atomidtesttask.domain.topic.Message;
import org.ra.atomidtesttask.presentation.http.EntityMapper;
import org.ra.atomidtesttask.presentation.http.message.dto.CreateMessageRequest;
import org.ra.atomidtesttask.presentation.http.message.dto.MessageResponse;
import org.ra.atomidtesttask.presentation.http.message.dto.UpdateMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper implements EntityMapper<Message, CreateMessageRequest, MessageResponse> {

    @Override
    public Message fromDto(CreateMessageRequest dto) {
        Message message = new Message();
        message.setText(dto.getText());
        return message;
    }

    @Override
    public MessageResponse toDto(Message entity) {
        return MessageResponse.builder()
                .id(entity.getId())
                .topicId(entity.getTopic().getId())
                .authorUsername(entity.getUser().getUsername())
                .publishedAt(entity.getPublishedAt())
                .text(entity.getText())
                .build();
    }

    public MessageUpdateDto toUpdateDto(UpdateMessageRequest request) {
        return MessageUpdateDto.builder()
                .text(request.getText())
                .build();
    }
}
