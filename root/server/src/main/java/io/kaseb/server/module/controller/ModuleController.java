package io.kaseb.server.module.controller;

import io.kaseb.server.authenticate.model.annotations.AuthenticationRequired;
import io.kaseb.server.authenticate.model.annotations.IgnoreAuthentication;
import io.kaseb.server.authenticate.model.enums.Role;
import io.kaseb.server.base.RequestContext;
import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.module.model.dto.response.LatestModuleDto;
import io.kaseb.server.module.service.ModuleService;
import io.kaseb.server.operator.model.dto.BaseModuleDto;
import io.kaseb.server.operator.model.dto.request.CreateModuleRequestDto;
import io.kaseb.server.operator.service.OperatorService;
import io.kaseb.server.user.exceptions.WebsiteNotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@RestController
@RequestMapping("/api/v1/modules")
@RequiredArgsConstructor
public class ModuleController {
    private final ModuleService moduleService;
    private final OperatorService operatorService;
    private final RequestContext requestContext;

    @PostMapping
    @ApiOperation("Create Module")
    @AuthenticationRequired(role = Role.OPERATOR)
    public ResponseEntity<BaseModuleDto> createModule(
            @RequestBody CreateModuleRequestDto requestDto)
            throws ServiceException {
        return ok(operatorService.createModule(requestContext.getOperator(), requestDto));
    }

    @GetMapping("/latest")
    @ApiOperation("Get Latest Module link")
    @IgnoreAuthentication
    public ResponseEntity<LatestModuleDto> getLatestModuleLink() {
        return ok(moduleService.getLatestModuleLink());
    }

    @GetMapping("{websiteId}/latest")
    @ApiOperation("Get Latest Module link")
    @IgnoreAuthentication
    public ResponseEntity<LatestModuleDto> getWebsiteLatestModuleLink(
            @PathVariable("websiteId") String websiteId) throws WebsiteNotFoundException {
        return ok(moduleService.getLatestModuleLink(websiteId));
    }

    @GetMapping
    @ApiOperation("Get Module links")
    @AuthenticationRequired(role = Role.OPERATOR)
    public ResponseEntity<List<BaseModuleDto>> getModules() {
        return ok(moduleService.getModules());
    }
}
