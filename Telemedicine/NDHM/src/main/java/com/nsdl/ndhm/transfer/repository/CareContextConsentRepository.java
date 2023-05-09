package com.nsdl.ndhm.transfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.ndhm.transfer.entity.CareContextConsentEntity;

public interface CareContextConsentRepository extends JpaRepository<CareContextConsentEntity, String> {

	CareContextConsentEntity getByConcentId(String id);
}
