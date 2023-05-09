package com.nsdl.telemedicine.videoConference.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nsdl.telemedicine.videoConference.entity.BBBMeetingEntity;

public interface BBBMeetingRepo extends CrudRepository<BBBMeetingEntity, Integer> {
	
	
	@Query(value="FROM BBBMeetingEntity where meetingId=:meetingId ")
	public BBBMeetingEntity findByMeetingId(@Param("meetingId") String meetingId);

}
