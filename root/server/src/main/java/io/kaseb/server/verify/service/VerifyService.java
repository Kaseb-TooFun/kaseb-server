package io.kaseb.server.verify.service;

import io.kaseb.server.verify.response.VerifyResponseDto;
import org.springframework.stereotype.Service;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Service
public class VerifyService {
    public VerifyResponseDto verify() {
        return new VerifyResponseDto(true);
    }
}
