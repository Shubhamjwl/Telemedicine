package com.nsdl.telemedicine.master.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.master.entity.StateMstrEntity;
@Repository
@Transactional
public interface StateRepo extends JpaRepository<StateMstrEntity, Long> {

	@Query(value = "SELECT sm_state_name FROM master.state_mstr where sm_isactive_flg='Y' and sm_cntry_name_fk=:countryName  ORDER BY sm_state_name ;", nativeQuery = true)
	List<String> getStateList(String countryName);

}
