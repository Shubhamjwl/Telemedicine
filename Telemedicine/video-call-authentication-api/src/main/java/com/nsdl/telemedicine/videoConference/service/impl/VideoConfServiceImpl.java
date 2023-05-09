package com.nsdl.telemedicine.videoConference.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nsdl.telemedicine.videoConference.constant.AuthConstant;
import com.nsdl.telemedicine.videoConference.dto.InvitationRequest;
import com.nsdl.telemedicine.videoConference.dto.InvitationResponse;
import com.nsdl.telemedicine.videoConference.dto.InviteServiceMainRequestDTO;
import com.nsdl.telemedicine.videoConference.dto.InviteServiceMainResponseDTO;
import com.nsdl.telemedicine.videoConference.dto.InviteServiceRequest;
import com.nsdl.telemedicine.videoConference.dto.InviteServiceResponse;
import com.nsdl.telemedicine.videoConference.dto.VideoConfRequestDTO;
import com.nsdl.telemedicine.videoConference.dto.VideoConfResponse;
import com.nsdl.telemedicine.videoConference.entity.AppointmentDtlsEntity;
import com.nsdl.telemedicine.videoConference.logger.LoggingClientInfo;
import com.nsdl.telemedicine.videoConference.repository.AppointmentDtlsRepository;
import com.nsdl.telemedicine.videoConference.service.VideoConfService;
import com.nsdl.telemedicine.videoConference.utility.DateTimeUtil;
import com.nsdl.telemedicine.videoConference.utility.EmptyCheckUtility;
import com.nsdl.telemedicine.videoConference.utility.EncryptionUtil;
import com.nsdl.telemedicine.videoConference.utility.MD5HashUtil;

@LoggingClientInfo
@Service
public class VideoConfServiceImpl implements VideoConfService {

	@Value("${videoConference.serice.videoconf.fixedTxt}")
	private String fixedTxt;

	@Value("${videoConference.serice.videoconf.secretKey}")
	private String secretKey;

	@Value("${videoConference.url}")
	private String videoConferenceUrl;

	@Value("${invitation.service.url}")
	private String invitationServiceURL;

	@Value("${videoConference.url.encode.string}")
	private String videoConferenceUrlEncodeString;
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AppointmentDtlsRepository appointmentDtlsRepository;

	private static final Logger logger = LoggerFactory.getLogger(VideoConfServiceImpl.class);

	@Override
	public VideoConfResponse startConf(VideoConfRequestDTO request) {

		LocalDate appointmentDate = null;
		String slotTime = null;
		VideoConfResponse videoConfResponse = new VideoConfResponse();

		logger.info("Request received for start video conf service");
		AppointmentDtlsEntity appointmentDtlsEntity = appointmentDtlsRepository
				.findByAdApptId(request.getAppointmentId());
		if (!EmptyCheckUtility.isNullEmpty(appointmentDtlsEntity)) {
			appointmentDate = appointmentDtlsEntity.getAdApptDateFk();
			slotTime = appointmentDtlsEntity.getAdApptSlotFk();
			logger.info("Appointment date is:" + appointmentDate + "and slot is:" + slotTime);
		}
		String appointmentDateInString = DateTimeUtil.formatDateToStringForVideoConf(appointmentDate);
		logger.info("Appointment date in String is:" + appointmentDateInString);

		String[] startAndEndTime = appointmentDateInString.split(AuthConstant.DASH);
		String starttime = startAndEndTime[0];
		String endtime = startAndEndTime[1];

		String hashOfRoomName = getHashOfRoom(appointmentDateInString, starttime, endtime, request.getAppointmentId());

		// String originalString = "Start time:" + appointmentDateInString + starttime +
		// "," + "End time:"
		// + appointmentDateInString + endtime + "," + "User Type:" +
		// request.getUserType() + "," + "Source:"
		// + request.getSource() + "," + "room_name:" + hashOfRoomName;

		// String EncryptedString = EncryptionUtil.getEncryptedString(originalString,
		// secretKey);

		String EncryptedString = getEncryptedString(appointmentDateInString, starttime, endtime, hashOfRoomName,
				request.getUserType(), request.getSource());

		String url = videoConferenceUrl + request.getAppointmentId();
		videoConfResponse.setEncryptedString(EncryptedString);
		videoConfResponse.setUrl(url);
		return videoConfResponse;
	}

	private String getHashOfRoom(String appointmentDateInString, String starttime, String endtime,
			String appointmentId) {
		return MD5HashUtil.hashFunction(getDigestedString(appointmentDateInString, starttime, endtime, appointmentId));
	}

	private String getEncryptedString(String appointmentDateInString, String starttime, String endtime,
			String hashOfRoomName, String userType, String source) {
		String originalString = "Start time:" + appointmentDateInString + starttime + "," + "End time:"
				+ appointmentDateInString + endtime + "," + "User Type:" + userType + "," + "Source:" + source + ","
				+ "room_name:" + hashOfRoomName;

		return EncryptionUtil.getEncryptedString(originalString, secretKey);
	}

	@Override
	public InvitationResponse invite(InvitationRequest invitationRequest) {

		String responseMsg = null;
		String content = null;
		LocalDate appointmentDate = null;
		String slotTime = null;
		String doctorName = null;

		logger.info("Request received for sending invitation to EXP doctor on " + invitationRequest.getInviteMode()
				+ " : " + invitationRequest.getInviteOn() + " for joining the tele-conference call of appointmentId : "
				+ invitationRequest.getAppointmentId());

		AppointmentDtlsEntity appointmentDtlsEntity = appointmentDtlsRepository
				.findByAdApptId(invitationRequest.getAppointmentId());

		if (!EmptyCheckUtility.isNullEmpty(appointmentDtlsEntity)) {
			logger.info("fetching Doctor Name, Appointment Date and Slot");
			
			appointmentDate = appointmentDtlsEntity.getAdApptDateFk();
			slotTime = appointmentDtlsEntity.getAdApptSlotFk();
			doctorName = appointmentDtlsEntity.getDocMstrDtlsEntity().getDmdDrName();
			
			logger.info(" Doctor Name : " + doctorName + " Appointment date : " + appointmentDate + " and slot :" + slotTime);
		}

		String appointmentDateInString = DateTimeUtil.formatDateToStringForVideoConf(appointmentDate);
		logger.info("Appointment date in String is:" + appointmentDateInString);

		String[] startAndEndTime = appointmentDateInString.split("-");
		String starttime = startAndEndTime[0];
		String endtime = startAndEndTime[1];

		String hashOfRoomName = getHashOfRoom(appointmentDateInString, starttime, endtime,
				invitationRequest.getAppointmentId());

		String EncryptedString = getEncryptedString(appointmentDateInString, starttime, endtime, hashOfRoomName,
				AuthConstant.EXCEPTION_TYPE, AuthConstant.SOURCE_BROWSER);

		// https:// cloudvoice.com/<appointment-id>?e=<encrypted string>
		String url = videoConferenceUrl + invitationRequest.getAppointmentId() + videoConferenceUrlEncodeString
				+ EncryptedString;

		if (invitationRequest.getInviteMode().equalsIgnoreCase(AuthConstant.MOBILE)) {
			
			logger.info("RestTemplate call to OTP Service to send invitation on MOBILE");
			logger.info("Sending invitation to Doctor");
			
			InviteServiceMainRequestDTO<InviteServiceRequest> mainRequestDTO = new InviteServiceMainRequestDTO<>();
			InviteServiceRequest inviteServiceRequest = new InviteServiceRequest();
			
			content = "Dr. " + doctorName + " has requested you to join the tele-conference for Appointment ID : " + invitationRequest.getAppointmentId() + ", Click the link to join now : " + url;
			
			inviteServiceRequest.setInviteMode("mobile");
			inviteServiceRequest.setInviteOn(invitationRequest.getInviteOn());
			inviteServiceRequest.setContent(content);
			
			mainRequestDTO.setId("tele-conference-invitation-link");
			mainRequestDTO.setVersion("v1");
			mainRequestDTO.setRequestTime(LocalDateTime.now());
			mainRequestDTO.setMethod("POST");
			mainRequestDTO.setRequest(inviteServiceRequest);
			
			HttpEntity<InviteServiceMainRequestDTO<InviteServiceRequest>> requestEntity = new HttpEntity<>(mainRequestDTO);
			ParameterizedTypeReference<InviteServiceMainResponseDTO<InviteServiceResponse>> parameterizedResponse = new ParameterizedTypeReference<InviteServiceMainResponseDTO<InviteServiceResponse>>() {
			};
			ResponseEntity<InviteServiceMainResponseDTO<InviteServiceResponse>> response = restTemplate.exchange(invitationServiceURL,
					HttpMethod.POST, requestEntity, parameterizedResponse);
			
			 responseMsg = response.getBody().getResponse().getStatus();
			
		} else if (invitationRequest.getInviteMode().equalsIgnoreCase(AuthConstant.EMAIL)) {
			
			logger.info("RestTemplate call to OTP Service to send invitation on EMAIL");
			logger.info("Sending invitation to Doctor");
			
			InviteServiceMainRequestDTO<InviteServiceRequest> mainRequestDTO = new InviteServiceMainRequestDTO<>();
			InviteServiceRequest inviteServiceRequest = new InviteServiceRequest();
			
			content = "Dr. " + doctorName + " has requested you to join the tele-conference for Appointment ID : " + invitationRequest.getAppointmentId() + ", Click the link to join now : " + url;
			
			inviteServiceRequest.setInviteMode("email");
			inviteServiceRequest.setInviteOn(invitationRequest.getInviteOn());
			inviteServiceRequest.setContent(content);
			
			mainRequestDTO.setId("tele-conference-invitation-link");
			mainRequestDTO.setVersion("v1");
			mainRequestDTO.setRequestTime(LocalDateTime.now());
			mainRequestDTO.setMethod("POST");
			mainRequestDTO.setRequest(inviteServiceRequest);
			
			HttpEntity<InviteServiceMainRequestDTO<InviteServiceRequest>> requestEntity = new HttpEntity<>(mainRequestDTO);
			ParameterizedTypeReference<InviteServiceMainResponseDTO<InviteServiceResponse>> parameterizedResponse = new ParameterizedTypeReference<InviteServiceMainResponseDTO<InviteServiceResponse>>() {
			};
			ResponseEntity<InviteServiceMainResponseDTO<InviteServiceResponse>> response = restTemplate.exchange(invitationServiceURL,
					HttpMethod.POST, requestEntity, parameterizedResponse);
			
			 responseMsg = response.getBody().getResponse().getStatus();
			
		}

		InvitationResponse invitationResponse = new InvitationResponse();		
		if(responseMsg.equalsIgnoreCase("SUCCESS")) {
			invitationResponse.setMessage("Invitation has been sent successfully");
		} else if(responseMsg.equalsIgnoreCase("FAILURE")) {
			invitationResponse.setMessage("Invitation has not been sent");
		}

		return invitationResponse;
	}

	private String getDigestedString(String appointmentDateInString, String starttime, String endtime,
			String appointmentId) {
		String digestedString = appointmentDateInString + starttime + appointmentId + appointmentDateInString + endtime
				+ fixedTxt;
		logger.info("digested String is:" + digestedString);
		return digestedString;
	}
}
