package io.kaseb.server.config;

import io.kaseb.server.authenticate.model.annotations.IgnoreAuthentication;
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
        boolean checkAuthentication = true;
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            if (method.isAnnotationPresent(IgnoreAuthentication.class))
                checkAuthentication = false;
        }
        if (checkAuthentication)
            requestContext.setUser(authenticateService.authenticate(request));
        return true;
    }
}