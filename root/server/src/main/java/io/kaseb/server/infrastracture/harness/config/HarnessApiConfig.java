package io.kaseb.server.infrastracture.harness.config;

import io.kaseb.server.infrastracture.harness.HarnessRestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Configuration
public class HarnessApiConfig {
    @Value("${services.infrastructure.harness.username}")
    private String username;
    @Value("${services.infrastructure.harness.password}")
    private String password;

    public BasicAuthenticationInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthenticationInterceptor(username, password);
    }

    @Bean
    public HarnessRestTemplate harnessRestTemplate() {
        HarnessRestTemplate harnessRestTemplate = new HarnessRestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(basicAuthRequestInterceptor());
        harnessRestTemplate.setInterceptors(interceptors);
        return harnessRestTemplate;
    }
}
