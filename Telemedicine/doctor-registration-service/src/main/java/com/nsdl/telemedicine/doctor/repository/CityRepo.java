package com.nsdl.telemedicine.doctor.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.doctor.entity.CityMstrEntity;

@Repository
@Transactional
public interface CityRepo extends JpaRepository<CityMstrEntity, Long> {

	@Query(value = "SELECT cm_city_name FROM master.city_mstr where cm_isactive_flg='Y' and cm_state_name_fk=:stateName  ORDER BY cm_city_name ;", nativeQuery = true)
	List<String> getCityList(String stateName);

}
