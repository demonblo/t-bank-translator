package ru.tbank.translator.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.tbank.translator.model.Translation;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcTranslationRepository implements TranslationRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public int save(Translation translation) {
        String sql = "INSERT INTO translations (ip_address, input_words, output_words) VALUES (:ipAddress, :inputWords, :outputWords)";

        Map<String, String> params = new HashMap<>();

        params.put("ipAddress", translation.getIpAddress());
        params.put("inputWords", translation.getInputWords());
        params.put("outputWords", translation.getOutputWords());
        return jdbcTemplate.update(sql, params);
    }


}
