package com.nsdl.user.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.user.entity.ProteanIdUserEntity;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<ProteanIdUserEntity, Long> {

	@Query(value = "from ProteanIdUserEntity where uid =:uid")
	public ProteanIdUserEntity findByUid(@Param("uid") Long uid);

	@Query(value = "from ProteanIdUserEntity where mobileId =:mobileId")
	public ProteanIdUserEntity findByMobileId(@Param("mobileId") String mobileId);

}
