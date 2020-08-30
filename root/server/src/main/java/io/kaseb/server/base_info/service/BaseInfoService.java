package io.kaseb.server.base_info.service;

import io.kaseb.server.base.MessageService;
import io.kaseb.server.exceptions.model.ErrorResponse;
import io.kaseb.server.exceptions.model.ServiceExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Service
@RequiredArgsConstructor
public class BaseInfoService {
    private final MessageService messageService;

    public List<ErrorResponse> getErrors() {
        return Arrays.stream(ServiceExceptions.values()).map(ErrorResponse::new).collect(Collectors.toList());
    }
}
