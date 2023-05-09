package com.nsdl.telemedicine.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.nsdl.telemedicine.dto.LoginResponse;
import com.nsdl.telemedicine.dto.RedFlagData;
import com.nsdl.telemedicine.dto.RedFlagResponseDTO;
import com.nsdl.telemedicine.entity.DoctorRedFlagMarketEntity;
import com.nsdl.telemedicine.repository.RedFlagMarketRepo;

@Configuration
@EnableScheduling
public class SchedulerUtils {

	private static final Logger logger = LoggerFactory.getLogger(SchedulerUtils.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RedFlagMarketRepo redFlagRepo;

	@Value("${marketplace.url}")
	private String marketPlaceUrl;

	@Value("${marketplace.key}")
	private String marketPlaceKey;

	@Value("${marketplace.username}")
	private String marketPlaceUsername;

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Scheduled(cron = "${cron.expression}")
	public void scheduleTaskUsingCronExpression() throws ParseException {

		logger.info("scheduler running at {}", LocalDateTime.now());

		LoginResponse response = getApiToken();

		if (response == null || response.getSuccess() == null) {
			logger.info("login response failed with error {}", response.getError());
			return;
		}
		RedFlagResponseDTO redFlagResponse = getRedFlagData(response.getApi_token());

		if (redFlagResponse == null || !redFlagResponse.isSuccess()) {
			logger.info("redFlag response failed with error {}", redFlagResponse.getError());
			return;
		}
		List<String> identifierList = new ArrayList<String>();
		HashMap<Integer, RedFlagData> data = redFlagResponse.getData().getEntries();

		for (Entry<Integer, RedFlagData> entry : data.entrySet()) {
			String identifier = entry.getValue().getIdentifier();
			identifierList.add(identifier);
		}
		List<DoctorRedFlagMarketEntity> existDataMap = redFlagRepo.findByNotExistIndentifier(identifierList);

		if (!existDataMap.isEmpty()) {
			for (DoctorRedFlagMarketEntity entityData : existDataMap) {
				Iterator<Entry<Integer, RedFlagData>> iterator = data.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<Integer, RedFlagData> entry = iterator.next();
					if (entityData.getDrmIdentifier().equals(entry.getValue().getIdentifier())) {
						iterator.remove();
					}
				}
			}
		}
		List<DoctorRedFlagMarketEntity> entities = new ArrayList<DoctorRedFlagMarketEntity>();
		if (!data.isEmpty()) {
			for (Entry<Integer, RedFlagData> entry : data.entrySet()) {
				DoctorRedFlagMarketEntity entity = new DoctorRedFlagMarketEntity();
				entity.setDrmDrEmailid(
						entry.getValue().getDr_email() != null ? entry.getValue().getDr_email().toUpperCase() : null);
				entity.setDrmRedflag(entry.getValue().getRed_flag());
				entity.setDrmFormName(entry.getValue().getForm_label());
				entity.setDrmIsActive("Y");
				entity.setDrmDate(format.parse(entry.getValue().getDate_added()));
				entity.setDrmPtMobile(entry.getValue().getMobile());
				entity.setDrmIdentifier(entry.getValue().getIdentifier());
				entity.setDrmCreatedBy("Admin");
				entity.setDrmCreatedTmstmp(LocalDateTime.now());
				entity.setDrmOptiVersion(1);
				entities.add(entity);
			}
			redFlagRepo.saveAll(entities);
			logger.info("redFlag data inserted into database successfully");
		}
		logger.info("scheduler stopped at {}", LocalDateTime.now());

	}

	private RedFlagResponseDTO getRedFlagData(String token) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(marketPlaceUrl);

		builder.queryParam("api_token", token);
		builder.queryParam("route", "api/redflag/list");
		builder.queryParam("filter_entry_date", LocalDate.now());

		logger.info("redflag request {}", builder.build().encode().toUri());
		ResponseEntity<RedFlagResponseDTO> redFlagResponse = restTemplate.getForEntity(builder.build().encode().toUri(),
				RedFlagResponseDTO.class);
		return redFlagResponse.getBody();
	}

	private LoginResponse getApiToken() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("key", marketPlaceKey);
		map.add("username", marketPlaceUsername);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(marketPlaceUrl);

		builder.queryParam("route", "api/login");
		logger.info("get API request {}", builder.build().encode().toUri());
		ResponseEntity<LoginResponse> response = restTemplate.postForEntity(builder.build().encode().toUri(), request,
				LoginResponse.class);
		logger.info("get API response {}", response.getBody().toString());
		return response.getBody();
	}

}
