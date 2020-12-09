package io.kaseb.server.verify.controller;

import io.kaseb.server.authenticate.model.annotations.IgnoreAuthentication;
import io.kaseb.server.verify.response.VerifyResponseDto;
import io.kaseb.server.verify.service.VerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@RestController
@RequestMapping("/api/v1/verify")
@RequiredArgsConstructor
public class VerifyController {
	private final VerifyService verifyService;

	@GetMapping
	@IgnoreAuthentication
	public ResponseEntity<VerifyResponseDto> verify() {
		return ResponseEntity.ok(verifyService.verify());
	}
}
