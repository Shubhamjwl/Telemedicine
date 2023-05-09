package com.nsdl.telemedicine.consultancy.repository.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.consultancy.entity.audit.AuditConsultPriscpDtl;

@Repository
public interface AuditConsultPriscpDtlRepository extends JpaRepository<AuditConsultPriscpDtl, Long> {

}
