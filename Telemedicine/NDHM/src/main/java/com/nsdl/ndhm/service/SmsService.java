package com.nsdl.ndhm.service;

import com.nsdl.ndhm.dto.StatusDTO;

public interface SmsService {

    StatusDTO sendGupShupSms(String msg, String mobile);
}
