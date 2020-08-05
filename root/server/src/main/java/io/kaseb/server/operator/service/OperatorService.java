package io.kaseb.server.operator.service;

import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.operator.model.dao.OperatorRepo;
import io.kaseb.server.operator.model.entities.OperatorEntity;
import io.kaseb.server.user.exceptions.DuplicateUsernameException;
import io.kaseb.server.user.exceptions.IncorrectPasswordException;
import io.kaseb.server.user.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OperatorService {
    private final OperatorRepo operatorRepo;

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
}
