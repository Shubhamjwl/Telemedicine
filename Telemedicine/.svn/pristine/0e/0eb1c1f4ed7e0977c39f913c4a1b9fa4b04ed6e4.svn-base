package com.nsdl.auth.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.auth.entity.PatientPersonalDetailEntity;


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
	
	@Query(value = "select p.* FROM registration.pt_reg_dtls p inner join registration.dr_pt_map_dtls m on p.prd_user_id=m.dpmd_pt_user_id_fk inner join usrmgmt.usr_dtls l  on l.ud_user_id=m.dpmd_pt_user_id_fk where m.dpmd_dr_user_id_fk = ?1 and l.ud_isactive_flg=true and p.prd_isactive='Y' and m.dpmd_status='Y'" , nativeQuery = true)
	public List<PatientPersonalDetailEntity> getMappedPatientByDrId(String drId);
	
	//@Query(value = "FROM PatientPersonalDetailEntity  where isactive = 'Y' and (upper(ptFullName) like :nameOrMob or ptMobNo like :nameOrMob) ")
	@Query(value="select p.* FROM registration.pt_reg_dtls p inner join registration.dr_pt_map_dtls m on p.prd_user_id=m.dpmd_pt_user_id_fk inner join usrmgmt.usr_dtls l  on l.ud_user_id=m.dpmd_pt_user_id_fk where m.dpmd_dr_user_id_fk = :docUserId and (upper(p.prd_pt_name) like :nameOrMob or p.prd_mobile_no like :nameOrMob) and l.ud_isactive_flg=true and p.prd_isactive='Y' and m.dpmd_status='Y'" , nativeQuery = true)
	public List<PatientPersonalDetailEntity> getPatientDetailsByMobOrName(@Param("nameOrMob") String nameOrMob,@Param("docUserId") String docUserId);
	
	@Modifying
	@Query(value = "delete from registration.pt_reg_dtls where prd_user_id=:prd_user_id",nativeQuery= true)
	public int deleteByUserId(@Param("prd_user_id") String prd_user_id);
	
	@Query(value = "SELECT * FROM registration.pt_reg_dtls where prd_mobile_no =:ptMobNumber" , nativeQuery = true)
	public PatientPersonalDetailEntity existsByPatientMobNo(@Param("ptMobNumber") String ptMobNumber);
	
	
	@Query(value = "FROM PatientPersonalDetailEntity  where isactive = 'Y' and (upper(ptFullName) like :nameOrMob or ptMobNo like :nameOrMob) ")
	public List<PatientPersonalDetailEntity> getPatientDetailsByMobOrNameForCallcentre(@Param("nameOrMob") String nameOrMob);

	@Query(value = "SELECT * FROM registration.pt_reg_dtls ORDER BY prd_id_pk DESC" , nativeQuery = true)
	List<PatientPersonalDetailEntity> getAllPatient();
		
}







