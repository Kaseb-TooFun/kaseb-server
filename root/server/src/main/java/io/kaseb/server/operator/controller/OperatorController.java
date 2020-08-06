package io.kaseb.server.operator.controller;

import io.kaseb.server.authenticate.model.annotations.AuthenticationRequired;
import io.kaseb.server.authenticate.model.enums.Role;
import io.kaseb.server.base.RequestContext;
import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.operator.model.dto.BaseModuleDto;
import io.kaseb.server.operator.model.dto.OperatorDto;
import io.kaseb.server.operator.model.dto.request.CreateModuleRequestDto;
import io.kaseb.server.operator.service.OperatorService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RequestMapping("/api/v1/operator")
@RestController
@RequiredArgsConstructor
public class OperatorController {
    private final RequestContext requestContext;
    private final OperatorService operatorService;

    @GetMapping
    @ApiOperation("Get Operators List")
    @AuthenticationRequired(role = Role.OPERATOR)
    public ResponseEntity<List<OperatorDto>> getOperatorsList() {
        return ok(operatorService.getOperatorsList());
    }

    @PutMapping("/{operatorId}/activate")
    @ApiOperation("Active/deActive Operator")
    @AuthenticationRequired(role = Role.OPERATOR)
    public ResponseEntity<Void> activateOperator(
            @PathVariable("operatorId") String operatorId,
            @RequestParam(name = "activate", required = false, defaultValue = "true") boolean activate)
            throws ServiceException {
        return ok(operatorService.activateOperator(requestContext.getOperator(), operatorId, activate));
    }

    @PostMapping("/modules")
    @ApiOperation("Set module")
    @AuthenticationRequired(role = Role.OPERATOR)
    public ResponseEntity<BaseModuleDto> createModule(
            @RequestBody CreateModuleRequestDto requestDto)
            throws ServiceException {
        return ok(operatorService.createModule(requestContext.getOperator(), requestDto));
    }
}
