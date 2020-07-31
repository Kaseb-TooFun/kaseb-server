package io.kaseb.server.website.service;

import io.kaseb.server.user.exceptions.UnauthorizedAccessException;
import io.kaseb.server.user.exceptions.WebsiteConfigNotFoundException;
import io.kaseb.server.user.exceptions.WebsiteExistsException;
import io.kaseb.server.user.exceptions.WebsiteNotFoundException;
import io.kaseb.server.user.model.dao.WebsiteRepo;
import io.kaseb.server.user.model.dto.BaseUserDto;
import io.kaseb.server.user.model.dto.BaseWebsiteDto;
import io.kaseb.server.user.model.dto.ConfigDto;
import io.kaseb.server.user.model.dto.request.RegisterWebsiteRequestDto;
import io.kaseb.server.user.model.dto.request.UpdateWebsiteRequestDto;
import io.kaseb.server.user.model.dto.response.GetWebsitesResponseDto;
import io.kaseb.server.user.model.dto.response.RegisterWebsiteResponseDto;
import io.kaseb.server.user.model.dto.response.UpdateWebsiteResponseDto;
import io.kaseb.server.user.model.entities.UserEntity;
import io.kaseb.server.website.model.dao.WebsiteConfigRepo;
import io.kaseb.server.website.model.dto.request.CreateWebsiteConfigRequestDto;
import io.kaseb.server.website.model.dto.request.UpdateWebsiteConfigRequestDto;
import io.kaseb.server.website.model.dto.response.CreateWebsiteConfigResponseDto;
import io.kaseb.server.website.model.dto.response.GetWebsiteConfigResponseDto;
import io.kaseb.server.website.model.dto.response.GetWebsiteResponseDto;
import io.kaseb.server.website.model.dto.response.UpdateWebsiteConfigResponseDto;
import io.kaseb.server.website.model.entities.WebsiteConfigEntity;
import io.kaseb.server.website.model.entities.WebsiteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Service
@RequiredArgsConstructor
public class WebsiteService {
    private final WebsiteRepo websiteRepo;
    private final WebsiteConfigRepo websiteConfigRepo;

    public GetWebsiteConfigResponseDto getWebsiteConfigs(String url, String id) throws WebsiteNotFoundException {
        WebsiteEntity websiteEntity = websiteRepo.findByUrl(url).orElse(null);
        if (websiteEntity == null)
            websiteEntity = websiteRepo.findById(id).orElseThrow(WebsiteNotFoundException::new);
        return new GetWebsiteConfigResponseDto(websiteEntity);
    }

    public UpdateWebsiteResponseDto updateWebsite(UpdateWebsiteRequestDto request, String id, UserEntity user)
            throws WebsiteNotFoundException, UnauthorizedAccessException {
        WebsiteEntity websiteEntity = websiteRepo.findById(id).orElseThrow(WebsiteNotFoundException::new);
        if (!user.equals(websiteEntity.getUser()))
            throw new UnauthorizedAccessException();
        updateWebsite(websiteEntity, request.getTitle());
        final BaseWebsiteDto websiteDto = new BaseWebsiteDto(websiteRepo.save(websiteEntity));
        return new UpdateWebsiteResponseDto(websiteDto);
    }

    private void updateWebsite(WebsiteEntity websiteEntity, String title) {
        if (title != null)
            websiteEntity.setTitle(title);
    }

    @Transactional
    public Void deleteWebsite(String id, UserEntity user)
            throws WebsiteNotFoundException, UnauthorizedAccessException {
        WebsiteEntity websiteEntity = websiteRepo.findById(id).orElseThrow(WebsiteNotFoundException::new);
        if (!user.equals(websiteEntity.getUser()))
            throw new UnauthorizedAccessException();
        websiteEntity.setDeleted(true);
        websiteRepo.save(websiteEntity);
        return null;
    }

    public CreateWebsiteConfigResponseDto createWebsiteConfig(
            String websiteId, CreateWebsiteConfigRequestDto request, UserEntity user)
            throws WebsiteNotFoundException, UnauthorizedAccessException {
        WebsiteEntity websiteEntity = websiteRepo.findById(websiteId).orElseThrow(WebsiteNotFoundException::new);
        if (!user.equals(websiteEntity.getUser()))
            throw new UnauthorizedAccessException();
        if (!CollectionUtils.isEmpty(websiteEntity.getConfigs()))
            websiteEntity.setConfigs(new ArrayList<>());
        if (CollectionUtils.isEmpty(request.getConfigValues()))
            return new CreateWebsiteConfigResponseDto();
        final List<ConfigDto> configDtos = new ArrayList<>(request.getConfigValues().size());
        for (String configValue : request.getConfigValues()) {
            WebsiteConfigEntity websiteConfigEntity = new WebsiteConfigEntity(websiteEntity, configValue);
            websiteConfigEntity = websiteConfigRepo.save(websiteConfigEntity);
            ConfigDto configDto = new ConfigDto(websiteConfigEntity);
            configDtos.add(configDto);
        }
        return new CreateWebsiteConfigResponseDto(configDtos);

    }

    public UpdateWebsiteConfigResponseDto updateWebsiteConfig(
            UpdateWebsiteConfigRequestDto request, String websiteId, String configId, UserEntity user)
            throws UnauthorizedAccessException, WebsiteConfigNotFoundException {
        WebsiteConfigEntity websiteConfigEntity =
                websiteConfigRepo.findById(configId).orElseThrow(WebsiteConfigNotFoundException::new);
        if (!Objects.equals(websiteConfigEntity.getWebsite().getUser(), user))
            throw new UnauthorizedAccessException();
        websiteConfigEntity.setConfigValue(request.getConfigValue());
        websiteConfigEntity = websiteConfigRepo.save(websiteConfigEntity);
        return new UpdateWebsiteConfigResponseDto(websiteConfigEntity);
    }

    @Transactional
    public Void deleteWebsiteConfig(String websiteId, String configId, UserEntity user)
            throws WebsiteConfigNotFoundException, UnauthorizedAccessException {
        WebsiteConfigEntity websiteConfigEntity =
                websiteConfigRepo.findById(configId).orElseThrow(WebsiteConfigNotFoundException::new);
        if (!Objects.equals(websiteConfigEntity.getWebsite().getUser(), user))
            throw new UnauthorizedAccessException();
        websiteConfigRepo.delete(websiteConfigEntity);
        return null;
    }

    public GetWebsiteResponseDto getWebsites(String websiteId) throws WebsiteNotFoundException {
        final WebsiteEntity websiteEntity = websiteRepo.findById(websiteId).orElseThrow(WebsiteNotFoundException::new);
        return new GetWebsiteResponseDto(websiteEntity);
    }

    public GetWebsitesResponseDto getWebsites(UserEntity user) {
        final List<WebsiteEntity> websites = user.getWebsites();
        List<BaseWebsiteDto> websiteDtoList;
        if (CollectionUtils.isEmpty(websites))
            websiteDtoList = Collections.emptyList();
        else
            websiteDtoList = websites.stream()
                    .filter(i -> !i.isDeleted()).map(BaseWebsiteDto::new).collect(Collectors.toList());
        return new GetWebsitesResponseDto(websiteDtoList);
    }

    public RegisterWebsiteResponseDto registerWebsite(RegisterWebsiteRequestDto request, UserEntity user)
            throws WebsiteExistsException {
        final String url = request.getWebsiteUrl();
        if (websiteRepo.existsByUrl(url))
            throw new WebsiteExistsException();
        WebsiteEntity websiteEntity = new WebsiteEntity(user, url, request.getTitle());
        websiteEntity = websiteRepo.save(websiteEntity);
        final BaseWebsiteDto websiteDto = new BaseWebsiteDto(websiteRepo.save(websiteEntity));
        final BaseUserDto userDto = new BaseUserDto(user);
        return new RegisterWebsiteResponseDto(websiteDto, userDto);
    }
}
