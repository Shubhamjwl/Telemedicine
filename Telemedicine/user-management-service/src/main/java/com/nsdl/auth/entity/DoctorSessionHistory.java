package com.nsdl.auth.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "session_history", schema = "usrmgmt")
public class DoctorSessionHistory {

	@Id
	@Column(name = "sh_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	
	@Column(name = "sh_usr_id")
	private String sh_usr_id;
	
	@Column(name = "sh_session_id")
	private String sh_session_id;
	
	@Column(name = "sh_created_tmpstmp")
	private LocalDateTime sh_created_tmpstmp;

	@Column(name = "sh_end_tmpstmp")
	private LocalDateTime sh_end_tmpstmp;
	
	@Column(name = "sh_isActive")
	private String sh_isActive;

	
	public DoctorSessionHistory(Long id, String sh_usr_id, String sh_session_id, LocalDateTime sh_created_tmpstmp,
			LocalDateTime sh_end_tmpstmp, String sh_isActive) {
		super();
		Id = id;
		this.sh_usr_id = sh_usr_id;
		this.sh_session_id = sh_session_id;
		this.sh_created_tmpstmp = sh_created_tmpstmp;
		this.sh_end_tmpstmp = sh_end_tmpstmp;
		this.sh_isActive = sh_isActive;
	}



	public DoctorSessionHistory() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getSh_usr_id() {
		return sh_usr_id;
	}

	public void setSh_usr_id(String sh_usr_id) {
		this.sh_usr_id = sh_usr_id;
	}

	public String getSh_session_id() {
		return sh_session_id;
	}

	public void setSh_session_id(String sh_session_id) {
		this.sh_session_id = sh_session_id;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public LocalDateTime getSh_created_tmpstmp() {
		return sh_created_tmpstmp;
	}

	public void setSh_created_tmpstmp(LocalDateTime sh_created_tmpstmp) {
		this.sh_created_tmpstmp = sh_created_tmpstmp;
	}

	public LocalDateTime getSh_end_tmpstmp() {
		return sh_end_tmpstmp;
	}

	public void setSh_end_tmpstmp(LocalDateTime sh_end_tmpstmp) {
		this.sh_end_tmpstmp = sh_end_tmpstmp;
	}



	public String getSh_isActive() {
		return sh_isActive;
	}



	public void setSh_isActive(String sh_isActive) {
		this.sh_isActive = sh_isActive;
	}

	
}
