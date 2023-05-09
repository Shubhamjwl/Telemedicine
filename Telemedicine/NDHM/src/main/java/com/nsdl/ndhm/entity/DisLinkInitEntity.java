package com.nsdl.ndhm.entity;

import com.nsdl.ndhm.generators.StringPrefixedSequenceIdGenerator;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.List;
@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
/*@Table(name = "discover_linking_init" , schema = "ndhm")*/
@Table(name = "discover_linking_init")
public class DisLinkInitEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Discover_Link_Init_seq")
	@GenericGenerator(name = "Discover_Link_Init_seq", strategy = "com.nsdl.ndhm.generators.StringPrefixedSequenceIdGenerator", parameters = {
			@Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "01"),
			@Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "LINK_REF_"),
			@Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	private String linkReferenceNo; 
	private String requestId;
	private String timestamp;
	private String transactionId;	
	private String commnMedium;
	private String commnExpire;
	private String patientId;
	private String patientReferenceNo;
	private String authModes;
	private String otp;
	
	@OneToMany(mappedBy="disLinkInitEntity",fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<DisLinkedCareContextEntity> careContexts;
	
}
