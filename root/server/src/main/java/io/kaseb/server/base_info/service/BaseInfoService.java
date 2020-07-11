package io.kaseb.server.base_info.service;

import io.kaseb.server.exceptions.model.ErrorResponse;
import io.kaseb.server.exceptions.model.ServiceExceptions;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Service
public class BaseInfoService {

    public List<ErrorResponse> getErrors() {
        return Arrays.stream(ServiceExceptions.values()).map(ErrorResponse::new).collect(Collectors.toList());
    }
}
