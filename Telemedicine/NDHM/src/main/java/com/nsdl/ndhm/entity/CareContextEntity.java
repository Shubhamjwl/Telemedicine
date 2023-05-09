package com.nsdl.ndhm.entity;

import com.nsdl.ndhm.generators.StringPrefixedSequenceIdGenerator;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
/*@Table(name = "patient_care_contexts" , schema = "ndhm")*/
@Table(name = "patient_care_contexts")
public class CareContextEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "care_context_seq")
	@GenericGenerator(name = "care_context_seq", strategy = "com.nsdl.ndhm.generators.StringPrefixedSequenceIdGenerator", parameters = {
			@Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "01"),
			@Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "CARE_CNTX_"),
			@Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	private String careContextId;
	private String displayName;
	private String patientId;
	private String patientName;
	private String mobileNo;
	private String healthId;
	private String healthNo;
	private String aadhaarNo;
}
