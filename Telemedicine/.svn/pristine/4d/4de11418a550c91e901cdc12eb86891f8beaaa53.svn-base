package com.nsdl.telemedicine.slot.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.slot.entity.HolidayEntity;
@Repository
public interface HolidayRepository extends JpaRepository<HolidayEntity, Integer> {

	HolidayEntity findByDrUserIdFkAndHolidayDate(String drUserIdFk,LocalDate holidayDate);
	
	@Query(value = "select * from appointment.holiday_dtls where hd_isactive=true and hd_dr_user_id_fk=:dsd_dr_user_id_fk and EXTRACT(MONTH from hd_holiday_date)=:month and EXTRACT(YEAR from hd_holiday_date)=:year", nativeQuery = true)
	public List<HolidayEntity> getHolidayList(String dsd_dr_user_id_fk, int month,int year);
}
