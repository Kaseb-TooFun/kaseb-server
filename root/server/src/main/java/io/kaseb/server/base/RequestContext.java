package io.kaseb.server.base;

import io.kaseb.server.operator.model.entities.OperatorEntity;
import io.kaseb.server.user.model.entities.UserEntity;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Component
@Data
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestContext {
    private String requestId = UUID.randomUUID().toString();
    private UserEntity user;
    private OperatorEntity operator;
    private String clientIp;
}
