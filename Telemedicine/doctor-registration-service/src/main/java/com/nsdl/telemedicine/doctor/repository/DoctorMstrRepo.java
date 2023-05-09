/**
 * 
 */
package com.nsdl.telemedicine.doctor.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.doctor.entity.DoctorMstrDtlsEntity;


/**
 * @author Pegasus_girishk
 *
 */

@Repository
@Transactional
public interface DoctorMstrRepo extends JpaRepository<DoctorMstrDtlsEntity, String> {

	@Query(value = "FROM DoctorMstrDtlsEntity where dmdUserId =:dmdUserId", nativeQuery = false)
	public DoctorMstrDtlsEntity findByDmdUserId(@Param("dmdUserId") String dmdUserId);

	@Modifying
	@Query(value = "DELETE FROM DoctorMstrDtlsEntity where dmdUserId in :dmdUserId", nativeQuery = false)
	public void deleteByDmdUserIds(@Param("dmdUserId") List<String> dmdUserId);
	
	@Query(value = "select d.* FROM registration.dr_mstr_dtls d inner join registration.dr_pt_map_dtls m on d.dmd_user_id=m.dpmd_dr_user_id_fk inner join usrmgmt.usr_dtls l  on l.ud_user_id=m.dpmd_dr_user_id_fk where m.dpmd_pt_user_id_fk = ?1 and l.ud_isactive_flg=true and d.dmd_isactive_flg=true and m.dpmd_status='Y' ", nativeQuery = true)
	public List<DoctorMstrDtlsEntity> getMappedDrListByPatientId(String patientId);
	
	@Modifying
	@Query("update DoctorMstrDtlsEntity d set d.dmdTcFlag = true where d.dmdUserId =:dmdUserId")
	int updateTermsAndConditionForDoctor(@Param("dmdUserId") String dmdUserId);
	
	@Query(value = "FROM DoctorMstrDtlsEntity where dmdEmail =:dmdEmail", nativeQuery = false)
	public DoctorMstrDtlsEntity findByDmdEmailId(@Param("dmdEmail") String dmdEmail);
}
