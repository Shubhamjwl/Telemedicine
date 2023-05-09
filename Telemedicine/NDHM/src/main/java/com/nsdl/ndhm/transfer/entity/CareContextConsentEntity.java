package com.nsdl.ndhm.transfer.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
/*@Table(name = "carecontext_consent_mappings", schema = "ndhm")*/
@Table(name = "carecontext_consent_mappings")
public class CareContextConsentEntity {

	@Id
	private String requestId;
	private String timestamp;
	private String transactionId;
	private String concentId;
	private String patientId;
	private String perpose;
	private String hip;
	private String consentManager;
	private String accessMode;
	private String dataEraseAt;

	@OneToMany(mappedBy = "careContextConsentEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<CareContextsEntity> careContexts;
}
