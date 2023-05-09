package com.nsdl.telemedicine.gateway.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@ConfigurationProperties(prefix = "function")
@Data
@NoArgsConstructor
public class FunctionIdUriMap {

	private Map<String, String> functionUriMap;

}
