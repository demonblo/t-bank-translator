package ru.tbank.translator.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class IamDTO {
    private String iamToken;
    private OffsetDateTime expiresAt;
}
