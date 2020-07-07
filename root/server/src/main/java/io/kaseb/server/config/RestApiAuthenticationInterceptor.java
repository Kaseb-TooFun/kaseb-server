package io.kaseb.server.config;

import io.kaseb.server.authenticate.service.AuthenticateService;
import io.kaseb.server.base.RequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        requestContext.setUser(authenticateService.authenticate(request));
        return true;
    }
}