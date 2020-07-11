package io.kaseb.server.website.service;

import io.kaseb.server.user.exceptions.WebsiteNotFoundException;
import io.kaseb.server.user.model.dao.WebsiteRepo;
import io.kaseb.server.user.model.entities.WebsiteEntity;
import io.kaseb.server.website.model.dto.response.GetWebsiteConfigResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Service
@RequiredArgsConstructor
public class WebsiteService {
    private final WebsiteRepo websiteRepo;

    public GetWebsiteConfigResponseDto getWebsiteConfigs(String websiteUrl) throws WebsiteNotFoundException {
        WebsiteEntity websiteEntity = websiteRepo.findByUrl(websiteUrl).orElseThrow(WebsiteNotFoundException::new);
        return new GetWebsiteConfigResponseDto(websiteEntity);
    }
}
