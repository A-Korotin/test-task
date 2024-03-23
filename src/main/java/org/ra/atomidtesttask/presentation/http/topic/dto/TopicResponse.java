package org.ra.atomidtesttask.presentation.http.topic.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@Schema(name = "Topic response")
public class TopicResponse {
    private UUID id;
    @Schema(example = "Topic name")
    private String name;
}
