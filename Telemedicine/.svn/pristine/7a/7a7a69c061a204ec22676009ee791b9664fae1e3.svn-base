package com.nsdl.ndhm.transfer.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
/*@Table(name = "CareContexts", schema = "ndhm")*/
@Table(name = "CareContexts")
public class CareContextsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String patientReference;
	private String careContextReference;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "concent_Id", nullable = false)
	private CareContextConsentEntity careContextConsentEntity;

}
