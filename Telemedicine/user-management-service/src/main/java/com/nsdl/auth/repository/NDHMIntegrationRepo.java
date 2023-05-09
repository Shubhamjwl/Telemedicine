package com.nsdl.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nsdl.auth.entity.NDHMDeatilsEntity;

@Repository
@Transactional
public interface NDHMIntegrationRepo extends JpaRepository<NDHMDeatilsEntity, Integer> {

}
