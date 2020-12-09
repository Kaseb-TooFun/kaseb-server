package io.kaseb.server.config;

import io.kaseb.server.authenticate.exceptions.AdminAuthenticationException;
import io.kaseb.server.authenticate.exceptions.AuthenticationException;
import io.kaseb.server.authenticate.exceptions.OperatorAuthenticationException;
import io.kaseb.server.authenticate.model.annotations.AuthenticationRequired;
import io.kaseb.server.authenticate.model.annotations.IgnoreAuthentication;
import io.kaseb.server.authenticate.model.entities.SessionEntity;
import io.kaseb.server.authenticate.model.enums.Role;
import io.kaseb.server.authenticate.service.AuthenticateService;
import io.kaseb.server.base.RequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RestApiAuthenticationInterceptor implements HandlerInterceptor {
	private final AuthenticateService authenticateService;
	private final RequestContext requestContext;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		requestContext.setClientIp(request.getRemoteAddr());
		if (handler instanceof HandlerMethod) {
			Method method = ((HandlerMethod) handler).getMethod();
			if (!method.isAnnotationPresent(IgnoreAuthentication.class)) {
				AuthenticationRequired annotation = method.getAnnotation(AuthenticationRequired.class);
				authenticate(request, annotation == null ? null : annotation.role());
			}
		}
		return true;
	}

	private void authenticate(HttpServletRequest request, Role role)
			throws AuthenticationException, AdminAuthenticationException, OperatorAuthenticationException {
		final SessionEntity sessionEntity = authenticateService.authenticate(request);
		if (Role.ADMIN.equals(role)) {
			if (sessionEntity.getUser() == null)
				throw new AdminAuthenticationException();
			requestContext.setUser(sessionEntity.getUser());
		} else if (Role.OPERATOR.equals(role)) {
			if (sessionEntity.getOperator() == null)
				throw new OperatorAuthenticationException();
			requestContext.setOperator(sessionEntity.getOperator());
		} else {
			requestContext.setOperator(sessionEntity.getOperator());
			requestContext.setUser(sessionEntity.getUser());
		}
	}
}