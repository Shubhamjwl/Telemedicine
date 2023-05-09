package com.nsdl.ndhm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.ndhm.entity.SaveAuthOnConfirmRespEntity;

public interface OnConfirmResponseRepository extends JpaRepository<SaveAuthOnConfirmRespEntity, Long> {

	SaveAuthOnConfirmRespEntity findByHealthId(String healthId);

	SaveAuthOnConfirmRespEntity findByToken(String token);
}
