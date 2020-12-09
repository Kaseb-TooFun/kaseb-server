package io.kaseb.server.verify.response;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@ToString
@Getter
public class VerifyResponseDto {
	private final String date;
	private final boolean up;

	public VerifyResponseDto(boolean up) {
		this.date = LocalDate.now().toString();
		this.up = up;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof VerifyResponseDto)) return false;
		VerifyResponseDto that = (VerifyResponseDto) o;
		return up == that.up;
	}

	@Override
	public int hashCode() {
		return Objects.hash(up);
	}
}
