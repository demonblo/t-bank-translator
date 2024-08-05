package ru.tbank.translator.service;

import lombok.RequiredArgsConstructor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.tbank.translator.mapper.TranslateRequestMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final YandexTranslateClientService yandexTranslateClientService;
    private final TranslateRequestMapper translateRequestMapper;
    @Qualifier("asyncExecutor")
    private final ThreadPoolTaskExecutor asyncExecutor;

    public void translate(String... args) {
        List<Callable<String>> callables = new ArrayList<>();
        String sourceLanguage = args[0];
        String targetLanguage = args[1];
        List<String> words = new ArrayList<>();
        for (int i = 2; i < args.length; i++) {
            var requestDTO = translateRequestMapper.argsToRequestDTO(
                    sourceLanguage,
                    targetLanguage,
                    args[i]
            );
            callables.add(
                    () -> yandexTranslateClientService
                            .translate(requestDTO)
                            .getTranslations()[0]
                            .getText()
            );
        }
        List<Future<String>> completableFutureList = new ArrayList<>();
        for (var callable: callables) {
            try {
                completableFutureList.add(asyncExecutor.submit(callable));
            } catch (RejectedExecutionException e) {
                String response = "http 500. Ошибка выполнения программы. " + e.getMessage();
                logger.error(response);
                return;
            }
        }

        for (var completableFuture: completableFutureList) {
            try {
                var translatedWord = completableFuture.get();
                words.add(translatedWord);
            } catch (HttpClientErrorException e) {
                String response = "http " + e.getStatusCode() + ". Ошибка выполнения программы. " + e.getMessage();
                logger.error(response);
                return;
            } catch (Exception e) {
                String response = "http 500. Ошибка выполнения программы. " + e.getMessage();
                logger.error(response, e);
                return;
            }
        }
        logger.info("http 200. " + String.join(" ", words));

    }
}
