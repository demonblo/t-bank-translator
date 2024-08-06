package ru.tbank.translator.component;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.tbank.translator.service.TranslationService;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class AppRunnerImpl implements ApplicationRunner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TranslationService translationService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var sourceArgs = args.getSourceArgs();
        if (sourceArgs == null || sourceArgs.length < 3) {
            String response = "http 400. Неверные входные данные.";
            logger.error(response);
            return;
        }
        translationService.translate(sourceArgs[0], sourceArgs[1], Arrays.stream(sourceArgs).skip(2).toList());
    }
}
