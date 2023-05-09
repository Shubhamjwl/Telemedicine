package com.nsdl.telemedicine.videoConference.dto;

import lombok.Data;

@Data
public class CreateMeetingResponse {
    public String meetingId;
    public String attendeePW;
    public String moderatorPW;
}
