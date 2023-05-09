package com.nsdl.telemedicine.master.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.master.entity.CountryMstrEntity;
@Repository
@Transactional
public interface CountryRepo extends JpaRepository<CountryMstrEntity, Long> {

	@Query(value = "SELECT cm_cntry_name FROM master.cntry_mstr where cm_isactive_flg='Y'  ORDER BY cm_cntry_name ;", nativeQuery = true)
	public List<Object[]> getCountryList();

}
