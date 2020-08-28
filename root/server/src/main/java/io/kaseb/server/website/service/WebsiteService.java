package io.kaseb.server.website.service;

import io.kaseb.server.user.exceptions.UnauthorizedAccessException;
import io.kaseb.server.user.exceptions.WebsiteConfigNotFoundException;
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
import io.kaseb.server.website.model.dto.response.*;
import io.kaseb.server.website.model.entities.WebsiteConfigEntity;
import io.kaseb.server.website.model.entities.WebsiteEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class WebsiteService {
    private final WebsiteRepo websiteRepo;
    private final WebsiteConfigRepo websiteConfigRepo;


    public GetWebsiteConfigsResponseDto getWebsiteConfigs(String url, String id) throws WebsiteNotFoundException {
        WebsiteEntity websiteEntity = websiteRepo.findByUrl(url).orElse(null);
        if (websiteEntity == null) {
            websiteEntity = findById(id);
        }
        return new GetWebsiteConfigsResponseDto(getWebsiteConfigs(websiteEntity)
                .stream().map(ConfigDto::new).collect(Collectors.toList()));
    }

    public GetWebsiteConfigResponseDto getWebsiteConfig(String websiteId, String configId)
            throws WebsiteNotFoundException, WebsiteConfigNotFoundException {
        WebsiteEntity websiteEntity = websiteRepo.findById(websiteId).orElseThrow(WebsiteNotFoundException::new);
        WebsiteConfigEntity websiteConfigEntity = websiteConfigRepo.findById(configId)
                .orElseThrow(WebsiteConfigNotFoundException::new);
        if (!Objects.equals(websiteConfigEntity.getWebsite().getId(), websiteEntity.getId()))
            throw new WebsiteConfigNotFoundException();
        return new GetWebsiteConfigResponseDto(websiteConfigEntity, websiteEntity.getUrl());
    }

    public WebsiteEntity findById(String websiteId) throws WebsiteNotFoundException {
        logger.info("trying to find website with id : {}", websiteId);
        return websiteRepo.findById(websiteId).orElseThrow(WebsiteNotFoundException::new);
    }

    public List<WebsiteConfigEntity> getWebsiteConfigs(WebsiteEntity websiteEntity) {
        if (websiteEntity == null)
            return Collections.emptyList();
        return websiteConfigRepo.findAllByWebsite(websiteEntity);
    }

    public UpdateWebsiteResponseDto updateWebsite(UpdateWebsiteRequestDto request, String id, UserEntity user)
            throws WebsiteNotFoundException, UnauthorizedAccessException {
        WebsiteEntity websiteEntity = findById(id);
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
        WebsiteEntity websiteEntity = findById(id);
        if (!user.equals(websiteEntity.getUser()))
            throw new UnauthorizedAccessException();
        websiteEntity.setDeleted(true);
        websiteRepo.save(websiteEntity);
        return null;
    }

    public CreateWebsiteConfigResponseDto createWebsiteConfig(
            String websiteId, CreateWebsiteConfigRequestDto request, UserEntity user)
            throws WebsiteNotFoundException, UnauthorizedAccessException {
        WebsiteEntity websiteEntity = findById(websiteId);
        if (!user.equals(websiteEntity.getUser()))
            throw new UnauthorizedAccessException();
        if (CollectionUtils.isEmpty(request.getConfigValues()))
            return new CreateWebsiteConfigResponseDto();
        final List<ConfigDto> configDtoList = new ArrayList<>(request.getConfigValues().size());
        for (String configValue : request.getConfigValues()) {
            WebsiteConfigEntity websiteConfigEntity = createWebsiteConfigEntity(websiteEntity, request, configValue);
            websiteConfigEntity = websiteConfigRepo.save(websiteConfigEntity);
            ConfigDto configDto = new ConfigDto(websiteConfigEntity);
            configDtoList.add(configDto);
        }
        return new CreateWebsiteConfigResponseDto(configDtoList);

    }

    private WebsiteConfigEntity createWebsiteConfigEntity(
            WebsiteEntity websiteEntity, CreateWebsiteConfigRequestDto request, String configValue) {
        return new WebsiteConfigEntity()
                .setWebsite(websiteEntity)
                .setName(request.getName())
                .setGoalType(request.getGoalType())
                .setGoalLink(request.getGoalLink())
                .setGoalSelector(request.getGoalSelector())
                .setConfigValue(configValue);
    }

    public UpdateWebsiteConfigResponseDto updateWebsiteConfig(
            UpdateWebsiteConfigRequestDto request, String websiteId, String configId, UserEntity user)
            throws UnauthorizedAccessException, WebsiteConfigNotFoundException {
        logger.info("trying to update websiteConfig with [websiteId : {}, configId : {}]", websiteId, configId);
        WebsiteConfigEntity websiteConfigEntity =
                websiteConfigRepo.findById(configId).orElseThrow(WebsiteConfigNotFoundException::new);
        if (!Objects.equals(websiteConfigEntity.getWebsite().getUser(), user))
            throw new UnauthorizedAccessException();
        updateWebsiteConfig(request, websiteConfigEntity);
        websiteConfigEntity = websiteConfigRepo.save(websiteConfigEntity);
        return new UpdateWebsiteConfigResponseDto(websiteConfigEntity);
    }

    private void updateWebsiteConfig(UpdateWebsiteConfigRequestDto request, WebsiteConfigEntity websiteConfigEntity) {
        if (null != request.getConfigValue())
            websiteConfigEntity.setConfigValue(request.getConfigValue());
        if (null != request.getName())
            websiteConfigEntity.setName(request.getName());
        if (null != request.getGoalLink())
            websiteConfigEntity.setGoalLink(request.getGoalLink());
        if (null != request.getGoalType())
            websiteConfigEntity.setGoalType(request.getGoalType());
        if (null != request.getGoalSelector())
            websiteConfigEntity.setGoalSelector(request.getGoalSelector());
    }

    @Transactional
    public Void deleteWebsiteConfig(String websiteId, String configId, UserEntity user)
            throws WebsiteConfigNotFoundException, UnauthorizedAccessException {
        logger.info("trying to delete websiteConfig with [websiteId : {}, configId : {}]", websiteId, configId);
        WebsiteConfigEntity websiteConfigEntity =
                websiteConfigRepo.findById(configId).orElseThrow(WebsiteConfigNotFoundException::new);
        if (!Objects.equals(websiteConfigEntity.getWebsite().getUser(), user))
            throw new UnauthorizedAccessException();
        websiteConfigRepo.delete(websiteConfigEntity);
        return null;
    }

    public GetWebsiteResponseDto getWebsite(String websiteId) throws WebsiteNotFoundException {
        final WebsiteEntity websiteEntity = findById(websiteId);
        return new GetWebsiteResponseDto(websiteEntity, getWebsiteConfigs(websiteEntity));
    }

    public GetWebsitesResponseDto getWebsites(UserEntity user) {

        final List<WebsiteEntity> websites = websiteRepo.findAllByUserAndDeletedIsFalse(user);
        List<BaseWebsiteDto> websiteDtoList;
        if (CollectionUtils.isEmpty(websites))
            websiteDtoList = Collections.emptyList();
        else
            websiteDtoList = websites.stream()
                    .map(BaseWebsiteDto::new).collect(Collectors.toList());
        return new GetWebsitesResponseDto(websiteDtoList);
    }

    public RegisterWebsiteResponseDto registerWebsite(RegisterWebsiteRequestDto request, UserEntity user) {
        final String url = request.getWebsiteUrl();
        WebsiteEntity websiteEntity = new WebsiteEntity(user, url, request.getTitle());
        websiteEntity = websiteRepo.save(websiteEntity);
        final BaseWebsiteDto websiteDto = new BaseWebsiteDto(websiteRepo.save(websiteEntity));
        final BaseUserDto userDto = new BaseUserDto(user);
        return new RegisterWebsiteResponseDto(websiteDto, userDto);
    }
}
