package io.kaseb.server.operator.service;

import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.operator.model.dao.OperatorRepo;
import io.kaseb.server.operator.model.dto.OperatorDto;
import io.kaseb.server.operator.model.entities.OperatorEntity;
import io.kaseb.server.user.exceptions.DuplicateUsernameException;
import io.kaseb.server.user.exceptions.IncorrectPasswordException;
import io.kaseb.server.user.exceptions.UserNotFoundException;
import io.kaseb.server.util.HashUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OperatorService {
    private final OperatorRepo operatorRepo;
    private static final String SYSADMIN_USERNAME = "sysadmin";
    private final MessageSource messageSource;

    public OperatorEntity signup(String username, String hashedPassword) throws DuplicateUsernameException {
        logger.info("trying to signup operator with username : {}", username);
        Optional<OperatorEntity> optionalOperatorEntity = operatorRepo.findByUsername(username);
        if (optionalOperatorEntity.isPresent()) {
            throw new DuplicateUsernameException();
        }
        OperatorEntity operatorEntity = new OperatorEntity(username, hashedPassword);
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
        if (operatorRepo.findByUsername(SYSADMIN_USERNAME).isPresent())
            return;
        logger.info("trying to create sysadmin user");
        final String sysadminPassword = messageSource.getMessage("SYSADMIN_PASSWORD", null, Locale.US);
        OperatorEntity operatorEntity = new OperatorEntity(SYSADMIN_USERNAME, HashUtils.hash(sysadminPassword));
        operatorRepo.saveAndFlush(operatorEntity);
    }

    public List<OperatorDto> getOperatorsList() {
        final List<OperatorEntity> operatorEntities = operatorRepo.findAll();
        if (CollectionUtils.isEmpty(operatorEntities))
            return Collections.emptyList();
        return operatorEntities.stream().map(OperatorDto::new).collect(Collecots.toList());
    }
}
