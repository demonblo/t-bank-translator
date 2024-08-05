package ru.tbank.translator.component;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@RequiredArgsConstructor
public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {
    private final String token;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        // TODO: add check
        request.getHeaders().set("Authorization", "Bearer " + token);
        return execution.execute(request, body);
    }
}