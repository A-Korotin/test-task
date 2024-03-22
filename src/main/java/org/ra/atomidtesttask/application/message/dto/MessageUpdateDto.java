package org.ra.atomidtesttask.application.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MessageUpdateDto {
    private String text;
}
