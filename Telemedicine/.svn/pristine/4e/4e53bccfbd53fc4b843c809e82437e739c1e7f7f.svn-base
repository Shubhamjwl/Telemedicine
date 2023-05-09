package com.nsdl.telemedicine.consultancy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nsdl.telemedicine.consultancy.entity.InvestigationDtlsEntity;

public interface InvestigationDtlsRepository extends JpaRepository<InvestigationDtlsEntity, Integer> {

	/*
	 * @Query("Select new com.nsdl.telemedicine.consultancy.dto.InvestigationDetailsDTO(c.cidApptUlReportName, c.cidApptUlReportType, "
	 * +
	 * "c.spo2Level, c.hb, c.pulse, c.height, c.weight, c.ofc, c.systolic, c.diastolic, c.respirationRate, c.pefr,"
	 * +
	 * "c.physicalExam, c.allergiesHistory, c.familyHistory, c.cidInvesNote) FROM InvestigationDtlsEntity c WHERE c.appointmentDtlsEntity.adApptId = ?1"
	 * )
	 */
	@Query(value = "SELECT * FROM appointment.consult_inves_dtls c where c.cid_appt_id_fk =:adApptId", nativeQuery = true)
	public List<InvestigationDtlsEntity> findByCcidApptIdFk(@Param("adApptId") String adApptId);

	// @Query(value = "SELECT * FROM appointment.consult_inves_dtls c where
	// c.cid_appt_id_fk =:adApptId", nativeQuery = true)
	/*
	 * @Query("Select new com.nsdl.telemedicine.consultancy.dto.InvestigationDetailsDTO(c.cidApptUlReportName,c.cidApptUlReportType,c.spo2Level,c.hb,c.pulse,c.height,c.weight,c.ofc,c.systolic,c.diastolic,c.respirationRate,c.pefr,c.physicalExam,c.allergiesHistory,c.familyHistory,c.cidInvesNote) FROM InvestigationDtlsEntity c WHERE c.appointmentDtlsEntity.adApptId = ?1"
	 * ) public List<InvestigationDtlsEntity> findInverstListByCcidApptIdFk(String
	 * adApptId);
	 */

}
