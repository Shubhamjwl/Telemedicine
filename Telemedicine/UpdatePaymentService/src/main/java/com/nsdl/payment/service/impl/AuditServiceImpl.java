package com.nsdl.payment.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nsdl.payment.entity.AppointmentAuditEntity;
import com.nsdl.payment.entity.AppointmentDtlsEntity;
import com.nsdl.payment.repository.AppointmentDtlsAuditRepository;
import com.nsdl.payment.service.AuditService;

@Service
public class AuditServiceImpl implements AuditService {

	@Autowired
	private AppointmentDtlsAuditRepository auditRepository;

	

	@Override
	public boolean auditAppointmentData(AppointmentDtlsEntity appointmentDtlsEntity) {
		// TODO Auto-generated method stub
		AppointmentAuditEntity appointmentDtlsAuditEntity = new AppointmentAuditEntity();
		appointmentDtlsAuditEntity.setAdIdPk(appointmentDtlsEntity.getAdIdPk());
		appointmentDtlsAuditEntity.setAdApptId(appointmentDtlsEntity.getAdApptId());
		appointmentDtlsAuditEntity.setAdDrUserIdFk(appointmentDtlsEntity.getDocMstrDtlsEntity().getDmdUserId());
		appointmentDtlsAuditEntity.setAdPtUserIdFk(appointmentDtlsEntity.getPatientRegDtlsEntity().getPtUserID());
		appointmentDtlsAuditEntity.setAdApptDateFk(appointmentDtlsEntity.getAdApptDateFk());
		appointmentDtlsAuditEntity.setAdApptSlotFk(appointmentDtlsEntity.getAdApptSlotFk());
		appointmentDtlsAuditEntity.setAdApptBookedFor(appointmentDtlsEntity.getAdApptBookedFor());
		appointmentDtlsAuditEntity.setAdIsbooked(appointmentDtlsEntity.getAdIsbooked());
		appointmentDtlsAuditEntity.setAdApptStatus(appointmentDtlsEntity.getAdApptStatus());
		appointmentDtlsAuditEntity.setAdPmtTransIdFk(appointmentDtlsEntity.getPaymentDtlsEntity().getTransId());
		appointmentDtlsAuditEntity.setApCancelReason(appointmentDtlsEntity.getApCancelReason());
		appointmentDtlsAuditEntity.setApCancelDate(appointmentDtlsEntity.getApCancelDate());
		appointmentDtlsAuditEntity.setAdCreatedBy(appointmentDtlsEntity.getAdCreatedBy());
		appointmentDtlsAuditEntity.setAdScrbUserId(appointmentDtlsEntity.getAdScrbUserId());
		appointmentDtlsAuditEntity.setAdCreatedTmstmp(appointmentDtlsEntity.getAdCreatedTmstmp());
		appointmentDtlsAuditEntity.setAdModifiedBy(appointmentDtlsEntity.getAdModifiedBy());
		appointmentDtlsAuditEntity.setAdModifiedTmstmp(appointmentDtlsEntity.getAdModifiedTmstmp());
		appointmentDtlsAuditEntity.setAdOptiVersion(appointmentDtlsEntity.getAdOptiVersion());
		appointmentDtlsAuditEntity.setUserId(appointmentDtlsEntity.getDocMstrDtlsEntity().getDmdUserId());
		appointmentDtlsAuditEntity.setTimestamp(LocalDateTime.now());
		auditRepository.save(appointmentDtlsAuditEntity);
		return true;

	}

}
