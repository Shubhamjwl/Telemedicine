package com.nsdl.telemedicine.videoConference.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="consult_mdcn_dtls",schema="appointment")
public class ConsultMedicationDtls implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cmd_id_pk")
	private Integer cmdIdPk;

	@Column(name="cmd_appt_id_fk")
	private String cmdApptIdFk;

	@Column(name="cmd_created_by")
	private String cmdCreatedBy;

	@Column(name="cmd_created_tmstmp")
	private Timestamp cmdCreatedTmstmp;

	@Column(name="cmd_dr_user_id_fk")
	private String cmdDrUserIdFk;

	@Column(name="cmd_medicine_dose_dtls")
	private String cmdMedicineDoseDtls;

	@Column(name="cmd_medicine_name")
	private String cmdMedicineName;

	@Column(name="cmd_medicine_type")
	private String cmdMedicineType;

	@Column(name="cmd_medicine_unit")
	private String cmdMedicineUnit;

	@Version
	@Column(name="cmd_opti_version")
	private Integer cmdOptiVersion;

	@Column(name="cmd_pt_user_id_fk")
	private String cmdPtUserIdFk;
	
	public ConsultMedicationDtls() {
		
	}

	public Integer getCmdIdPk() {
		return cmdIdPk;
	}

	public void setCmdIdPk(Integer cmdIdPk) {
		this.cmdIdPk = cmdIdPk;
	}

	public String getCmdApptIdFk() {
		return cmdApptIdFk;
	}

	public void setCmdApptIdFk(String cmdApptIdFk) {
		this.cmdApptIdFk = cmdApptIdFk;
	}

	public String getCmdCreatedBy() {
		return cmdCreatedBy;
	}

	public void setCmdCreatedBy(String cmdCreatedBy) {
		this.cmdCreatedBy = cmdCreatedBy;
	}

	public Timestamp getCmdCreatedTmstmp() {
		return cmdCreatedTmstmp;
	}

	public void setCmdCreatedTmstmp(Timestamp cmdCreatedTmstmp) {
		this.cmdCreatedTmstmp = cmdCreatedTmstmp;
	}

	public String getCmdDrUserIdFk() {
		return cmdDrUserIdFk;
	}

	public void setCmdDrUserIdFk(String cmdDrUserIdFk) {
		this.cmdDrUserIdFk = cmdDrUserIdFk;
	}

	public String getCmdMedicineDoseDtls() {
		return cmdMedicineDoseDtls;
	}

	public void setCmdMedicineDoseDtls(String cmdMedicineDoseDtls) {
		this.cmdMedicineDoseDtls = cmdMedicineDoseDtls;
	}

	public String getCmdMedicineName() {
		return cmdMedicineName;
	}

	public void setCmdMedicineName(String cmdMedicineName) {
		this.cmdMedicineName = cmdMedicineName;
	}

	public String getCmdMedicineType() {
		return cmdMedicineType;
	}

	public void setCmdMedicineType(String cmdMedicineType) {
		this.cmdMedicineType = cmdMedicineType;
	}

	public String getCmdMedicineUnit() {
		return cmdMedicineUnit;
	}

	public void setCmdMedicineUnit(String cmdMedicineUnit) {
		this.cmdMedicineUnit = cmdMedicineUnit;
	}

	public Integer getCmdOptiVersion() {
		return cmdOptiVersion;
	}

	public void setCmdOptiVersion(Integer cmdOptiVersion) {
		this.cmdOptiVersion = cmdOptiVersion;
	}

	public String getCmdPtUserIdFk() {
		return cmdPtUserIdFk;
	}

	public void setCmdPtUserIdFk(String cmdPtUserIdFk) {
		this.cmdPtUserIdFk = cmdPtUserIdFk;
	}
	
	@PrePersist
	public void prePersist() {
		this.cmdCreatedTmstmp = Timestamp.from(Instant.now());
	}

}
