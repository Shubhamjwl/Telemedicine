package com.nsdl.telemedicine.consultancy.repository.audit;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.consultancy.entity.audit.AppointmentDtlsAuditEntity;


@Repository
@Transactional
public interface AppointmentDtlsAuditRepository extends JpaRepository<AppointmentDtlsAuditEntity, Long> {

}
