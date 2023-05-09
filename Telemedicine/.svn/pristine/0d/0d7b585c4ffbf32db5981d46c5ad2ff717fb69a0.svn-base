package com.nsdl.notification.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nsdl.notification.constant.NotificationConstant;
import com.nsdl.notification.dto.NotificationDTO;
import com.nsdl.notification.dto.NotificationFileResponse;
import com.nsdl.notification.dto.NotificationResponse;
import com.nsdl.notification.dto.PushNotificationRequest;
import com.nsdl.notification.dto.SendNotificationRequest;
import com.nsdl.notification.dto.UserDTO;
import com.nsdl.notification.entity.NotificationEntity;
import com.nsdl.notification.entity.NotificationTemplatesEntity;
import com.nsdl.notification.entity.NotificationUserTokenEntity;
import com.nsdl.notification.entity.PatientRegDtlsEntity;
import com.nsdl.notification.entity.WalletEntity;
import com.nsdl.notification.exception.NotificationErrorConstant;
import com.nsdl.notification.exception.NotificationException;
import com.nsdl.notification.exception.ServiceError;
import com.nsdl.notification.logger.LoggingClientInfo;
import com.nsdl.notification.repository.NotificationRepo;
import com.nsdl.notification.repository.NotificationTemplatesRepository;
import com.nsdl.notification.repository.NotificationUserTokenRepo;
import com.nsdl.notification.repository.PatientPersonalDetailsRepository;
import com.nsdl.notification.repository.WalletRepo;
import com.nsdl.notification.service.NotificationService;

@Service
@LoggingClientInfo
public class NotificationServiceImpl implements NotificationService {

	private Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	private NotificationRepo notificationRepo;

	@Autowired
	private NotificationUserTokenRepo notificationUserTokenRepo;
	
	@Autowired
	private NotificationTemplatesRepository notificationTemplatesRepository;
	
	@Autowired
	private PatientPersonalDetailsRepository patientRepo;
	
	@Autowired
	private WalletRepo walletRepo;

	@Autowired
	private FCMServiceImpl fcmServiceImpl;

	@Autowired
	private UserDTO userDto;
	
	@Value("${notification.templates.path}")
	private String notificationTemplatesPath;
	
	@Value("${notification.attachment.path}")
	private String notificationAttachmentPath;
	
	Map<String, String> templatesMap = new HashMap<>();

	@PostConstruct
	private void init() {
		List<NotificationTemplatesEntity> templates = notificationTemplatesRepository.findAll();

		templates.forEach(template -> {
			templatesMap.put(template.getNtType(), template.getNtColor());
		});
	}

	@Override
	public NotificationResponse getAllNotifications(boolean updateLastSyncFlag) {

		NotificationResponse response = new NotificationResponse();
		List<NotificationDTO> notificationList = new ArrayList<NotificationDTO>();

		// Pageable page = PageRequest.of(pageNo, 5);
		List<NotificationEntity> notificationEntities = notificationRepo
				.findNotificationsByUserIdAndUserType(userDto.getUserName(), userDto.getRole());

		if (notificationEntities != null && !notificationEntities.isEmpty()) {
			for (NotificationEntity entity : notificationEntities) {
				NotificationDTO notification = NotificationDTO.builder().notificationId(entity.getNdId())
						.notificationType(entity.getNdNotificationType()).notification(entity.getNdNotification())
						.notificationColor(templatesMap.get(entity.getNdNotificationType()))
						.createdTmstp(entity.getNdCreatedOn()).unReadFlag(entity.getNdUnreadFlag()).build();
				notificationList.add(notification);
			}
		} else {
			throw new NotificationException(
					new ServiceError(NotificationErrorConstant.NOTIFICATION_EMPTY.getErrorCode(),
							NotificationErrorConstant.NOTIFICATION_EMPTY.getErrorMessage()));
		}

		NotificationUserTokenEntity userEntity = notificationUserTokenRepo
				.findByUserIdAndUserType(userDto.getUserName(), userDto.getRole());

		if (updateLastSyncFlag) {
			if (userEntity != null) {
				userEntity.setNutLastSync(LocalDateTime.now());
				notificationUserTokenRepo.save(userEntity);
			}
		}
		
		long unReadCount = 0L;
		if (userEntity != null && userEntity.getNutLastSync() != null) {
			unReadCount = notificationList.stream()
					.filter(n -> n.getCreatedTmstp().isAfter(userEntity.getNutLastSync()) && n.isUnReadFlag()).count();
		} else {
			unReadCount = notificationList.stream().filter(n -> n.isUnReadFlag()).count();
		}

		response.setCount(unReadCount);
		response.setNotificationList(notificationList);
		return response;
	}

	@Override
	public String updateNotificationReadFlag(int notificationId) {

		int count = notificationRepo.updateNotificationFlag(false, notificationId, userDto.getUserName());

		if (count == 1) {
			return NotificationConstant.NOTIFICATION_READ_FLAG_UPDATED;
		} else {
			throw new NotificationException(
					new ServiceError(NotificationErrorConstant.NOTIFICATION_ID_NOT_FOUND.getErrorCode(),
							NotificationErrorConstant.NOTIFICATION_ID_NOT_FOUND.getErrorMessage()));
		}
	}

	@Override
	public String updateRegistrationTokenForUser(String token) {

		if (token == null || token.isEmpty()) {
			throw new NotificationException(
					new ServiceError(NotificationErrorConstant.TOKEN_SHOULD_NOT_BE_EMPTY_OR_NULL.getErrorCode(),
							NotificationErrorConstant.TOKEN_SHOULD_NOT_BE_EMPTY_OR_NULL.getErrorMessage()));
		}

		NotificationUserTokenEntity entity = notificationUserTokenRepo.findByUserIdAndUserType(userDto.getUserName(),
				userDto.getRole());

		if (entity == null) {
			NotificationUserTokenEntity userEntity = new NotificationUserTokenEntity();
			userEntity.setNutUserId(userDto.getUserName());
			userEntity.setNutUserType(userDto.getRole());
			userEntity.setNutUserToken(token);
			notificationUserTokenRepo.save(userEntity);
		} else {
			entity.setNutUserToken(token);
			notificationUserTokenRepo.save(entity);
		}
		return NotificationConstant.TOKEN_UPDATED_FOR_USER;
	}

	@Override
	public void sendPushNotification(Map<String, String> data, PushNotificationRequest request) {
		try {
			fcmServiceImpl.sendMessageToToken(data, request);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	@Override
	@Transactional
	public String sendNotification(SendNotificationRequest request) {

		if (!userDto.getRole().equalsIgnoreCase(NotificationConstant.ADMIN)) {
			throw new NotificationException(
					new ServiceError(NotificationErrorConstant.OPERATION_NOT_ALLOWED.getErrorCode(),
							NotificationErrorConstant.OPERATION_NOT_ALLOWED.getErrorMessage()));
		}

		String fileName = String.valueOf(Instant.now().getEpochSecond());

		NotificationTemplatesEntity templateInfo = notificationTemplatesRepository
				.findByTemplateName(request.getNotificationType());
		if (templateInfo == null) {
			logger.info("template not found.");
			throw new NotificationException(
					new ServiceError(NotificationErrorConstant.TEMPLATE_NOT_FOUND.getErrorCode(),
							NotificationErrorConstant.TEMPLATE_NOT_FOUND.getErrorMessage()));
		}
		
		if (request.getNotificationType().equalsIgnoreCase(NotificationConstant.WALLET)) {
			if (request.getAmount() <= 0) {
				throw new NotificationException(new ServiceError(
						NotificationErrorConstant.AMOUNT_SHOULD_NOT_BE_LESS_THAN_OR_EQUAL_TO_ZERO.getErrorCode(),
						NotificationErrorConstant.AMOUNT_SHOULD_NOT_BE_LESS_THAN_OR_EQUAL_TO_ZERO.getErrorMessage()));
			}

			if (request.getUserType().equalsIgnoreCase(NotificationConstant.DOCTOR)) {
				throw new NotificationException(new ServiceError(
						NotificationErrorConstant.DOCTOR_NOT_ALLOWED_FOR_WALLET_NOTIFICATION.getErrorCode(),
						NotificationErrorConstant.DOCTOR_NOT_ALLOWED_FOR_WALLET_NOTIFICATION.getErrorMessage()));
			}
		}
	
		uploadNotificationTemplateFile(fileName, request.getTemplate(), notificationTemplatesPath);

		if (request.getAttachmentFileName() != null) {
			uploadAttachmentFile(request.getAttachmentFileName(), request.getAttachmentFile(),
					notificationAttachmentPath);
		}
		
		List<String> requestedUsers = new ArrayList<String>();
		if (request.getUserType().equalsIgnoreCase(NotificationConstant.PATIENT)) {
			requestedUsers = patientRepo.findAllByPtUserID(request.getUserIds());
		} else {
			requestedUsers = request.getUserIds();
		}

		for (String userId : requestedUsers) {
			
			String template = null;
			if (request.getNotificationType().equalsIgnoreCase(NotificationConstant.WALLET)) {
				template = addAmountToUserWalletAndReturnTemplate(userId, request.getAmount(), templateInfo);
			} else {
				template = templateInfo.getNtMessage();
			}

			NotificationEntity notification = new NotificationEntity();
			notification.setNdNotification(template);
			notification.setNdNotificationType(templateInfo.getNtType());
			notification.setNdTemplatePath(notificationTemplatesPath + fileName);
			if (request.getAttachmentFileName() != null) {
				notification.setNdAttachmentPath(notificationAttachmentPath + request.getAttachmentFileName());
			}
			notification.setNdCreatedOn(LocalDateTime.now());
			notification.setNdUnreadFlag(true);
			notification.setNdUserId(userId);
			notification.setNdUserType(request.getUserType());
			NotificationEntity entity = notificationRepo.save(notification);

			NotificationUserTokenEntity tokenEntity = notificationUserTokenRepo.findByUserIdAndUserType(userId,
					request.getUserType());

			if (tokenEntity == null) {
				continue;
			}

			PushNotificationRequest notificationRequest = new PushNotificationRequest();
			notificationRequest.setMessage(notification.getNdNotification());
			notificationRequest.setTitle(notification.getNdNotificationType());
			notificationRequest.setToken(tokenEntity.getNutUserToken());

			Map<String, String> notificationData = new HashMap<>();
			notificationData.put("notificationId", Integer.toString(entity.getNdId()));
			notificationData.put("notificationType", notification.getNdNotificationType());
			notificationData.put("notificationColor", templateInfo.getNtColor());
			notificationData.put("notification", notification.getNdNotification());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			notificationData.put("createdTmstp", notification.getNdCreatedOn().format(formatter));
			notificationData.put("unReadFlag", String.valueOf(notification.getNdUnreadFlag()));

			sendPushNotification(notificationData, notificationRequest);
		}
		return NotificationConstant.NOTIFICATION_SENT;
	}

	private String addAmountToUserWalletAndReturnTemplate(String userId, int amount,
			NotificationTemplatesEntity templateInfo) {

		PatientRegDtlsEntity entity = patientRepo.findByPtUserID(userId);

		if (entity.getPrdWalletAmount() == null) {
			entity.setPrdWalletAmount(amount);
		} else {
			entity.setPrdWalletAmount(entity.getPrdWalletAmount() + amount);
		}

		// added amount to existing wallet amount
		patientRepo.save(entity);

		// wallet audit
		WalletEntity walletEntity = new WalletEntity();
		walletEntity.setWalPatUserId(userId);
		walletEntity.setWalTranstAmount(amount);
		walletEntity.setWalTranstBy(userDto.getUserName());
		walletEntity.setWalTranstTmpstmp(LocalDateTime.now());
		walletEntity.setWalTranstType(NotificationConstant.CREDIT);
		walletRepo.save(walletEntity);

		return templateInfo.getNtMessage().replace("<amount>", Integer.toString(amount)).replace("<availableAmount>",
				Integer.toString(entity.getPrdWalletAmount()));
	}

	private void uploadNotificationTemplateFile(String fileName, String template, String path) {
		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		File file = new File(path + fileName);
		try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
			logger.info("write data to file");
			outputStream.write(template.getBytes(Charset.forName("UTF-8")));
		} catch (IOException e) {
			logger.error("Exception while uploading notification template file.");
			e.printStackTrace();
			throw new NotificationException(
					new ServiceError(NotificationErrorConstant.NOTIFICATION_TEMPLATE_UPLOAD_FAILED.getErrorCode(),
							NotificationErrorConstant.NOTIFICATION_TEMPLATE_UPLOAD_FAILED.getErrorMessage()));
		}
	}

	private void uploadAttachmentFile(String fileName, String templateData, String path) {

		byte[] data = null;
		String templateDataInfo[] = templateData.split(",");
		try {
			data = DatatypeConverter.parseBase64Binary(templateDataInfo[1].trim());
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotificationException(new ServiceError(NotificationErrorConstant.FILE_INVALID.getErrorCode(),
					NotificationErrorConstant.FILE_INVALID.getErrorMessage()));
		}

		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		File file = new File(path + fileName);
		try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
			logger.info("write data to file");
			outputStream.write(data);
		} catch (IOException e) {
			logger.error("Exception while uploading notification file.");
			e.printStackTrace();
			throw new NotificationException(
					new ServiceError(NotificationErrorConstant.NOTIFICATION_FILE_UPLOAD_FAILED.getErrorCode(),
							NotificationErrorConstant.NOTIFICATION_FILE_UPLOAD_FAILED.getErrorMessage()));
		}
	}

	@Override
	public NotificationFileResponse getTemplateByNotificationId(int notificationId) {

		NotificationFileResponse response = new NotificationFileResponse();

		NotificationEntity notification = notificationRepo.findNotificationsById(notificationId);
		if (notification == null || notification.getNdTemplatePath() == null) {
			throw new NotificationException(
					new ServiceError(NotificationErrorConstant.TEMPLATE_NOT_FOUND.getErrorCode(),
							NotificationErrorConstant.TEMPLATE_NOT_FOUND.getErrorMessage()));
		}

		try {
			response.setTemplate(new String(Files.readAllBytes(Paths.get(notification.getNdTemplatePath()))));
			response.setAttachmentPath(notification.getNdAttachmentPath());
			return response;
		} catch (Throwable e) {
			e.printStackTrace();
			throw new NotificationException(
					new ServiceError(NotificationErrorConstant.FILE_DOWNLOAD_FAILED.getErrorCode(),
							NotificationErrorConstant.FILE_DOWNLOAD_FAILED.getErrorMessage()));
		}
	}

}
