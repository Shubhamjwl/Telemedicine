package com.nsdl.telemedicine.gateway.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.telemedicine.gateway.dto.FunctionDetails;

@Configuration
public class GatewayConfig {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AuthProperties authProperties;

	private Map<String, List<FunctionDetails>> mapOfObjects;

	// @Bean(name = "roleFunc")
	public void getRoles() {
		SSLVerification.disableSslVerification();
		ResponseEntity<String> response = restTemplate.exchange(authProperties.getToken().getRoleFunctionUrl(),
				HttpMethod.GET, null, String.class);
		String responseStr = response.getBody();
		Map<String, List<FunctionDetails>> mapOfRoleFunction = new HashMap<>();
		List<FunctionDetails> listOfFunctions = null;
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(responseStr);
			if (jsonObject instanceof JSONObject) {
				Iterator<String> keyOfRoles = jsonObject.keys();
				while (keyOfRoles.hasNext()) {
					String currentKey = (String) keyOfRoles.next();
					JSONArray arrayOfRole = (JSONArray) jsonObject.getJSONArray(currentKey);
					ObjectMapper mapper = new ObjectMapper();
					listOfFunctions = mapper.readValue(arrayOfRole.toString(),
							new TypeReference<List<FunctionDetails>>() {
							});
					mapOfRoleFunction.put(currentKey, listOfFunctions);
				}
			}
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}

		mapOfObjects = mapOfRoleFunction;
	}

	public Map<String, List<FunctionDetails>> getRoleFunctionMap() {
		return mapOfObjects;
	}

}
