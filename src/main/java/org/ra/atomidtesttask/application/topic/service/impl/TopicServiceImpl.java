package org.ra.atomidtesttask.application.topic.service.impl;

import lombok.RequiredArgsConstructor;
import org.ra.atomidtesttask.application.exception.NotFoundException;
import org.ra.atomidtesttask.application.message.service.MessageService;
import org.ra.atomidtesttask.application.topic.service.TopicService;
import org.ra.atomidtesttask.application.topic.dto.TopicUpdateDto;
import org.ra.atomidtesttask.domain.topic.Message;
import org.ra.atomidtesttask.domain.topic.Topic;
import org.ra.atomidtesttask.infrastructure.persistence.topic.TopicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
    private final TopicRepository repository;
    private final MessageService messageService;

    @Override
    public Topic findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Topic could not be found"));
    }

    @Override
    public Page<Topic> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Topic save(Topic entity) {
        return repository.save(entity);
    }

    @Override
    public Topic saveWithInitialMessage(Topic topic, Message initial) {
        topic = repository.save(topic);
        initial.setTopic(topic);
        messageService.save(initial);
        return topic;
    }

    @Override
    public Topic updateById(UUID id, TopicUpdateDto dto) {
        Topic existing = repository.findById(id).orElseThrow(() -> new NotFoundException("Topic could not be found"));
        existing.setName(dto.getName());
        return repository.save(existing);
    }

    @Override
    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Topic could not be found");
        }

        repository.deleteById(id);

    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    @Override
    public Message attachToTopic(UUID topicId, Message message) {
        Topic existing = repository.findById(topicId).orElseThrow(() -> new NotFoundException("Topic could not be found"));
        message.setTopic(existing);
        return messageService.save(message);
    }
}
