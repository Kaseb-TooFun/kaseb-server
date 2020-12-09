package io.kaseb.server.authenticate.model.dto.request;

import lombok.Data;

@Data
public class SignupRequestDto {
	private String username;
	private String password;
	private boolean isOperator;
}
