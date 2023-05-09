package com.nsdl.telemedicine.consultancy.dto;

import javax.validation.constraints.NotBlank;

//@Data
public class ConsultMedicationDtlsDTO {
	
//	private Integer cmdIdPk;

	@NotBlank(message = "cmdApptIdFk cannot be empty or null")
	private String cmdApptIdFk;

	@NotBlank(message = "cmdCreatedBy cannot be empty or null")
	private String cmdCreatedBy;

	@NotBlank(message = "cmdDrUserIdFk cannot be empty or null")
	private String cmdDrUserIdFk;

	@NotBlank(message = "cmdMedicineDoseDtls cannot be empty or null")
	private String cmdMedicineDoseDtls;

	@NotBlank(message = "cmdMedicineName cannot be empty or null")
	private String cmdMedicineName;

	@NotBlank(message = "cmdMedicineType cannot be empty or null")
	private String cmdMedicineType;

	private String cmdMedicineUnit;

	@NotBlank(message = "cmdPtUserIdFk cannot be empty or null")
	private String cmdPtUserIdFk;
	
	public ConsultMedicationDtlsDTO() {
		
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

	public String getCmdPtUserIdFk() {
		return cmdPtUserIdFk;
	}

	public void setCmdPtUserIdFk(String cmdPtUserIdFk) {
		this.cmdPtUserIdFk = cmdPtUserIdFk;
	}
	
	
}
