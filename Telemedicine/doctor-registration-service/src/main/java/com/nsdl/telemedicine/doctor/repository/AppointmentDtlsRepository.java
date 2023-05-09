package com.nsdl.telemedicine.doctor.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.doctor.entity.AppointmentDtlsEntity;

@Repository
@Transactional
public interface AppointmentDtlsRepository extends JpaRepository<AppointmentDtlsEntity, Long>, JpaSpecificationExecutor<AppointmentDtlsEntity> {

}
