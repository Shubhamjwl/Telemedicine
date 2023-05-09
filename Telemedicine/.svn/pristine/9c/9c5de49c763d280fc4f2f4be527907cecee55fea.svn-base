package com.nsdl.telemedicine.gateway;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import com.nsdl.telemedicine.gateway.config.GatewayConfig;


public class BootListener implements ApplicationListener<ApplicationReadyEvent> {
	@Override
	public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
		applicationReadyEvent.getApplicationContext().getBean(GatewayConfig.class).getRoles();
	}
}
