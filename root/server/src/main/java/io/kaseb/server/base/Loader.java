package io.kaseb.server.base;

import io.kaseb.server.operator.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Loader implements ApplicationListener<ContextRefreshedEvent> {
	private OperatorService operatorService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		operatorService.initializeSysadmin();
	}

	@Autowired
	public void setOperatorService(OperatorService operatorService) {
		this.operatorService = operatorService;
	}
}
