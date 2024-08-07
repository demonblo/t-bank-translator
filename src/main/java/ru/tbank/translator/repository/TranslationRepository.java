package ru.tbank.translator.repository;

import ru.tbank.translator.model.Translation;

public interface TranslationRepository {
    void save(Translation translation);
    Translation findOneBySourceText(String sourceText);
}
