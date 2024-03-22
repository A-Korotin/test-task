package org.ra.atomidtesttask.application.topic.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicUpdateDto {
    private String name;
}
