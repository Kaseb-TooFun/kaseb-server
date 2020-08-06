package io.kaseb.server.operator.controller;

import io.kaseb.server.authenticate.model.annotations.AuthenticationRequired;
import io.kaseb.server.authenticate.model.enums.Role;
import io.kaseb.server.operator.model.dto.OperatorDto;
import io.kaseb.server.operator.service.OperatorService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RequestMapping("/api/v1/operator")
@RestController
@RequiredArgsConstructor
public class OperatorController {
    private final OperatorService operatorService;

    @GetMapping
    @ApiOperation("Get Operators List")
    @AuthenticationRequired(role = Role.OPERATOR)
    public ResponseEntity<List<OperatorDto>> getOperatorsList() {
        return ok(operatorService.getOperatorsList());
    }
}
