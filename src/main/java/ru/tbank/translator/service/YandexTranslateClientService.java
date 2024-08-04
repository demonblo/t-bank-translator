package ru.tbank.translator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.tbank.translator.dto.TranslateRequestDTO;
import ru.tbank.translator.dto.TranslateResponseDTO;

@Service
@RequiredArgsConstructor
public class YandexTranslateClientService {
    @Value("${integration.translate.url}")
    private String translateUrl;
    private final RestTemplate restTemplate;

    public TranslateResponseDTO translate(TranslateRequestDTO request) {
        return restTemplate.postForObject(
                translateUrl,
                request,
                TranslateResponseDTO.class
        );
    }
}
