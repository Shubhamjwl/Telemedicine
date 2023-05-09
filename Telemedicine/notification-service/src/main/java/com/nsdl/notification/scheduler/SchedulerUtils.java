package com.nsdl.notification.scheduler;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.nsdl.notification.dto.PushNotificationRequest;
import com.nsdl.notification.entity.AppointmentDtlsEntity;
import com.nsdl.notification.entity.NotificationEntity;
import com.nsdl.notification.entity.NotificationTemplatesEntity;
import com.nsdl.notification.entity.NotificationUserTokenEntity;
import com.nsdl.notification.entity.UserEntity;
import com.nsdl.notification.repository.AppointmentDtlsRepository;
import com.nsdl.notification.repository.NotificationRepo;
import com.nsdl.notification.repository.NotificationTemplatesRepository;
import com.nsdl.notification.repository.NotificationUserTokenRepo;
import com.nsdl.notification.repository.UserRepositiory;
import com.nsdl.notification.service.NotificationService;

@Configuration
@EnableScheduling
public class SchedulerUtils {

	private static final Logger logger = LoggerFactory.getLogger(SchedulerUtils.class);

	@Autowired
	private UserRepositiory userRepositiory;

	@Autowired
	private AppointmentDtlsRepository appointmentRepo;

	@Autowired
	private NotificationUserTokenRepo notificationUserTokenRepo;

	@Autowired
	private NotificationTemplatesRepository notificationTemplatesRepository;

	@Autowired
	private NotificationRepo notificationRepo;

	@Autowired
	private NotificationService notificationService;
	
	static final long ONE_MINUTE_IN_MILLIS = 60000;// millisecs

	@Scheduled(cron = "${pwd.expiry.10days.cron.expression}")
	public void findExpiredUsersBy10Days() throws ParseException {

		logger.info("scheduler for 10 days pwd expiry running at {}", LocalDateTime.now());

		List<UserEntity> users = userRepositiory.findExpiredUsersBy10Days();

		if (users == null || users.isEmpty()) {
			logger.info("users not found whose pwd will expire in 10 days.");
			return;
		}

		NotificationTemplatesEntity templateInfo = notificationTemplatesRepository
				.findByTemplateName("PasswordExpiry10Days");
		if (templateInfo == null) {
			logger.info("template not found.");
			return;
		}

		for (UserEntity user : users) {

			NotificationEntity notification = new NotificationEntity();
			notification.setNdNotification(templateInfo.getNtMessage());
			notification.setNdNotificationType(templateInfo.getNtType());
			notification.setNdCreatedOn(LocalDateTime.now());
			notification.setNdUnreadFlag(true);
			notification.setNdUserId(user.getUserId());
			notification.setNdUserType(user.getUserType());
			NotificationEntity entity = notificationRepo.save(notification);

			NotificationUserTokenEntity tokenEntity = notificationUserTokenRepo
					.findByUserIdAndUserType(user.getUserId(), user.getUserType());

			if (tokenEntity == null) {
				continue;
			}

			PushNotificationRequest request = new PushNotificationRequest();
			request.setMessage(notification.getNdNotification());
			request.setTitle(notification.getNdNotificationType());
			request.setToken(tokenEntity.getNutUserToken());

			Map<String, String> data = new HashMap<>();
			data.put("notificationId", Integer.toString(entity.getNdId()));
			data.put("notificationType", notification.getNdNotificationType());
			data.put("notificationColor", templateInfo.getNtColor());
			data.put("notification", notification.getNdNotification());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			data.put("createdTmstp", notification.getNdCreatedOn().format(formatter));
			data.put("unReadFlag", String.valueOf(notification.getNdUnreadFlag()));

			notificationService.sendPushNotification(data, request);

		}
		logger.info("scheduler for 10 days pwd expiry stopped at {}", LocalDateTime.now());
	}

	@Scheduled(cron = "${pwd.expiry.1day.cron.expression}")
	public void findExpiredUsersBy1Day() throws ParseException {

		logger.info("scheduler for 1 day pwd expiry running at {}", LocalDateTime.now());

		List<UserEntity> users = userRepositiory.findExpiredUsersBy1Day();

		if (users == null || users.isEmpty()) {
			logger.info("users not found whose pwd will expire in 1 day.");
			return;
		}

		NotificationTemplatesEntity templateInfo = notificationTemplatesRepository
				.findByTemplateName("PasswordExpiry1Day");
		if (templateInfo == null) {
			logger.info("template not found.");
			return;
		}

		for (UserEntity user : users) {

			NotificationEntity notification = new NotificationEntity();
			notification.setNdNotification(templateInfo.getNtMessage());
			notification.setNdNotificationType(templateInfo.getNtType());
			notification.setNdCreatedOn(LocalDateTime.now());
			notification.setNdUnreadFlag(true);
			notification.setNdUserId(user.getUserId());
			notification.setNdUserType(user.getUserType());
			NotificationEntity entity = notificationRepo.save(notification);

			NotificationUserTokenEntity tokenEntity = notificationUserTokenRepo
					.findByUserIdAndUserType(user.getUserId(), user.getUserType());

			if (tokenEntity == null) {
				continue;
			}

			PushNotificationRequest request = new PushNotificationRequest();
			request.setMessage(notification.getNdNotification());
			request.setTitle(notification.getNdNotificationType());
			request.setToken(tokenEntity.getNutUserToken());

			Map<String, String> data = new HashMap<>();
			data.put("notificationId", Integer.toString(entity.getNdId()));
			data.put("notificationType", notification.getNdNotificationType());
			data.put("notificationColor", templateInfo.getNtColor());
			data.put("notification", notification.getNdNotification());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			data.put("createdTmstp", notification.getNdCreatedOn().format(formatter));
			data.put("unReadFlag", String.valueOf(notification.getNdUnreadFlag()));

			notificationService.sendPushNotification(data, request);

		}
		logger.info("scheduler for 1 day pwd expiry stopped at {}", LocalDateTime.now());
	}

	@Scheduled(cron = "${patient.appointment.cron.expression}")
	public void sendPatientNotificationForAppointments() throws ParseException {

		logger.info("scheduler for patient appointments running at {}", LocalDateTime.now());

		// find date by adding 30 minutes to current time
		Calendar date = Calendar.getInstance();
		long t = date.getTimeInMillis();
		Date afterAddingThirtyMins = new Date(t + (30 * ONE_MINUTE_IN_MILLIS));

		List<AppointmentDtlsEntity> appointments = appointmentRepo
				.findAppointmentsForCurrentTimePlus30Mins(afterAddingThirtyMins);

		if (appointments == null || appointments.isEmpty()) {
			logger.info("appointments not available with 30 mins left.");
			return;
		}

		NotificationTemplatesEntity templateInfo = notificationTemplatesRepository
				.findByTemplateName("PatientAppointment");
		if (templateInfo == null) {
			logger.info("template not found.");
			return;
		}

		for (AppointmentDtlsEntity appointment : appointments) {

			String template = templateInfo.getNtMessage().replace("<hh:mm>",
					LocalTime.parse(appointment.getAdApptSlotFk().substring(0, 5), DateTimeFormatter.ofPattern("HH:mm"))
							.format(DateTimeFormatter.ofPattern("hh:mm a")))
					.replace("<Doctor Name>", appointment.getDocMstrDtlsEntity().getDmdDrName());

			NotificationEntity notification = new NotificationEntity();
			notification.setNdNotification(template);
			notification.setNdNotificationType(templateInfo.getNtType());
			notification.setNdCreatedOn(LocalDateTime.now());
			notification.setNdUnreadFlag(true);
			notification.setNdUserId(appointment.getPatientRegDtlsEntity().getPrdUserId());
			notification.setNdUserType("PATIENT");
			NotificationEntity entity = notificationRepo.save(notification);

			NotificationUserTokenEntity tokenEntity = notificationUserTokenRepo
					.findByUserIdAndUserType(appointment.getPatientRegDtlsEntity().getPrdUserId(), "PATIENT");

			if (tokenEntity == null) {
				continue;
			}

			PushNotificationRequest request = new PushNotificationRequest();
			request.setMessage(notification.getNdNotification());
			request.setTitle(notification.getNdNotificationType());
			request.setToken(tokenEntity.getNutUserToken());

			Map<String, String> data = new HashMap<>();
			data.put("notificationId", Integer.toString(entity.getNdId()));
			data.put("notificationType", notification.getNdNotificationType());
			data.put("notificationColor", templateInfo.getNtColor());
			data.put("notification", notification.getNdNotification());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			data.put("createdTmstp", notification.getNdCreatedOn().format(formatter));
			data.put("unReadFlag", String.valueOf(notification.getNdUnreadFlag()));

			notificationService.sendPushNotification(data, request);

		}
		logger.info("scheduler for patient appointments stopped at {}", LocalDateTime.now());
	}
	
	@Scheduled(cron = "${doctor.appointment.cron.expression}")
	public void sendDoctorNotificationForAppointments() throws ParseException {

		logger.info("scheduler for doctor appointments running at {}", LocalDateTime.now());

		// find date by deducting 5 minutes to current time
		Calendar date = Calendar.getInstance();
		long t = date.getTimeInMillis();
		Date afterDeductingFiveMins = new Date(t - (5 * ONE_MINUTE_IN_MILLIS));

		List<AppointmentDtlsEntity> appointments = appointmentRepo
				.findAppointmentsForCurrentTimeMinus5Mins(afterDeductingFiveMins);

		if (appointments == null || appointments.isEmpty()) {
			logger.info("appointments not available for current time plus 5 mins.");
			return;
		}

		NotificationTemplatesEntity templateInfo = notificationTemplatesRepository
				.findByTemplateName("DoctorAppointment");
		if (templateInfo == null) {
			logger.info("template not found.");
			return;
		}

		for (AppointmentDtlsEntity appointment : appointments) {

			if (appointment.getAdApptStartTime() != null) {
				continue;
			}

			String template = templateInfo.getNtMessage().replace("<hh:mm>",
					LocalTime.parse(appointment.getAdApptSlotFk().substring(0, 5), DateTimeFormatter.ofPattern("HH:mm"))
							.format(DateTimeFormatter.ofPattern("hh:mm a")))
					.replace("<Patient Name>", appointment.getPatientRegDtlsEntity().getPrdPtName());

			NotificationEntity notification = new NotificationEntity();
			notification.setNdNotification(template);
			notification.setNdNotificationType(templateInfo.getNtType());
			notification.setNdCreatedOn(LocalDateTime.now());
			notification.setNdUnreadFlag(true);
			notification.setNdUserId(appointment.getDocMstrDtlsEntity().getDmdUserId());
			notification.setNdUserType("DOCTOR");
			NotificationEntity entity = notificationRepo.save(notification);

			NotificationUserTokenEntity tokenEntity = notificationUserTokenRepo
					.findByUserIdAndUserType(appointment.getDocMstrDtlsEntity().getDmdUserId(), "DOCTOR");

			if (tokenEntity == null) {
				continue;
			}

			PushNotificationRequest request = new PushNotificationRequest();
			request.setMessage(notification.getNdNotification());
			request.setTitle(notification.getNdNotificationType());
			request.setToken(tokenEntity.getNutUserToken());

			Map<String, String> data = new HashMap<>();
			data.put("notificationId", Integer.toString(entity.getNdId()));
			data.put("notificationType", notification.getNdNotificationType());
			data.put("notificationColor", templateInfo.getNtColor());
			data.put("notification", notification.getNdNotification());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			data.put("createdTmstp", notification.getNdCreatedOn().format(formatter));
			data.put("unReadFlag", String.valueOf(notification.getNdUnreadFlag()));

			notificationService.sendPushNotification(data, request);

		}
		logger.info("scheduler for doctor appointments stopped at {}", LocalDateTime.now());
	}

}
