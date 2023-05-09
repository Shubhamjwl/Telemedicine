package com.nsdl.ndhm.transfer.dto;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class NotifyStatusNotificationDTO {
	private String sessionStatus;
	private String hipId;
	private ArrayList<NotifyStatusResponsDTO> statusResponses;
}
