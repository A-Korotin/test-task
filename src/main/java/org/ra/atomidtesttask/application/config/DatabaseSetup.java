package org.ra.atomidtesttask.application.config;

import lombok.RequiredArgsConstructor;
import org.ra.atomidtesttask.application.message.service.MessageService;
import org.ra.atomidtesttask.application.topic.service.TopicService;
import org.ra.atomidtesttask.application.user.service.UserService;
import org.ra.atomidtesttask.domain.topic.Message;
import org.ra.atomidtesttask.domain.topic.Topic;
import org.ra.atomidtesttask.domain.user.User;
import org.ra.atomidtesttask.domain.user.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSetup implements CommandLineRunner {
    private final UserService userService;
    private final TopicService topicService;
    private final MessageService messageService;

    private void populateMessages(Topic topic, User user) {
        for (int i = 0; i < 100; ++i) {
            Message message = new Message(null, user, topic, "Text " + i, null);
            messageService.save(message);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        // populate users
        User user = new User();
        user.setUsername("initial_user");
        user.setRole(UserRole.USER);
        user.setPassword("initial_password");
        user = userService.register(user);

        User admin = new User();
        admin.setUsername("admin_user");
        admin.setRole(UserRole.ADMIN);
        admin.setPassword("admin_password");
        admin = userService.register(admin);

        // populate topics and messages
        for (int i = 0; i < 100; ++i) {
            Topic topic = new Topic(null, "Topic " + i, null);
            topic = topicService.save(topic);
            populateMessages(topic, i % 2 == 0 ? user : admin);
        }
    }
}
