package org.ra.atomidtesttask.presentation.http.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@Schema(name = "Message response")
public class MessageResponse {
    private UUID id;
    private UUID topicId;
    @Schema(example = "Message text")
    private String text;
    @Schema(example = "example_username")
    private String authorUsername;
    private ZonedDateTime publishedAt;
}
