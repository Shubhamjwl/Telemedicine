package com.nsdl.telemedicine.master.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.master.entity.MasterEntity;

@Repository
@Transactional
public interface MasterRepo extends JpaRepository<MasterEntity, String>{

	@Query(value = "SELECT mt_master_name,mt_master_value,COALESCE(mt_master_unit,'') FROM master.mstr_tbl where mt_master_name =:masterName and mt_isactive_flg ='Y'  ORDER BY mt_master_name ;", nativeQuery = true)
	public List<Object[]> getMasterDetailsListByMasterName(@Param("masterName") String masterName);
	
	@Query(value = "SELECT distinct mt_master_name FROM master.mstr_tbl where mt_isactive_flg ='Y'  ORDER BY mt_master_name ;", nativeQuery = true)
	public List<Object[]> getMasterList();
	
	@Query(value = "select count(*) from master.mstr_tbl where lower(mt_master_name)='doctor specialiazation'", nativeQuery = true)
	public Long getCountOfSpeciality();
	
}
