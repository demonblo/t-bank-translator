package ru.tbank.translator.dto;

import lombok.Data;

@Data
public class TranslateResponseDTO {
    private Translation[] translations;
    @Data
    public static class Translation {
        private String text;
        private String detectedLanguageCode;
    }
}
