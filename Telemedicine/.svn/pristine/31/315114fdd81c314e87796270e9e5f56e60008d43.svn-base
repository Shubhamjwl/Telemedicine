package com.nsdl.telemedicine.videoConference.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "bbb_vc_dtls", schema = "usrmgmt")
@Data
public class BBBMeetingEntity {

	@Id
	@Column(name = "bbb_id_pk")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bbbIdPk;

	@Column(name = "meeting_id")
	private String meetingId;

	@Column(name = "meeting_name")
	private String meetingName;

	@Column(name = "moderator_password")
	private String moderatorPassword;

	@Column(name = "attendee_password")
	private String attendeePassword;

	@Column(name = "redirect")
	private boolean redirect;

	@Column(name = "record")
	private boolean record;

	@Column(name = "voiceBridge")
	private String voiceBridge;

	@Column(name = "internal_meeting_id")
	public String internalMeetingID;

	@Column(name = "dial_number")
	public String dialNumber;

	@Column(name = "duration_minutes")
	public String duration;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_tmstmp")
	private LocalDateTime createdTime;

}
