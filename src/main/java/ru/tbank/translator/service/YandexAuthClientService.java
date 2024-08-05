package ru.tbank.translator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;
import ru.tbank.translator.dto.IamDTO;
import ru.tbank.translator.dto.JwtDTO;
import ru.tbank.translator.dto.KeyInfoDTO;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class YandexAuthClientService {
    @Value("${integration.auth.url}")
    private String authUrl;
    @Value("classpath:keys/authorized_key.json")
    private Resource resource;
    private final ObjectMapper objectMapper;
    private final RestTemplate authRestTemplate;

    private JwtDTO getEncodedToken() throws NoSuchAlgorithmException, InvalidKeySpecException {

        KeyInfoDTO keyInfo;
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            String content = FileCopyUtils.copyToString(reader);
            keyInfo = objectMapper.readValue(content, KeyInfoDTO.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("File is invalid");
        }
        String privateKeyString = keyInfo.getPrivate_key();
        String serviceAccountId = keyInfo.getService_account_id();
        String keyId = keyInfo.getId();

        PemObject privateKeyPem;
        try (PemReader reader = new PemReader(new StringReader(privateKeyString))) {
            privateKeyPem = reader.readPemObject();
        } catch (IOException e) {
            throw new IllegalArgumentException("File is invalid");
        }

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyPem.getContent()));

        Instant now = Instant.now();

        String token = Jwts.builder()
                .setHeaderParam("kid", keyId)
                .setIssuer(serviceAccountId)
                .setAudience("https://iam.api.cloud.yandex.net/iam/v1/tokens")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(3600)))
                .signWith(privateKey, SignatureAlgorithm.PS256)
                .compact();

        return new JwtDTO(token);
    }

    public IamDTO auth() {
        JwtDTO jwt;
        try {
            jwt = getEncodedToken();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            throw new IllegalArgumentException("Error while generating token!");
        }
        return authRestTemplate.postForObject(authUrl, jwt, IamDTO.class);
    }
}
