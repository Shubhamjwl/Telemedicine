
package com.nsdl.verifier.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.nsdl.verifier.security.AuthenticationManager;
import com.nsdl.verifier.security.SecurityContextRepository;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Autowired
	private SecurityContextRepository securityContextRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Bean
	protected SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
		return http.cors().and().exceptionHandling().authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> {
			swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		})).accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> {
			swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
		})).and().csrf().disable().authenticationManager(authenticationManager)
				.securityContextRepository(securityContextRepository).authorizeExchange()
				.pathMatchers("/verifier/v1/loginVerifier", "/verifier/v1/sync", "/verifier/v1/getBioDetails")
				.permitAll().pathMatchers(HttpMethod.OPTIONS).permitAll().anyExchange().authenticated().and().build();
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
