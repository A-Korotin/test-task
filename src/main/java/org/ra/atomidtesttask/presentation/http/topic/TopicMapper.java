package org.ra.atomidtesttask.presentation.http.topic;

import lombok.RequiredArgsConstructor;
import org.ra.atomidtesttask.application.topic.dto.TopicUpdateDto;
import org.ra.atomidtesttask.domain.topic.Topic;
import org.ra.atomidtesttask.presentation.http.EntityMapper;
import org.ra.atomidtesttask.presentation.http.topic.dto.CreateTopicRequest;
import org.ra.atomidtesttask.presentation.http.topic.dto.TopicResponse;
import org.ra.atomidtesttask.presentation.http.topic.dto.UpdateTopicRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TopicMapper implements EntityMapper<Topic, CreateTopicRequest, TopicResponse> {

    @Override
    public Topic fromDto(CreateTopicRequest dto) {
        Topic topic = new Topic();
        topic.setName(dto.getName());
        return topic;
    }

    @Override
    public TopicResponse toDto(Topic entity) {
        return TopicResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public TopicUpdateDto toUpdateDto(UpdateTopicRequest request) {
        return TopicUpdateDto.builder()
                .name(request.getName())
                .build();
    }
}
