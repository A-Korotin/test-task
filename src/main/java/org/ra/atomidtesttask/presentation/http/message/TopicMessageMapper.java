package org.ra.atomidtesttask.presentation.http.message;

import org.ra.atomidtesttask.domain.topic.Message;
import org.ra.atomidtesttask.presentation.http.EntityMapper;
import org.ra.atomidtesttask.presentation.http.message.dto.CreateMessageRequest;
import org.ra.atomidtesttask.presentation.http.message.dto.TopicMessageResponse;
import org.springframework.stereotype.Component;

@Component
public class TopicMessageMapper implements EntityMapper<Message, CreateMessageRequest, TopicMessageResponse> {

    @Override
    public Message fromDto(CreateMessageRequest dto) {
        Message message = new Message();
        message.setText(dto.getText());
        return message;
    }

    @Override
    public TopicMessageResponse toDto(Message entity) {
        return TopicMessageResponse.builder()
                .id(entity.getId())
                .text(entity.getText())
                .authorUsername(entity.getUser().getUsername())
                .publishedAt(entity.getPublishedAt())
                .build();
    }
}
