package com.nsdl.auth.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "role_function_dtls", schema = "usrmgmt")
public class RoleFunctionEntity extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID =  -8541947597557596709L;
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="rfd_rolefunc_pk", nullable = false)	
	private int ID;	
	
	
	@Column(name = "rfd_role_name_fk", nullable = false)
	private String roleName;
	
	@Column(name = "rfd_function_fk", nullable = false)
	private String functionName;
	
	@Column(name = "rfd_function_uri",nullable = false)
	private String functionUri;
	
}
