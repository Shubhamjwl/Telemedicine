package com.nsdl.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.auth.entity.UserHistory;

@Repository
public interface UserHistoryRepo extends JpaRepository<UserHistory, Integer> {

	@Query(value = "SELECT id,ud_user_id,ud_password,ud_created_by,ud_created_tmstmp FROM usrmgmt.usr_dtls_his where ud_user_id =:userId ORDER BY ud_created_tmstmp DESC limit :passLimit", nativeQuery = true)
	List<UserHistory> checkLastThreePassword(@Param("userId") String userId,@Param("passLimit")Long passLimit);

}
