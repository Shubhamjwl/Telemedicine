package com.nsdl.ndhm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.ndhm.entity.DisLinkInitEntity;

public interface DisLinkInitRepository extends JpaRepository<DisLinkInitEntity, String> {
	DisLinkInitEntity findByLinkReferenceNo(String linkReferenceNo);

}
