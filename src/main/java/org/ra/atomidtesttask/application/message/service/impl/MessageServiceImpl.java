package org.ra.atomidtesttask.application.message.service.impl;

import lombok.RequiredArgsConstructor;
import org.ra.atomidtesttask.application.exception.NotFoundException;
import org.ra.atomidtesttask.application.message.dto.MessageUpdateDto;
import org.ra.atomidtesttask.application.message.service.MessageService;
import org.ra.atomidtesttask.application.topic.service.TopicService;
import org.ra.atomidtesttask.domain.topic.Message;
import org.ra.atomidtesttask.domain.topic.Topic;
import org.ra.atomidtesttask.infrastructure.persistence.topic.MessageRepository;
import org.ra.atomidtesttask.infrastructure.persistence.topic.TopicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository repository;
    private final TopicRepository topicRepository;

    private boolean isOnlyMessageInTopic(Message message) {
        Topic topic = message.getTopic();
        return topic.getMessages().size() == 1 && topic.getMessages().get(0).equals(message);
    }

    @Override
    public Message findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Message could not be found"));
    }

    @Override
    public Message save(Message entity) {
        return repository.save(entity);
    }

    @Override
    public Message updateById(UUID id, MessageUpdateDto dto) {
        Message existing = repository.findById(id).orElseThrow(() ->
                new NotFoundException("Message with could not be found"));

        existing.setText(dto.getText());
        return repository.save(existing);
    }

    @Override
    public void deleteById(UUID id) {
        Message message = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Message could not be found"));
        if (isOnlyMessageInTopic(message)) {
            // if the last message in the topic is being deleted, the topic itself should be deleted
            topicRepository.delete(message.getTopic());
            return;
        }

        repository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    @Override
    public Page<Message> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Message> findAllByTopicId(UUID topicId, Pageable pageable) {
        return repository.findByTopicId(topicId, pageable);
    }
}
