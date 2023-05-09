package com.nsdl.patientReport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nsdl.patientReport.dto.AppointmentDTO;
import com.nsdl.patientReport.entity.AppointmentDtlsEntity;


public interface PatientReportRepo extends JpaRepository<AppointmentDtlsEntity, Integer> {
	public static final String GET_PATIENT_APPOINTMENT_DETAIL="Select new com.nsdl.patientReport.dto.AppointmentDTO(a.adApptId,a.adApptSlotFk,a.adApptDateFk,a.ad_appt_status,p.prdPtName,d.dmdDrName)"
			+ " from AppointmentDtlsEntity a,PatientRegDtlsEntity p ,DocMstrDtlsEntity d where a.patientRegDtlsEntity.prdUserId=p.prdUserId  and "
			+ "a.docMstrDtlsEntity.dmdUserId=d.dmdUserId and a.patientRegDtlsEntity.prdUserId=:userName";
	
	
	public static final String GET_DOCTOR_APPOINTMENT_DETAIL="Select new com.nsdl.patientReport.dto.AppointmentDTO(a.adApptId,a.adApptSlotFk,a.adApptDateFk,a.ad_appt_status,p.prdPtName,d.dmdDrName)"
			+ " from AppointmentDtlsEntity a,PatientRegDtlsEntity p ,DocMstrDtlsEntity d where a.patientRegDtlsEntity.prdUserId=p.prdUserId  and "
			+ "a.docMstrDtlsEntity.dmdUserId=d.dmdUserId and a.docMstrDtlsEntity.dmdUserId=:userName";
	
	
	@Query(GET_PATIENT_APPOINTMENT_DETAIL)
	public List<AppointmentDTO> getPatientAppointmentDetails(@Param("userName") String userName);
	
	@Query(GET_DOCTOR_APPOINTMENT_DETAIL)
	public List<AppointmentDTO> getDoctorAppointmentDetails(@Param("userName") String userName);
	
	

}
