package com.nsdl.telemedicine.videoConference.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class CreateMeetingRequest {

	private String meetingId;
	private String name;
	private String moderatorPassword;
	private String attendeePassword;
	private boolean redirect;
	private boolean record;
	private String duration;
	private String moderatorId;

	private Map<String, String> meta = new HashMap<String, String>();

	public void addMeta(String key, String value) {
		meta.put(key, value);
	}

	public void removeMeta(String key) {
		if (meta.containsKey(key))
			meta.remove(key);
	}

}
