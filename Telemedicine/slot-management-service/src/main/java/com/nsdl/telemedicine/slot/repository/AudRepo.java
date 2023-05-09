package com.nsdl.telemedicine.slot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.telemedicine.slot.entity.AudDocSlotDtlsEntity;

public interface AudRepo extends JpaRepository<AudDocSlotDtlsEntity, Long> {

}
