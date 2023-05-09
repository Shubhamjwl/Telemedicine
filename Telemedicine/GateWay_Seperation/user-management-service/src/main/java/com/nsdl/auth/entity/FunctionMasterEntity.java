package com.nsdl.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "func_mstr", schema = "usrmgmt")
public class FunctionMasterEntity extends BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID =  -8541947597557595643L;
	
	@Column(name ="fm_func", nullable = false)	
	private int ID;	
	
	@Id
	@Column(name ="fm_func_name_pk", length = 100)
	private String functionName;
}
