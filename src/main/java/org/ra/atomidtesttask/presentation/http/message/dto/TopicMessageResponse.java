package org.ra.atomidtesttask.presentation.http.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@Schema(name = "Topic message response")
public class TopicMessageResponse {
    private UUID id;
    private String text;
    private String authorUsername;
    private ZonedDateTime publishedAt;
}
