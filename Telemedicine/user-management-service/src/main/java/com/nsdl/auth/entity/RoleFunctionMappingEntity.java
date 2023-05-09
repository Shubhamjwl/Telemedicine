package com.nsdl.auth.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "role_func_mapping", schema = "usrmgmt")
public class RoleFunctionMappingEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="rfm_id_pk",  nullable = false)
	private Long id;	
	
	@Column(name = "rfm_role_name")
	private String roleName;
	
	@Column(name = "rfm_menu_name")
	private String menuName;
	
	@Column(name = "rfm_submenu_name")
	private String subMenuName;
	
	@Column(name = "rfm_function_name")
	private String functionName;
	
	@Column(name = "rfm_route")
	private String route;
	
	@Column(name = "rfm_cr_by")
	private String createdBy;
	
	@Column(name = "rfm_cr_dtimes")
	private LocalDateTime createdtime;
	
	@Column(name = "rfm_is_active")
	private Boolean isActive;
	
	@Column(name = "rfm_is_deleted")
	private Boolean isDeleted;
	
	@Column(name = "rfm_upd_by")
	private String updatedBy;
	
	@Column(name = "rfm_upd_dtimes")
	private LocalDateTime updatedTime;
	
	@PrePersist
	public void prePersist() {
		this.createdtime = LocalDateTime.now();
		this.updatedTime = LocalDateTime.now();
	}
	
}

