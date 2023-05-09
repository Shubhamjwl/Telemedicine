package com.nsdl.payment.service;

import com.nsdl.payment.entity.AppointmentDtlsEntity;

public interface AuditService {
	
	public boolean auditAppointmentData(AppointmentDtlsEntity appointmentDtlsEntity);

}
