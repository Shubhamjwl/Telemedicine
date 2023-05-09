package com.nsdl.ndhm.transfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.ndhm.transfer.entity.CareContextsEntity;

public interface CareContextsRepository extends JpaRepository<CareContextsEntity, String> {

}
