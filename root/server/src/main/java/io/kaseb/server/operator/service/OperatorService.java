package io.kaseb.server.operator.service;

import io.kaseb.server.authenticate.exceptions.ForbiddenException;
import io.kaseb.server.base.Constants;
import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.module.service.ModuleService;
import io.kaseb.server.operator.exceptions.OperatorIsNotActiveException;
import io.kaseb.server.operator.exceptions.OperatorNotFoundException;
import io.kaseb.server.operator.model.dao.OperatorRepo;
import io.kaseb.server.operator.model.dto.BaseModuleDto;
import io.kaseb.server.operator.model.dto.OperatorDto;
import io.kaseb.server.operator.model.dto.request.CreateModuleRequestDto;
import io.kaseb.server.operator.model.entities.OperatorEntity;
import io.kaseb.server.user.exceptions.DuplicateUsernameException;
import io.kaseb.server.user.exceptions.IncorrectPasswordException;
import io.kaseb.server.user.exceptions.UserNotFoundException;
import io.kaseb.server.util.HashUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OperatorService {
	private final OperatorRepo operatorRepo;
	private final MessageSource messageSource;
	private ModuleService moduleService;

	@Autowired
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public OperatorEntity signup(String username, String hashedPassword) throws DuplicateUsernameException {
		logger.info("trying to signup operator with username : {}", username);
		Optional<OperatorEntity> optionalOperatorEntity = operatorRepo.findByUsername(username);
		if (optionalOperatorEntity.isPresent()) {
			throw new DuplicateUsernameException();
		}
		OperatorEntity operatorEntity = new OperatorEntity(username, hashedPassword, false);
		return operatorRepo.save(operatorEntity);
	}

	public OperatorEntity login(String username, String hashedPassword) throws ServiceException {
		logger.info("trying to login operator with username : {}", username);
		Optional<OperatorEntity> optionalUserEntity = operatorRepo.findByUsername(username);
		OperatorEntity userEntity = optionalUserEntity.orElseThrow(UserNotFoundException::new);
		if (!Objects.equals(userEntity.getHashedPassword(), hashedPassword))
			throw new IncorrectPasswordException();
		return userEntity;
	}

	public void initializeSysadmin() {
		if (operatorRepo.findByUsername(Constants.SYSADMIN_USERNAME).isPresent())
			return;
		logger.info("trying to create sysadmin user");
		final String sysadminPassword = messageSource.getMessage("SYSADMIN_PASSWORD", null, Locale.US);
		OperatorEntity operatorEntity =
				new OperatorEntity(Constants.SYSADMIN_USERNAME, HashUtils.hash(sysadminPassword), true);
		operatorRepo.saveAndFlush(operatorEntity);
	}

	public List<OperatorDto> getOperatorsList() {
		final List<OperatorEntity> operatorEntities = operatorRepo.findAll();
		if (CollectionUtils.isEmpty(operatorEntities))
			return Collections.emptyList();
		return operatorEntities.stream().map(OperatorDto::new).collect(Collectors.toList());
	}


	public OperatorEntity findById(String id) throws OperatorNotFoundException {
		return operatorRepo.findById(id).orElseThrow(OperatorNotFoundException::new);
	}

	public Void activateOperator(OperatorEntity operator, String operatorId, boolean activate)
			throws ForbiddenException, OperatorNotFoundException {
		if (!operator.isSysadmin())
			throw new ForbiddenException();
		final OperatorEntity operatorEntity = findById(operatorId);
		if (operatorEntity.isSysadmin())
			throw new ForbiddenException();
		operatorEntity.updateActivationStatus(activate);
		operatorRepo.saveAndFlush(operatorEntity);
		return null;
	}

	public BaseModuleDto createModule(OperatorEntity operator, CreateModuleRequestDto requestDto)
			throws OperatorIsNotActiveException {
		if (!operator.isActivated())
			throw new OperatorIsNotActiveException();
		return moduleService.createModule(operator, requestDto);
	}
}
