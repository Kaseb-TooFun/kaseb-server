package io.kaseb.server.user.service;

import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.user.exceptions.*;
import io.kaseb.server.user.model.dao.UserRepo;
import io.kaseb.server.user.model.dao.WebsiteRepo;
import io.kaseb.server.user.model.dto.BaseUserDto;
import io.kaseb.server.user.model.dto.BaseWebsiteDto;
import io.kaseb.server.user.model.dto.request.RegisterWebsiteRequestDto;
import io.kaseb.server.user.model.dto.request.UpdateWebsiteRequestDto;
import io.kaseb.server.user.model.dto.response.GetWebsitesResponseDto;
import io.kaseb.server.user.model.dto.response.RegisterWebsiteResponseDto;
import io.kaseb.server.user.model.dto.response.UpdateWebsiteResponseDto;
import io.kaseb.server.user.model.entities.UserEntity;
import io.kaseb.server.user.model.entities.WebsiteEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final WebsiteRepo websiteRepo;

    public UserEntity signup(String username, String hashedPassword) throws DuplicateUsernameException {
        logger.info("trying to signup with username : {}", username);
        Optional<UserEntity> optionalUserEntity = userRepo.findByUsername(username);
        if (optionalUserEntity.isPresent()) {
            throw new DuplicateUsernameException();
        }
        UserEntity userEntity = new UserEntity(username, hashedPassword);
        return userRepo.save(userEntity);
    }

    public UserEntity login(String username, String hashedPassword) throws ServiceException {
        logger.info("trying to login with username : {}", username);
        Optional<UserEntity> optionalUserEntity = userRepo.findByUsername(username);
        UserEntity userEntity = optionalUserEntity.orElseThrow(UserNotFoundException::new);
        if (!Objects.equals(userEntity.getHashedPassword(), hashedPassword))
            throw new IncorrectPasswordException();
        return userEntity;
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

    public GetWebsitesResponseDto getWebsite(UserEntity user) throws UserNotFoundException {
        user = userRepo.findById(user.getId()).orElseThrow(UserNotFoundException::new);//doing this for reattaching entity
        final List<WebsiteEntity> websites = user.getWebsites();
        List<BaseWebsiteDto> websiteDtoList;
        if (CollectionUtils.isEmpty(websites))
            websiteDtoList = Collections.emptyList();
        else
            websiteDtoList = websites.stream()
                    .filter(i -> !i.isDeleted()).map(BaseWebsiteDto::new).collect(Collectors.toList());
        return new GetWebsitesResponseDto(websiteDtoList);
    }

    public UpdateWebsiteResponseDto updateWebsite(UpdateWebsiteRequestDto request, String id, UserEntity user)
            throws WebsiteNotFoundException, UnauthorizedAccessException {
        WebsiteEntity websiteEntity = websiteRepo.findById(id).orElseThrow(WebsiteNotFoundException::new);
        if (!user.equals(websiteEntity.getUser()))
            throw new UnauthorizedAccessException();
        websiteEntity.setTitle(request.getTitle());
        final BaseWebsiteDto websiteDto = new BaseWebsiteDto(websiteRepo.save(websiteEntity));
        return new UpdateWebsiteResponseDto(websiteDto);
    }

    public Void deleteWebsite(String id, UserEntity user)
            throws WebsiteNotFoundException, UnauthorizedAccessException {
        WebsiteEntity websiteEntity = websiteRepo.findById(id).orElseThrow(WebsiteNotFoundException::new);
        if (!user.equals(websiteEntity.getUser()))
            throw new UnauthorizedAccessException();
        websiteEntity.setDeleted(true);
        websiteRepo.save(websiteEntity);
        return null;
    }
}
