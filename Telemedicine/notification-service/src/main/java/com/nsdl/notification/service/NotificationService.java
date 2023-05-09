package com.nsdl.notification.service;

import java.util.Map;

import com.nsdl.notification.dto.NotificationFileResponse;
import com.nsdl.notification.dto.NotificationResponse;
import com.nsdl.notification.dto.PushNotificationRequest;
import com.nsdl.notification.dto.SendNotificationRequest;

public interface NotificationService {

	public NotificationResponse getAllNotifications(boolean updateLastSyncFlag);

	public String updateNotificationReadFlag(int notificationId);

	public String updateRegistrationTokenForUser(String token);

	public void sendPushNotification(Map<String, String> data, PushNotificationRequest request);

	public String sendNotification(SendNotificationRequest request);

	public NotificationFileResponse getTemplateByNotificationId(int notificationId);

}
