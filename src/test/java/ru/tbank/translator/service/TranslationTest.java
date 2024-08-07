package ru.tbank.translator.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.tbank.translator.model.Translation;
import ru.tbank.translator.repository.TranslationRepository;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.concurrent.RejectedExecutionException;

@SpringBootTest
public class TranslationTest {
    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private TranslationService translationService;

    @Test
    public void correctSaveTest() {
        String ipAddress = "26.168.90.123";
        String sourceWords = "I dont want this test 0";
        String expWords = "Я не хотеть этот тест 0";
        Translation expTranslation = Translation.builder()
                .ipAddress(ipAddress)
                .inputWords(sourceWords)
                .outputWords(expWords)
                .build();

        translationRepository.save(expTranslation);
        Translation translation = translationRepository.findOneBySourceText(sourceWords);

        Assertions.assertEquals(expWords, translation.getOutputWords());
    }

    @Test
    public void correctTranslateAndSaveTest() throws UnknownHostException {
        String sourceLanguage = "en";
        String targetLanguage = "ru";
        String ipAddress = "26.168.90.123";
        String sourceWords = "Mom and dad";
        String expWords = "Мама и папа";
        Translation expTranslation = Translation.builder()
                .ipAddress(ipAddress)
                .inputWords(sourceWords)
                .outputWords(expWords)
                .build();

        translationService.translate(sourceLanguage, targetLanguage, Collections.singletonList(sourceWords));
        Translation translation = translationRepository.findOneBySourceText(sourceWords);

        Assertions.assertEquals(expTranslation.getInputWords(), translation.getInputWords());
        Assertions.assertEquals(expTranslation.getOutputWords(), translation.getOutputWords());
    }

    @Test
    public void tooManyWords() throws UnknownHostException {
        String sourceLanguage = "en";
        String targetLanguage = "ru";
        String sourceWords = "Two households, both alike in dignity,\n" +
                "In fair Verona, where we lay our scene,\n" +
                "From ancient grudge break to new mutiny,\n" +
                "Where civil blood makes civil hands unclean.\n" +
                "From forth the fatal loins of these two foes\n" +
                "A pair of star-cross'd lovers take their life;\n" +
                "Whose misadventured piteous overthrows\n" +
                "Do with their death bury their parents' strife.\n" +
                "The fearful passage of their death-mark'd love,\n" +
                "And the continuance of their parents' rage,\n" +
                "Which, but their children's end, nought could remove,\n" +
                "Is now the two hours' traffic of our stage;\n" +
                "The which if you with patient ears attend,\n" +
                "What here shall miss, our toil shall strive to mend.";


        Assertions.assertThrows(RejectedExecutionException.class, () -> {translationService
                .translate(sourceLanguage,
                        targetLanguage,
                        Collections.singletonList(sourceWords));});
    }
}
