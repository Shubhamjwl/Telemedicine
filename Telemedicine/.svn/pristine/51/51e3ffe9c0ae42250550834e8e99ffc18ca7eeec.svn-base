package com.nsdl.telemedicine.gateway.config;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;


@Configuration
public class BeanConfig {

	/*@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes().route(r -> r.path("/login/**")  //usermanagement service URL added by sayali
				.filters(f -> f
						.rewritePath("/login/(?<remains>.*)", "/usermanagement/v1/user/login/${remains}"))
						//.hystrix(c -> c.setName("signin").setFallbackUri("forward:/fallback/signin")))
				.uri("http://172.30.151.100:7072/").id("user-management-service"))
				.route(r -> r.path("/roleFunction/**").filters(
						f -> f.rewritePath("/roleFunction/(?<remains>.*)", "/usermanagement/v1/user/roleFunction/${remains}"))
						.uri("lb://USER-MANAGEMENT-SERVICE/").id("user-management-service"))
				.route(r -> r.path("/verifyDoctor/**").filters(
						f -> f.rewritePath("/verifyDoctor/(?<remains>.*)", "/usermanagement/v1/user/verifyDoctor/${remains}"))
						.uri("lb://USER-MANAGEMENT-SERVICE/").id("user-management-service"))
				.route(r -> r.path("/function/**").filters(
						f -> f.rewritePath("/function/(?<remains>.*)", "/usermanagement/v1/user/function/${remains}"))
						.uri("lb://USER-MANAGEMENT-SERVICE/").id("user-management-service"))
				.route(r -> r.path("/role/**").filters(
						f -> f.rewritePath("/role/(?<remains>.*)", "/usermanagement/v1/user/role/${remains}"))
						.uri("lb://USER-MANAGEMENT-SERVICE/").id("user-management-service")) //usermanagement ends
				//captcha-registration-service added by sayali
				.route(r -> r.path("/captcha/**").filters(
						f -> f.rewritePath("/captcha/(?<remains>.*)", "/captcha/${remains}"))
						.uri("lb://CAPTCHA-SERVICE/").id("captcha-service"))				//capctha service ends
				//doctor-registration-service added by sayali
				.route(r -> r.path("/DoctorRegistration/v1/**").filters(
						f -> f.rewritePath("/DoctorRegistration/v1/(?<remains>.*)", "/doctor-registration/DoctorRegistration/v1/${remains}"))
						.uri("lb://DOCTOR-REGISTRATION-SERVICE/").id("doctor-registration-service"))
				.route(r -> r.path("/DoctorDeRegistration/v1/**").filters(
						f -> f.rewritePath("/DoctorDeRegistration/v1/(?<remains>.*)", "/doctor-registration/DoctorDeRegistration/v1/${remains}"))
						.uri("lb://DOCTOR-REGISTRATION-SERVICE/").id("doctor-registration-service"))   //doctor service ends
				//added by girishk start
				.route(r -> r.path("/appointment/**").filters(
						f -> f.rewritePath("/appointment/(?<remains>.*)", "/consultation/appointment/${remains}")) //consultation manager service.
						.uri("lb://CONSULTATION-MANAGER-SERVICE/").id("consultation-manager-service"))
				.route(r -> r.path("/review/v1/**").filters(
						f -> f.rewritePath("/review/v1/(?<remains>.*)", "/PatientReview/review/v1/${remains}")) //patient review service
						.uri("lb://PATIENT-REVIEW-SERVICE/").id("patient-review-service"))
				.route(r -> r.path("/scribe-service/v1/**").filters(
						f -> f.rewritePath("/scribe-service/v1/(?<remains>.*)", "/scribe-service/v1/${remains}"))
						.uri("lb://SCRIBE-REGISTRATION-SERVICE/").id("scribe-registration-service")) //scribe registration service.
				.route(r -> r.path("/patientRegistration/**").filters(
						f -> f.rewritePath("/patientRegistration/(?<remains>.*)", "/patient/v1/patientRegistration/${remains}"))
						.uri("lb://PATIENT-REGISTRATION-SERVICE/").id("patient-registration-service")) //patient registration service.
				.route(r -> r.path("/patientModification/**").filters(
						f -> f.rewritePath("/patientModification/(?<remains>.*)", "/patient/v1/patientModification/${remains}"))
						.uri("lb://PATIENT-REGISTRATION-SERVICE/").id("patient-registration-service")) //patient registration service.
				//added by girishk end
				//slot/master managemnet added by sayali
				.route(r -> r.path("/slotManagement/**").filters(
						 f -> f.rewritePath("/slotManagement/(?<remains>.*)", "/slotManagement/v1/${remains}"))
				         .uri("lb://SLOT-MANAGEMENT/").id("slot-management")) 							//slot-management integration
				                                                               
				.route(r -> r.path("/masterManagement/**").filters(
						 f -> f.rewritePath("/masterManagement/(?<remains>.*)", "/masterManagement/v1/${remains}"))
				         .uri("lb://MASTER-MANAGEMENT-SERVICE/").id("master-management-service")) 		//master-management integration
						 //added by swatis
				.route(r -> r.path("/OTPManager/**").filters(
						f -> f.rewritePath("/OTPManager/(?<remains>.*)", "/OTPManager/${remains}")) //captcha service integration
						.uri("lb://OTP-MANAGER-SERVICE/").id("otp-manager-service"))
				
				.route(r -> r.path("/videoconference/**").filters(
						f -> f.rewritePath("/videoconference/(?<remains>.*)", "/videoconference/${remains}")) //captcha service integration
						.uri("lb://VIDEO-CALL-CONFERENCE-SERVICE/").id("video-call-conference-service"))
				//added by swatis end 
				.route(r -> r.path("/PatientReportModule/**").filters(
						f -> f.rewritePath("/PatientReportModule/(?<remains>.*)", "/PatientReportModule/${remains}"))
						.uri("lb://PATIENT-REPORT-SERVICE/").id("patient-report-service"))
						//added by sayali 
				.route(r -> r.path("/book/**").filters(
						f -> f.rewritePath("/book/(?<remains>.*)", "/book/v1/${remains}"))
						.uri("lb://BOOKING-APPOINTMENT/").id("Booking-Appointment"))
				.build();
	}*/

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		// Do any additional configuration here
		return builder.build();
	}

	@Bean
	public WebFilter corsFilter() {
		return (ServerWebExchange ctx, WebFilterChain chain) -> {
			ServerHttpRequest request = ctx.getRequest();
			if (CorsUtils.isCorsRequest(request)) {
				ServerHttpResponse response = ctx.getResponse();
				HttpHeaders headers = response.getHeaders();

				String origin = request.getHeaders().getFirst(HttpHeaders.ORIGIN);
				headers.add("Access-Control-Allow-Origin", origin);
				headers.add("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
				headers.add("Access-Control-Max-Age", "3600");
				headers.add("Access-Control-Allow-Headers",
						"Date, Content-Type, Accept, X-Requested-With, Authorization, From, X-Auth-Token, Request-Id, sessionId, sessionid, captchaValue, flagValue");
				headers.add("Access-Control-Allow-Credentials", "true");
				if (request.getMethod() == HttpMethod.OPTIONS) {
					response.setStatusCode(HttpStatus.OK);
					return Mono.empty();
				}
			}
			return chain.filter(ctx);
		};
	}

}
