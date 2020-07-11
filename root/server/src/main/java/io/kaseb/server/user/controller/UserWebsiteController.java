package io.kaseb.server.user.controller;

import io.kaseb.server.base.RequestContext;
import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.user.model.dto.request.RegisterWebsiteConfigRequestDto;
import io.kaseb.server.user.model.dto.request.RegisterWebsiteRequestDto;
import io.kaseb.server.user.model.dto.request.UpdateWebsiteRequestDto;
import io.kaseb.server.user.model.dto.response.GetWebsitesResponseDto;
import io.kaseb.server.user.model.dto.response.RegisterWebsiteResponseDto;
import io.kaseb.server.user.model.dto.response.UpdateWebsiteResponseDto;
import io.kaseb.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@RestController
@RequestMapping("/api/v1/users/websites")
@RequiredArgsConstructor
public class UserWebsiteController {
    private final UserService userService;
    private final RequestContext requestContext;

    @PostMapping
    public ResponseEntity<RegisterWebsiteResponseDto> registerWebsite(@RequestBody RegisterWebsiteRequestDto request)
            throws ServiceException {
        return ResponseEntity.ok(userService.registerWebsite(request, requestContext.getUser()));
    }

    @PostMapping("{websiteId}/configs")
    public ResponseEntity<RegisterWebsiteResponseDto> registerWebsite(
            @PathVariable("websiteId") String websiteId,
            @RequestBody RegisterWebsiteConfigRequestDto request)
            throws ServiceException {
        return ResponseEntity.ok(userService.registerWebsiteConfig(request, websiteId, requestContext.getUser()));
    }

    @GetMapping
    public ResponseEntity<GetWebsitesResponseDto> getWebsites() throws ServiceException {
        return ResponseEntity.ok(userService.getWebsite(requestContext.getUser()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateWebsiteResponseDto> updateWebsite(
            @RequestBody UpdateWebsiteRequestDto request,
            @PathVariable("id") String id)
            throws ServiceException {
        return ResponseEntity.ok(userService.updateWebsite(request, id, requestContext.getUser()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> updateWebsite(
            @PathVariable("id") String id)
            throws ServiceException {
        return ResponseEntity.ok(userService.deleteWebsite(id, requestContext.getUser()));
    }

}
