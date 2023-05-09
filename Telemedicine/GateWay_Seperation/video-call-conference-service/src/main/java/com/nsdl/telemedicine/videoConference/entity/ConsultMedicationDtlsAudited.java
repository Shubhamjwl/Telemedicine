/**
 * 
 */
package com.nsdl.telemedicine.videoConference.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;

/**
 * @author Pegasus_girishk
 *
 */

@Data
@Entity
@Table(name="consult_mdcn_dtls_aud",schema="audit")
public class ConsultMedicationDtlsAudited  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	
	@Column(name="timestamp")
	private Timestamp timestamp;
	
	@Column(name="user_id")
	private String userId;
	
	@PrePersist
	public void prePersist() {
		this.timestamp = Timestamp.from(Instant.now());
	}
}
