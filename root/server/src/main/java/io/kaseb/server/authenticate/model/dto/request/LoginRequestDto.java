package io.kaseb.server.authenticate.model.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequestDto {
	private String username;
	private String password;
	private boolean isOperator;
}
