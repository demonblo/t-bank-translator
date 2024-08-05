package ru.tbank.translator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tbank.translator.mapper.TranslateRequestMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final YandexTranslateClientService yandexTranslateClientService;
    private final TranslateRequestMapper translaterequestMapper;

    public void translate(String... args) {
        // todo: multithreding
        String sourceLanguage = args[0];
        String targetLanguage = args[1];
        List<String> words = new ArrayList<>();
        for (int i = 2; i < args.length; i++) {
            var requestDTO = translaterequestMapper.argsToRequestDTO(
                    sourceLanguage,
                    targetLanguage,
                    args[i]
            );
            var responseDTO = yandexTranslateClientService.translate(requestDTO);
            words.add(responseDTO.getTranslations()[0].getText());
        }
        System.out.println(words);
    }
}
