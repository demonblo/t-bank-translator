package ru.tbank.translator.mapper;

import org.springframework.stereotype.Component;
import ru.tbank.translator.dto.TranslateRequestDTO;

@Component
public class TranslateRequestMapper {
    public TranslateRequestDTO argsToRequestDTO(String... args) {
        var translateRequestDTO = new TranslateRequestDTO();
        translateRequestDTO.setSourceLanguageCode(args[0]);
        translateRequestDTO.setTargetLanguageCode(args[1]);
        translateRequestDTO.setTexts(new String[]{args[2]});

        return translateRequestDTO;
    }
}
