package org.ra.atomidtesttask.domain.topic;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.ra.atomidtesttask.domain.user.User;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "messages")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_username")
    private User user;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    private String text;

    @CreationTimestamp
    private ZonedDateTime publishedAt;
}
