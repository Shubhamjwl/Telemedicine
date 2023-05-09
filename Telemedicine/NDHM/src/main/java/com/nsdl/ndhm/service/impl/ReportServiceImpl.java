package com.nsdl.ndhm.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nsdl.ndhm.dto.datareport.*;
import com.nsdl.ndhm.logger.LoggingClientInfo;
import com.nsdl.ndhm.service.ReportService;
import com.nsdl.ndhm.utility.CommonHeadersUtil;
import com.nsdl.ndhm.utility.fhir.files.opconconsult.OPConsultNote;
import com.nsdl.ndhm.utility.fhir.files.opconconsult.two.OPConsultNoteTwo;
import com.nsdl.ndhm.utility.fhir.files.prescrip.Prescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Stream;

@Service
@LoggingClientInfo
public class ReportServiceImpl implements ReportService {
	private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	CommonHeadersUtil commonHeadersUtil;

	@Autowired
	public ObjectMapper objectMapper;

	@Autowired
	Prescription prescription;

	@Autowired
	OPConsultNoteTwo opConsultNoteTwo;

	@Value("${ndhm.telemedicine.getReportsByCareContextIds}")
	String getReportsByCareContextIds;

	@Override
	public Map<String, List<String>> getReportData(RequestWrapper<?> reportRequestDTO) throws IOException {
		logger.info("Request Receives for getReportData");
		String url = getReportsByCareContextIds;
		URI uri;
		Map<String, List<String>> fhirMap = new HashMap<>();
		List<String> fhirStringList;
		ResponseEntity<String> result;
		ResponseWrapper respData = null;
		ReportResponseDto reportResponseDto = null;
		List<ReportResponseDtls> responseDtlsList;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		logger.info("getReportsByCareContextIds {}", getReportsByCareContextIds);

		try {
			uri = new URI(url);
			String encryptedString = new Gson().toJson(reportRequestDTO);
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString, headers);

			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			respData = new Gson().fromJson(result.getBody(), ResponseWrapper.class); // Json to response mapping
			reportResponseDto = objectMapper.convertValue(respData.getResponse(),
					new TypeReference<ReportResponseDto>() {
					});
		} catch (HttpClientErrorException | HttpServerErrorException | NullPointerException e) {
			logger.error("Error in getReportData {}", e);
		} catch (Exception e) {
			logger.error("Error in getReportData {}", e);
		}
		logger.info("ReportData get Successfully");


      /* For Prescription Record */
/*		if (!(reportResponseDto == null && reportResponseDto.getPatient() == null)) {

			if (Stream.of(reportResponseDto.getPatient().getAbhaId(), reportResponseDto.getPatient().getName(),
						reportResponseDto.getPatient().getGender(), reportResponseDto.getPatient().getDob(),
						reportResponseDto.getPatient().getMob()).allMatch(Objects::nonNull)) {

			for (CareContextResponse careContextResponse : reportResponseDto.getCareContextDetails()) {
				fhirStringList = new ArrayList<>();

				if ((careContextResponse.getDoctorDetails() != null)
						&& Stream.of(careContextResponse.getDoctorDetails().getDrId(),
										careContextResponse.getDoctorDetails().getDrName(),
										careContextResponse.getDoctorDetails().getEmail(),
										careContextResponse.getDoctorDetails().getMob(),
										careContextResponse.getDoctorDetails().getMciNo(),
										careContextResponse.getDoctorDetails().getSmcNo(),
										careContextResponse.getDoctorDetails().getSpeciality())
								.allMatch(Objects::nonNull)) {

					for (ReportResponseDtls reportDtls : careContextResponse.getReportResponseDtls()) {

						if ((reportDtls != null) && Stream
								.of(reportDtls.getReport_type(), reportDtls.getReport_name(),
										reportDtls.getReport_doc(), reportDtls.getDocCreationTimestamp())
								.allMatch(Objects::nonNull)) {

							String reportType = reportDtls.getReport_type();

							if (reportType.equalsIgnoreCase("Prescription PDF") ||
									  reportType.equalsIgnoreCase("Blood Report") ||
									reportType.equalsIgnoreCase("ECG Report")) {
								fhirStringList.add(prescription.getFhirString(reportResponseDto.getPatient(),
										careContextResponse.getDoctorDetails(), reportDtls));

								fhirStringList.add(opConsultNote.getFhirString(reportResponseDto.getPatient(),
										careContextResponse.getDoctorDetails(), reportDtls));
							}
						} else {
							logger.info("Report field is null");
							fhirMap = new HashMap<>();
							break;
						}

						fhirMap.put(careContextResponse.getCareContextId(), fhirStringList);
					}
				}

				else {
					logger.info("Doctor field is null");
					fhirMap = new HashMap<>();
					break;
				}
			}
		} else {
				logger.info("Patient field is null");
				fhirMap = new HashMap<>();
			}
		} else {
			logger.info("ReportResponse or Patient details is null");
			fhirMap = new HashMap<>();
		}*/

		/* For OPConsult Note */
		if (reportResponseDto != null) {

			if (reportResponseDto.getPatient() != null) {

				if (Stream.of(reportResponseDto.getPatient().getAbhaId(), reportResponseDto.getPatient().getName(),
						reportResponseDto.getPatient().getGender(), reportResponseDto.getPatient().getDob(),
						reportResponseDto.getPatient().getMob()).allMatch(Objects::nonNull)) {

					    logger.info("CarecxtDetails: {}", reportResponseDto.getCareContextDetails().size());
					for (CareContextResponse careContextResponse : reportResponseDto.getCareContextDetails()) {
						fhirStringList = new ArrayList<>();
						responseDtlsList = new ArrayList<>();

						if ((careContextResponse.getDoctorDetails() != null)
								&& Stream.of(careContextResponse.getCareContextId(),
										careContextResponse.getCareContextCreationTimestamp(),
										careContextResponse.getDoctorDetails().getDrId(),
										careContextResponse.getDoctorDetails().getDrName(),
										careContextResponse.getDoctorDetails().getEmail(),
										careContextResponse.getDoctorDetails().getMob(),
										careContextResponse.getDoctorDetails().getMciNo(),
										careContextResponse.getDoctorDetails().getSmcNo(),
										careContextResponse.getDoctorDetails().getSpeciality())
								.allMatch(Objects::nonNull)) {

							logger.info("ReportDetails: {}", careContextResponse.getReportResponseDtls().size());
							for (ReportResponseDtls reportDtls : careContextResponse.getReportResponseDtls()) {

								if ((reportDtls != null) && Stream.of(reportDtls.getReport_type(),
												reportDtls.getReport_name(), reportDtls.getReport_doc(),
												reportDtls.getDocCreationTimestamp())
										.allMatch(Objects::nonNull)) {

									responseDtlsList.add(reportDtls);
								}
							}

							fhirStringList.add(opConsultNoteTwo.getFhirString(careContextResponse.getCareContextId(),
									careContextResponse.getCareContextCreationTimestamp(),
									reportResponseDto.getPatient(),
									careContextResponse.getDoctorDetails(), responseDtlsList));

							fhirMap.put(careContextResponse.getCareContextId(), fhirStringList);
						} else {
							logger.info("Doctor or CareContext field is null");
							fhirMap = new HashMap<>();
							break;
						}
					}
				} else {
					logger.info("Patient field is null");
					fhirMap = new HashMap<>();
				}
			}
			else {
				logger.info("Patient details is null");
				fhirMap = new HashMap<>();
			}
		}

		else {
			logger.info("Report Response details is null");
			fhirMap = new HashMap<>();
		}

		return fhirMap;
	}

	public ReportResponseDto getDataReportResponseDto() {

		/*
		 * RequestWrapper<ReportRequestDto> reportRequestDTO = new RequestWrapper<>();
		 * reportRequestDTO.setRequest(ReportRequestDto.builder()
		 * .abhaId("rahulnew21@sbx") .careContextIds(Arrays.asList(new
		 * CareContextDtls("2022030310113750"), new
		 * CareContextDtls("2022030310111471"))).build());
		 * 
		 * ReportResponseDto reportResponseDto = getReportData(reportRequestDTO);
		 * PatientRegDetailsDTO patientRegDetailsDTO = reportResponseDto.getPatient();
		 * logger.info("PatientRegDetailsDTO: {}",patientRegDetailsDTO);
		 * 
		 * List<CareContextResponse> careContextResponseList =
		 * reportResponseDto.getCareContextDetails(); for (int i = 0; i <
		 * careContextResponseList.size(); i++) { CareContextResponse
		 * careContextResponse = careContextResponseList.get(i); DoctorDetailsDto
		 * doctorDetailsDto = careContextResponse.getDoctorDetails();
		 * logger.info("DoctorDetailsDto: {}",doctorDetailsDto);
		 * List<ReportResponseDtls> reportResponseDtls =
		 * careContextResponse.getReportResponseDtls();
		 * 
		 * for (int j = 0; j < reportResponseDtls.size(); j++) { String reportType =
		 * reportResponseDtls.get(j).getReport_type();
		 * logger.info("Report Type: {}",reportType);
		 * if(reportType.equalsIgnoreCase("Prescription PDF")){
		 * 
		 * } } }
		 * 
		 * return reportResponseDto;
		 */
		return null;
	}
}
