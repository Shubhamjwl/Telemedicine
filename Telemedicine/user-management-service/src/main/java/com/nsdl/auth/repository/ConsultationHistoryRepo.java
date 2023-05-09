package com.nsdl.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nsdl.auth.entity.ConsultationHistoryEntity;

@Transactional
@Repository
public interface ConsultationHistoryRepo extends JpaRepository<ConsultationHistoryEntity, String>{

	
	 @Modifying
	 @Query(value = "UPDATE usrmgmt.session_history SET sh_session_id =:sessId WHERE sh_id =:shId", nativeQuery=true)
	 public void updateSessionId(@Param("sessId") String sessId,@Param("shId") Long shId);
	
	 @Modifying
	 @Query(value = "UPDATE appointment.consultation_history SET ch_flag =false WHERE ch_session_id =:sessId", nativeQuery=true)
	 public void updateFlagToFalse(@Param("sessId") String sessionId);

	 @Modifying
	 @Query(value = "DELETE FROM appointment.consultation_history WHERE ch_session_id =:sessId AND ch_usr_id =:userId AND ch_flag =:flag", nativeQuery=true)
	 public void deleteNullRecord(@Param("sessId") String sessionId, @Param("userId") String userId, @Param("flag") String flag);

	//public void saveDataToConsultationHistory(String sessionid, boolean flag);

}
