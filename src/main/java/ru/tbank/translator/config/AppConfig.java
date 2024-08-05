package ru.tbank.translator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import ru.tbank.translator.component.HeaderRequestInterceptor;
import ru.tbank.translator.service.YandexAuthClientService;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean("authRestTemplate")
    public RestTemplate authRestTemplate() {
        return new RestTemplate();
    }

    @Bean("translateRestTemplate")
    public RestTemplate translateRestTemplate(YandexAuthClientService yandexAuthClientService) {
        RestTemplate restTemplate = new RestTemplate();

        List<ClientHttpRequestInterceptor> interceptors
                = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new HeaderRequestInterceptor(yandexAuthClientService.auth().getIamToken()));
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}


