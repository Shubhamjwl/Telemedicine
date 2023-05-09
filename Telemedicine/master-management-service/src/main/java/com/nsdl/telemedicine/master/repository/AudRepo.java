package com.nsdl.telemedicine.master.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.master.entity.AudtMasterEntity;

@Repository
@Transactional
public interface AudRepo extends JpaRepository<AudtMasterEntity, String>{

}
