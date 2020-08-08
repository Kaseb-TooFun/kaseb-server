package io.kaseb.server.module.controller;

import io.kaseb.server.authenticate.model.annotations.AuthenticationRequired;
import io.kaseb.server.authenticate.model.annotations.IgnoreAuthentication;
import io.kaseb.server.authenticate.model.enums.Role;
import io.kaseb.server.module.service.ModuleService;
import io.kaseb.server.operator.model.dto.BaseModuleDto;
import io.kaseb.server.user.exceptions.WebsiteNotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/latest")
    @ApiOperation("Get Latest Module link")
    @IgnoreAuthentication
    public ResponseEntity<String> getLatestModuleLink() {
        return ok(moduleService.getLatestModuleLink());
    }

    @GetMapping("{websiteId}/latest")
    @ApiOperation("Get Latest Module link")
    @IgnoreAuthentication
    public ResponseEntity<String> getWebsiteLatestModuleLink(
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
