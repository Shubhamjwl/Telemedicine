package com.nsdl.telemedicine.slot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.slot.entity.DrSlotMstr;
@Repository
public interface DrSlotMstrRepository extends JpaRepository<DrSlotMstr, Integer> {

	DrSlotMstr findByDrUserIdFk(String userName);
	
	@Query(value = "select * from appointment.dr_slot_mstr where dsm_dr_user_id_fk=:userName and dsm_from_month <= :month and dsm_to_month >= :month ;", nativeQuery = true)
	DrSlotMstr getSlotCreatedDays(String userName,int month);
	

}
