package io.kaseb.server.user.service;

import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.user.exceptions.DuplicateUsernameException;
import io.kaseb.server.user.exceptions.IncorrectPasswordException;
import io.kaseb.server.user.exceptions.UserNotFoundException;
import io.kaseb.server.user.model.dao.UserRepo;
import io.kaseb.server.user.model.dao.WebsiteRepo;
import io.kaseb.server.user.model.dto.request.RegisterWebsiteRequestDto;
import io.kaseb.server.user.model.dto.response.GetWebsitesResponseDto;
import io.kaseb.server.user.model.dto.response.RegisterWebsiteResponseDto;
import io.kaseb.server.user.model.entities.UserEntity;
import io.kaseb.server.website.service.WebsiteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	private final UserRepo userRepo;
	private final WebsiteRepo websiteRepo;
	private final WebsiteService websiteService;

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

	public RegisterWebsiteResponseDto registerWebsite(RegisterWebsiteRequestDto request, UserEntity user) {
		return websiteService.registerWebsite(request, user);
	}

	public GetWebsitesResponseDto getWebsite(UserEntity user) throws UserNotFoundException {
		user = userRepo.findById(user.getId()).orElseThrow(UserNotFoundException::new);
		return websiteService.getWebsites(user);
	}
}
