package org.ra.atomidtesttask.infrastructure.persistence.topic;

import org.ra.atomidtesttask.domain.topic.Topic;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TopicRepository extends JpaRepository<Topic, UUID> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"messages"})
    Optional<Topic> findById(UUID id);
}
