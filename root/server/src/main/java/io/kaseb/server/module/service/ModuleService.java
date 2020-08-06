package io.kaseb.server.module.service;

import io.kaseb.server.module.model.dao.ModuleRepo;
import io.kaseb.server.module.model.entities.ModuleEntity;
import io.kaseb.server.operator.model.dto.BaseModuleDto;
import io.kaseb.server.operator.model.dto.request.CreateModuleRequestDto;
import io.kaseb.server.operator.model.entities.OperatorEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ModuleService {
    private static volatile String latestModuleLink;
    private final ModuleRepo moduleRepo;

    private static synchronized void updateLatestModuleLink(String link) {
        latestModuleLink = link;
    }

    public BaseModuleDto createModule(OperatorEntity operator, CreateModuleRequestDto requestDto) {
        logger.info("trying to create module with operator : {} ,request : {}", operator.getId(), requestDto);
        ModuleEntity moduleEntity = new ModuleEntity(requestDto.getModuleLink(), operator);
        updateLatestModuleLink(requestDto.getModuleLink());
        return new BaseModuleDto(moduleRepo.saveAndFlush(moduleEntity));
    }

    @Scheduled(fixedRate = 2 * 60 * 60 * 1000)
    @PostConstruct
    public void updateLatestModuleLink() {
        Optional<ModuleEntity> optionalModuleEntity = moduleRepo.findFirstByOrderByCreationDateDesc();
        if (optionalModuleEntity.isEmpty())
            return;
        updateLatestModuleLink(optionalModuleEntity.get().getLink());
    }

    public String getLatestModuleLink() {
        return latestModuleLink;
    }

    public List<BaseModuleDto> getModules() {
        final List<ModuleEntity> moduleEntities = moduleRepo.findAll();
        if (CollectionUtils.isEmpty(moduleEntities))
            return Collections.emptyList();
        return moduleEntities.stream().map(BaseModuleDto::new).collect(Collectors.toList());
    }
}
