package io.kaseb.server.website.controller;

import io.kaseb.server.authenticate.model.annotations.IgnoreAuthentication;
import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.website.model.dto.response.GetWebsiteConfigResponseDto;
import io.kaseb.server.website.service.WebsiteService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@RequestMapping("/api/v1/website")
@RestController
@RequiredArgsConstructor
public class WebsiteController {
    private final WebsiteService websiteService;

    @ApiOperation("Get Website Configs")
    @GetMapping("/configs")
    @IgnoreAuthentication
    public ResponseEntity<GetWebsiteConfigResponseDto> getWebsiteConfig(
            @RequestParam("websiteUrl") String websiteUrl) throws ServiceException {
        return ResponseEntity.ok(websiteService.getWebsiteConfigs(websiteUrl));
    }
}
