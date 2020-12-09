package io.kaseb.server.base;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Service
@RequiredArgsConstructor
public class MessageService {
	private final MessageSource messageSource;

	public String getMessage(String code) {
		return getMessage(code, new Object[]{});
	}

	public String getMessage(String code, Object[] args) {
		Locale farsiLocale = new Locale("fa", "IR");
		return getMessage(code, args, farsiLocale);
	}

	public String getMessage(String code, Object[] args, Locale locale) {
		return messageSource.getMessage(code, args, code, locale);
	}
}
