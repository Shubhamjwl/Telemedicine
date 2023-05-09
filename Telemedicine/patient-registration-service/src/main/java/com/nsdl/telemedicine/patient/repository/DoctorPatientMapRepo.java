package com.nsdl.telemedicine.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.patient.entity.DoctorPatientMapDtlsEntity;

@Repository
public interface DoctorPatientMapRepo extends JpaRepository<DoctorPatientMapDtlsEntity, Integer>{

	@Query(value = "select * from registration.dr_pt_map_dtls where dpmd_dr_user_Id_fk=:drUserID and dpmd_pt_user_id_fk=:ptUserID" , nativeQuery = true)
	public DoctorPatientMapDtlsEntity existsBySameDoctor(@Param("drUserID") String drUserID, @Param("ptUserID") String ptUserID);
	
	@Query(value = "select * from registration.dr_pt_map_dtls where dpmd_pt_user_id_fk=:ptUserID" , nativeQuery = true)
	public DoctorPatientMapDtlsEntity existsByPatientID(@Param("ptUserID") String ptUserID);
	
	
}







