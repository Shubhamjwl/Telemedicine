package com.nsdl.telemedicine.videoConference.repository;

import org.springframework.data.repository.CrudRepository;

import com.nsdl.telemedicine.videoConference.entity.KarzaVCEntity;

public interface KarzaVCRepo extends CrudRepository<KarzaVCEntity, Integer> {

	public KarzaVCEntity findByKvdAppointmentId(String appointmentId);

	public KarzaVCEntity findByKvdPatientIdAndKvdAppointmentId(String patientId, String appointmentId);

}
