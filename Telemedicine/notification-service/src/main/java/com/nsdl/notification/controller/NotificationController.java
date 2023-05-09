package com.nsdl.notification.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.notification.dto.MainRequestDTO;
import com.nsdl.notification.dto.MainResponseDTO;
import com.nsdl.notification.dto.NotificationFileResponse;
import com.nsdl.notification.dto.NotificationResponse;
import com.nsdl.notification.dto.PushNotificationRequest;
import com.nsdl.notification.dto.SendNotificationRequest;
import com.nsdl.notification.logger.LoggingClientInfo;
import com.nsdl.notification.service.NotificationService;

@RestController
@RequestMapping(value = "/alert")
@LoggingClientInfo
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@GetMapping(path = "/getNotifications", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<NotificationResponse>> getAllNotifications(
			@RequestParam(required = true) boolean updateLastSyncFlag) {
		MainResponseDTO<NotificationResponse> response = new MainResponseDTO<NotificationResponse>();
		NotificationResponse notifications = notificationService.getAllNotifications(updateLastSyncFlag);
		response.setResponse(notifications);
		response.setResponsetime(LocalDateTime.now());
		response.setStatus(true);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping(path = "/updateNotificationReadFlag", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<String>> updateNotificationReadFlag(
			@RequestParam(required = true) int notificationId) {
		MainResponseDTO<String> response = new MainResponseDTO<String>();
		String status = notificationService.updateNotificationReadFlag(notificationId);
		response.setResponse(status);
		response.setResponsetime(LocalDateTime.now());
		response.setStatus(true);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping(path = "/updateRegistrationTokenForUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<String>> updateRegistrationTokenForUser(
			@RequestParam(required = true) String token) {
		MainResponseDTO<String> response = new MainResponseDTO<String>();
		String status = notificationService.updateRegistrationTokenForUser(token);
		response.setResponse(status);
		response.setResponsetime(LocalDateTime.now());
		response.setStatus(true);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping(path = "/sendNotification", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<String>> sendNotification(
			@RequestBody @Valid MainRequestDTO<SendNotificationRequest> request) {
		MainResponseDTO<String> response = new MainResponseDTO<String>();
		String status = notificationService.sendNotification(request.getRequest());
		response.setResponse(status);
		response.setResponsetime(LocalDateTime.now());
		response.setStatus(true);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping(path = "/getTemplateByNotificationId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<NotificationFileResponse>> getTemplateByNotificationId(
			@RequestParam(required = true) int notificationId) {
		MainResponseDTO<NotificationFileResponse> response = new MainResponseDTO<NotificationFileResponse>();
		NotificationFileResponse notifications = notificationService.getTemplateByNotificationId(notificationId);
		response.setResponse(notifications);
		response.setResponsetime(LocalDateTime.now());
		response.setStatus(true);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("/notification/token")
	public ResponseEntity<String> sendTokenNotification(@RequestBody PushNotificationRequest request) {
		notificationService.sendPushNotification(getSamplePayloadData(), request);
		return ResponseEntity.status(HttpStatus.OK).body("Notification has been sent.");
	}

	private Map<String, String> getSamplePayloadData() {
		Map<String, String> pushData = new HashMap<>();
		pushData.put("messageId", "msgid");
		pushData.put("text", "txt");
		pushData.put("user", "pankaj singh");
		return pushData;
	}

}
