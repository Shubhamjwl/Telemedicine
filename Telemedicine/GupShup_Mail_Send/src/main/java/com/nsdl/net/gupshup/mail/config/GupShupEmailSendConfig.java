package com.nsdl.net.gupshup.mail.config;

import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

//@Configuration
public class GupShupEmailSendConfig {

	@Value("${proxy.url}")
	private String proxyUrl;

	@Value("${proxy.port}")
	private Integer proxyPort;
	
	@Value("${proxy.flag.active}")
	private String flagForProxy;
	
	@Value("${spring.profiles.active}")
	private String env;
	
	@Value("${gupshup.mail.flag.vt}")
	private String flagForVTEnv;
	
	@Value("${gupshup.mail.flag.dev}")
	private String flagForDevEnv;

	@Bean(name = "restTemplateProxy")
	public RestTemplate getRestTemplateProxy() {
		
		String flag = "";
		if(env.equals("vt")) {
			flag = flagForVTEnv;
		}else if(env.equals("dev") || env.equals("uat")){
			flag = flagForDevEnv;
		}
		
		if(flagForProxy.equalsIgnoreCase("Y") || flag.equals("Y")) {
			TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
			SSLContext sslContext = null;
			try {
				sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
			} catch (Exception e) {
				e.printStackTrace();
			}
			HttpHost proxy = new HttpHost(proxyUrl, proxyPort);
			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).setProxy(proxy)
					.setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setHttpClient(httpClient);
			RestTemplate restTemplate = new RestTemplate(requestFactory);
			System.setProperty("https.protocols", "TLSv1.2");
	
			return restTemplate;
		}else {
			return new RestTemplate();
		}
	}

}
