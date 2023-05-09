package com.nsdl.notification.service.impl;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.WebpushConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nsdl.notification.constant.NotificationParameter;
import com.nsdl.notification.dto.PushNotificationRequest;

@Service
public class FCMServiceImpl {

	private Logger logger = LoggerFactory.getLogger(FCMServiceImpl.class);

	public void sendMessageToToken(Map<String, String> data, PushNotificationRequest request)
			throws InterruptedException, ExecutionException {
		Message message = getPreconfiguredMessageToToken(data, request);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(message);
		String response = sendAndGetResponse(message);
		logger.info(
				"Sent message to token. Device token: " + request.getToken() + ", " + response + " msg " + jsonOutput);
	}

	private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
		return FirebaseMessaging.getInstance().sendAsync(message).get();
	}

	private Message getPreconfiguredMessageToToken(Map<String, String> data, PushNotificationRequest request) {
		return getPreconfiguredMessageBuilder(request).putAllData(data).setToken(request.getToken()).build();
	}

	private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
		AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
		ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
		WebpushConfig webPushConfig = getWebpushConfig(request.getTopic());

		return Message.builder().setApnsConfig(apnsConfig).setAndroidConfig(androidConfig)
				.setWebpushConfig(webPushConfig).setNotification(
						Notification.builder().setBody(request.getMessage()).setTitle(request.getTitle()).build());
	}

	private AndroidConfig getAndroidConfig(String topic) {
		return AndroidConfig.builder().setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
				.setPriority(AndroidConfig.Priority.HIGH)
				.setNotification(AndroidNotification.builder().setSound(NotificationParameter.SOUND.getValue())
						.setColor(NotificationParameter.COLOR.getValue()).setTag(topic).build())
				.build();
	}

	private ApnsConfig getApnsConfig(String topic) {
		return ApnsConfig.builder().setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
	}

	private WebpushConfig getWebpushConfig(String topic) {
		return WebpushConfig.builder().build();
	}
}
