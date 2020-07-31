package io.kaseb.server.website.controller;

import io.kaseb.server.authenticate.model.annotations.IgnoreAuthentication;
import io.kaseb.server.base.RequestContext;
import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.website.model.dto.request.CreateWebsiteConfigRequestDto;
import io.kaseb.server.website.model.dto.request.UpdateWebsiteConfigRequestDto;
import io.kaseb.server.website.model.dto.response.CreateWebsiteConfigResponseDto;
import io.kaseb.server.website.model.dto.response.GetWebsiteConfigResponseDto;
import io.kaseb.server.website.model.dto.response.UpdateWebsiteConfigResponseDto;
import io.kaseb.server.website.service.WebsiteService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@RequestMapping("/api/v1/websites/{websiteId}/configs")
@RestController
@RequiredArgsConstructor
public class WebsiteConfigController {
    private final WebsiteService websiteService;
    private final RequestContext requestContext;

    @ApiOperation("Get Website Configs")
    @GetMapping
    @IgnoreAuthentication
    public ResponseEntity<GetWebsiteConfigResponseDto> getWebsiteConfig(
            @PathVariable("websiteId") String websiteId) throws ServiceException {
        return ResponseEntity.ok(websiteService.getWebsiteConfigs(null, websiteId));
    }

    @ApiOperation("Create Website Config")
    @PostMapping
    public ResponseEntity<CreateWebsiteConfigResponseDto> createWebsiteConfig(
            @RequestBody CreateWebsiteConfigRequestDto request,
            @PathVariable("websiteId") String websiteId)
            throws ServiceException {
        return ResponseEntity.ok(websiteService.createWebsiteConfig(websiteId, request, requestContext.getUser()));
    }

    @ApiOperation("Update Website Config")
    @PutMapping("/{configId}")
    public ResponseEntity<UpdateWebsiteConfigResponseDto> updateWebsiteConfig(
            @RequestBody UpdateWebsiteConfigRequestDto request,
            @PathVariable("websiteId") String websiteId,
            @PathVariable("configId") String configId)
            throws ServiceException {
        return ResponseEntity.ok(websiteService.updateWebsiteConfig(request, websiteId, configId,
                requestContext.getUser()));
    }

    @DeleteMapping("/{configId}")
    public ResponseEntity<Void> deleteWebsiteConfig(
            @PathVariable("websiteId") String websiteId,
            @PathVariable("configId") String configId)
            throws ServiceException {
        return ResponseEntity.ok(websiteService.deleteWebsiteConfig(websiteId, configId, requestContext.getUser()));
    }
}
