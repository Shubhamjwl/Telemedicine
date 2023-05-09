package com.nsdl.telemedicine.patient.repository;

import java.time.LocalDateTime;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.patient.entity.PatientPersonalDetailEntity;

@Repository
public interface PatientPersonalDetailsRepository extends JpaRepository<PatientPersonalDetailEntity, Integer>{

	@Query(value = "SELECT * FROM registration.pt_reg_dtls r WHERE r.prd_user_id = ?1",nativeQuery = true)
	PatientPersonalDetailEntity findByPtUserID(String ptUserID);

	@Modifying
	@Transactional
	@Query(value = "UPDATE registration.pt_reg_dtls SET prd_pt_name = ?1 ,prd_email=?2 , prd_mobile_no=?3, prd_height = ?4, prd_weight = ?5, prd_blood_grp = ?6, prd_dob = ?7, prd_address1 = ?8, prd_address2 =?9,  prd_address3 = ?10,  prd_modified_tmstmp = ?11 "
			+ " ,prd_photo_path =?12 ,prd_gender=?13 , prd_city_name_fk=?14 , prd_state_name_fk=?15 , prd_cntry_name_fk = ?16 WHERE prd_user_id = ?17", nativeQuery = true)
	int updateByPtUserID(String ptFullName , String ptEmail ,Long ptMobNo ,Double height, Double weight, String bloodgrp, Date dob, String address1,
			String address2, String address3, LocalDateTime now, String profilePhotoPath , String gender , String ptCity , String ptState , String ptCountry , String ptUserID );

	boolean existsByPtUserID(String ptUserID);
	
	boolean existsByptEmail(String ptEmail);
	
	@Query(value = "SELECT * FROM registration.pt_reg_dtls where prd_mobile_no = ?1" , nativeQuery = true)
	PatientPersonalDetailEntity existsByPtMobNo(String ptMobNo);
	
	@Query(value = "SELECT concat(prd_mobile_no,'_',prd_user_id) FROM registration.pt_reg_dtls where prd_mobile_no in (:ptMobNumbers)" , nativeQuery = true)
	public List<String> checkIfPtMobileNoExist(@Param("ptMobNumbers") List<String> ptMobNumberList);
}







