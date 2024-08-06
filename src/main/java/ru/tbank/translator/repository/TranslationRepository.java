package ru.tbank.translator.repository;

import ru.tbank.translator.model.Translation;

public interface TranslationRepository {
    int save(Translation translation);
}
