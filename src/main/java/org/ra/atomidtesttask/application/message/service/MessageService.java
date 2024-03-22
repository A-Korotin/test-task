package org.ra.atomidtesttask.application.message.service;

import org.ra.atomidtesttask.application.BaseService;
import org.ra.atomidtesttask.application.PageableService;
import org.ra.atomidtesttask.application.message.dto.MessageUpdateDto;
import org.ra.atomidtesttask.domain.topic.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MessageService extends BaseService<Message, UUID>, PageableService<Message> {
    Page<Message> findAllByTopicId(UUID topicId, Pageable pageable);
    Message updateById(UUID id, MessageUpdateDto dto);
}
