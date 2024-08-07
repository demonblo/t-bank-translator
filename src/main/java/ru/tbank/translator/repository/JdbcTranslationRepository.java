package ru.tbank.translator.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tbank.translator.model.Translation;

@Repository
@RequiredArgsConstructor
public class JdbcTranslationRepository implements TranslationRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Translation translation) {
        String sql = "INSERT INTO translations (ip_address, input_words, output_words) " +
                "VALUES (:ipAddress, :inputWords, :outputWords)";

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("ipAddress", translation.getIpAddress());
        params.addValue("inputWords", translation.getInputWords());
        params.addValue("outputWords", translation.getOutputWords());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Translation findOneBySourceText(String sourceText) {
        String sql = "SELECT DISTINCT * FROM translations WHERE input_words = '" + sourceText +"'";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Translation.class)).get(0);



    }
}
