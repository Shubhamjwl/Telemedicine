package com.nsdl.telemedicine.consultancy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.consultancy.dto.ConsultAdviceDTO;
import com.nsdl.telemedicine.consultancy.entity.ConsultAdvDtlsEntity;


@Repository
public interface ConsultAdvDtlsRepository extends JpaRepository<ConsultAdvDtlsEntity, Long> {
	
	
	@Query("Select new com.nsdl.telemedicine.consultancy.dto.ConsultAdviceDTO(c.cadAdvice,c.cadNote) FROM ConsultAdvDtlsEntity c WHERE c.appointmentDtlsEntity.adApptId = ?1")
	public List<ConsultAdviceDTO> findDetailsByAppID(String apptId );
	
}
