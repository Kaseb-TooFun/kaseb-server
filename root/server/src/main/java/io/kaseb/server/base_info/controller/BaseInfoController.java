package io.kaseb.server.base_info.controller;

import io.kaseb.server.authenticate.model.annotations.IgnoreAuthentication;
import io.kaseb.server.base_info.service.BaseInfoService;
import io.kaseb.server.exceptions.model.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@RestController
@RequestMapping("/api/v1/base-info")
@IgnoreAuthentication
@RequiredArgsConstructor
public class BaseInfoController {
    private final BaseInfoService baseInfoService;

    @GetMapping("/errors")
    public ResponseEntity<List<ErrorResponse>> errors() {
        return ResponseEntity.ok(baseInfoService.getErrors());
    }
}
