package org.ra.atomidtesttask.application.topic.service;

import org.ra.atomidtesttask.application.BaseService;
import org.ra.atomidtesttask.application.PageableService;
import org.ra.atomidtesttask.application.topic.dto.TopicUpdateDto;
import org.ra.atomidtesttask.domain.topic.Message;
import org.ra.atomidtesttask.domain.topic.Topic;

import java.util.UUID;

public interface TopicService extends BaseService<Topic, UUID>, PageableService<Topic> {
    Topic updateById(UUID id, TopicUpdateDto dto);
    Message attachToTopic(UUID topicId, Message message);
    Topic saveWithInitialMessage(Topic topic, Message initial);
}
