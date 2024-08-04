package ru.tbank.translator.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.tbank.translator.service.TranslationService;

@Component
@RequiredArgsConstructor
public class AppRunnerImpl implements ApplicationRunner {
    private final TranslationService translationService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var sourceArgs = args.getSourceArgs();
        if (sourceArgs == null || sourceArgs.length < 3) {
            throw new IllegalArgumentException("Input is invalid!");
        }
        translationService.translate(sourceArgs);
    }
}
