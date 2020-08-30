package io.kaseb.server.infrastracture.harness;

import io.kaseb.server.infrastracture.harness.model.dto.request.CreateEngineRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class HarnessService {
    private final HarnessRestTemplate restTemplate;
    @Value("${services.infrastructure.harness.base-url}")
    private String baseUrl;

    public void createEngine(String websiteId) {
        CreateEngineRequestDto requestDto = new CreateEngineRequestDto(websiteId);
        HttpEntity<CreateEngineRequestDto> httpEntity = new HttpEntity<>(requestDto);
        try {
            String response = restTemplate.exchange(baseUrl + "/engines", HttpMethod.POST, httpEntity, String.class).getBody();
            logger.info("create engine response : {}", response);
        } catch (Exception ex) {
            logger.error("error in creating engine ", ex);
        }
    }

}
