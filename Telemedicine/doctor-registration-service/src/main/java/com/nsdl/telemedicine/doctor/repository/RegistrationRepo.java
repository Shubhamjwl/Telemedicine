package com.nsdl.telemedicine.doctor.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.doctor.entity.DoctorRegDtlsEntity;

/**
 * @author SayaliA
 *
 */
@Repository
@Transactional
public interface RegistrationRepo extends JpaRepository<DoctorRegDtlsEntity, String> {

	@Query(value = "select * from registration.dr_reg_dtls where drd_user_id=:documentUserID", nativeQuery = true)
	public DoctorRegDtlsEntity findDoctorDetailsByUserID(@Param("documentUserID") String documentUserID);

	@Query(value = "FROM DoctorRegDtlsEntity where drdJobId=:jobId", nativeQuery = false)
	public List<DoctorRegDtlsEntity> findDoctorsByJobId(@Param("jobId") String jobId);

	@Query(value = "select * from registration.dr_reg_dtls where drd_user_id=:drUserID", nativeQuery = true)
	public Optional<DoctorRegDtlsEntity> findByDocUserID(@Param("drUserID") String drUserID);

	@Query(value = "select * from registration.dr_reg_dtls where drd_mobile_no=:drMobNo", nativeQuery = true)
	public Optional<DoctorRegDtlsEntity> findByDocMobNo(@Param("drMobNo") String drMobNo);

	@Query(value = "select drd_mobile_no from registration.dr_reg_dtls where drd_mobile_no in :drMobNo", nativeQuery = true)
	public List<String> findByDocMobNos(@Param("drMobNo") List<String> drMobNo);

	@Query(value = "select * from registration.dr_reg_dtls where drd_email=:emailID", nativeQuery = true)
	public Optional<DoctorRegDtlsEntity> findByDocEmail(@Param("emailID") String emailID);

	@Query(value = "select drd_email from registration.dr_reg_dtls where drd_email in :emailID", nativeQuery = true)
	public List<String> findByDocEmails(@Param("emailID") List<String> emailID);

	@Modifying
	@Query(value = "delete from registration.dr_reg_dtls where drd_user_id=:drd_user_id", nativeQuery = true)
	public int deleteByUserId(@Param("drd_user_id") String drd_user_id);

	@Modifying
	@Query(value = "update registration.dr_reg_dtls set drd_tc_flag=true where drd_user_id=:drd_user_id", nativeQuery = true)
	int updateTermsAndConditionForDoctor(@Param("drd_user_id") String drd_user_id);

	public List<DoctorRegDtlsEntity> findByDrdAssociationNumberIgnoreCaseOrDrdSmcNumberIgnoreCaseOrDrdMciNumberIgnoreCase(
			String associationNumber, String smcNumber, String mciNumber);

	public List<DoctorRegDtlsEntity> findByDrdSmcNumberIgnoreCaseOrDrdMciNumberIgnoreCase(String smcNumber,
			String mciNumber);

}
