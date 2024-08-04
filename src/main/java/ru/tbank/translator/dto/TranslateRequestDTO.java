package ru.tbank.translator.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TranslateRequestDTO {
    private String sourceLanguageCode;
    private String targetLanguageCode;
    private String format;
    private String[] texts;
    private String folderId;
    private String model;
    private GlossaryConfig glossaryConfig;
    private Boolean speller;
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GlossaryConfig {
        private GlossaryData glossaryData;
    }
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GlossaryData {
        private GlossaryPair[] glossaryPairs;
    }
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GlossaryPair {
        private String sourceText;
        private String translatedText;
        private Boolean exact;
    }

}
