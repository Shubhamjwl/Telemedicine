package com.nsdl.authenticate.id.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.nsdl.authenticate.id.entity.UinIdRepoEntity;

public interface UinIdRepoRepository extends JpaRepository<UinIdRepoEntity, String> {

	
	@Query(value="select reg_id from idrepo.uin where uin_hash=?1",nativeQuery=true)
	public Optional<UinIdRepoEntity> findByUinHash(String uin);
}
