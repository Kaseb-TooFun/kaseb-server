package io.kaseb.server.website.controller;

import io.kaseb.server.base.RequestContext;
import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.user.model.dto.request.RegisterWebsiteRequestDto;
import io.kaseb.server.user.model.dto.request.UpdateWebsiteRequestDto;
import io.kaseb.server.user.model.dto.response.GetWebsitesResponseDto;
import io.kaseb.server.user.model.dto.response.RegisterWebsiteResponseDto;
import io.kaseb.server.user.model.dto.response.UpdateWebsiteResponseDto;
import io.kaseb.server.website.model.dto.response.GetWebsiteResponseDto;
import io.kaseb.server.website.service.WebsiteService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@RequestMapping("/api/v1/websites")
@RestController
@RequiredArgsConstructor
public class WebsiteController {
    private final WebsiteService websiteService;
    private final RequestContext requestContext;

    @ApiOperation("Create Website")
    @PostMapping
    public ResponseEntity<RegisterWebsiteResponseDto> createWebsite(@RequestBody RegisterWebsiteRequestDto request)
            throws ServiceException {
        return ResponseEntity.ok(websiteService.registerWebsite(request, requestContext.getUser()));
    }

    @ApiOperation("Get Websites")
    @GetMapping
    public ResponseEntity<GetWebsitesResponseDto> getWebsites() {
        return ResponseEntity.ok(websiteService.getWebsites());
    }

    @ApiOperation("Get Website By Id")
    @GetMapping("/{websiteId}")
    public ResponseEntity<GetWebsiteResponseDto> getWebsites(
            @PathVariable("websiteId") String websiteId) throws ServiceException {
        return ResponseEntity.ok(websiteService.getWebsite(websiteId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateWebsiteResponseDto> updateWebsite(
            @RequestBody UpdateWebsiteRequestDto request,
            @PathVariable("id") String id)
            throws ServiceException {
        return ResponseEntity.ok(websiteService.updateWebsite(request, id, requestContext.getUser()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWebsite(
            @PathVariable("id") String id)
            throws ServiceException {
        return ResponseEntity.ok(websiteService.deleteWebsite(id, requestContext.getUser()));
    }

}
