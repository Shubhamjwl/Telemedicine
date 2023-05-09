package com.nsdl.telemedicine.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nsdl.telemedicine.entity.MarketPlaceDetailsEntity;

public interface MarketPlaceDtlsRepo extends CrudRepository<MarketPlaceDetailsEntity, Long> {

	@Query("FROM MarketPlaceDetailsEntity m WHERE m.modTranstIid = ?1")
	public Iterable<MarketPlaceDetailsEntity> findByTranstId(String modTranstIid);
	
	@Query("FROM MarketPlaceDetailsEntity m WHERE m.modTranstIid = ?1")
	public List<MarketPlaceDetailsEntity> isRecordExist(String modTranstIid);
	
	@Query(value="FROM MarketPlaceDetailsEntity where (upper(modDrEmail) =:modDrEmail and modOrderDate =:modOrderDate) \r\n"
			+ "or (upper(modDrEmail) = :modDrEmail and upper(modPtNname) =:modPtNname)\r\n"
			+ "or (upper(modDrEmail) = :modDrEmail and modOrderId =:modOrderId)")
	public List<MarketPlaceDetailsEntity> searchByDoctorTwoParameters(@Param("modDrEmail")String modDrEmail,@Param("modOrderDate")Date modOrderDate,@Param("modPtNname")String modPtNname,@Param("modOrderId")String modOrderId);
	
	
	@Query(value="FROM MarketPlaceDetailsEntity where \r\n"
			+ "(upper(modDrEmail) = :modDrEmail and upper(modPtNname) =:modPtNname and modOrderId =:modOrderId)\r\n"
			+ "or (upper(modDrEmail) =:modDrEmail and upper(modPtNname) =:modPtNname and modOrderDate =:modOrderDate) or (upper(modDrEmail) =:modDrEmail and modOrderId=:modOrderId and modOrderDate =:modOrderDate)")
	public List<MarketPlaceDetailsEntity> searchByDoctorThreeParameters(@Param("modDrEmail")String modDrEmail,@Param("modOrderDate")Date modOrderDate,@Param("modPtNname")String modPtNname,@Param("modOrderId")String modOrderId);
	
	
	@Query(value="FROM MarketPlaceDetailsEntity m WHERE (upper(m.modDrEmail) =:modDrEmail and upper(m.modPtNname) =:modPtNname and m.modOrderId =:modOrderId and m.modOrderDate =:modOrderDate )")
	public List<MarketPlaceDetailsEntity> searchByDoctorFourParameters(@Param("modDrEmail")String modDrEmail,@Param("modOrderDate")Date modOrderDate,@Param("modPtNname")String modPtNname,@Param("modOrderId")String modOrderId);
	
	
	
	@Query(value="FROM MarketPlaceDetailsEntity m WHERE (upper(m.modPtNname) =:modPtNname and m.modOrderDate =:modOrderDate) \r\n"
			+ "or (upper(m.modPtNname) =:modPtNname and m.modOrderId =:modOrderId)")
	public List<MarketPlaceDetailsEntity> searchByPatientOneParameters(@Param("modOrderDate")Date modOrderDate,@Param("modOrderId")String modOrderId,@Param("modPtNname")String modPtNname);
	
	
	@Query(value="FROM MarketPlaceDetailsEntity m WHERE (upper(m.modPtNname) =:modPtNname and m.modOrderId =:modOrderId and m.modOrderDate =:modOrderDate )")
	public List<MarketPlaceDetailsEntity> searchByPatientTwoParameters(@Param("modOrderDate")Date modOrderDate,@Param("modOrderId")String modOrderId,@Param("modPtNname")String modPtNname);


}
