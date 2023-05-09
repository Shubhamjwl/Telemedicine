package com.nsdl.ndhm.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
/*@Table(name = "dis_linked_carecontext_dtls" , schema = "ndhm")*/
@Table(name = "dis_linked_carecontext_dtls")
public class DisLinkedCareContextEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String careContextReferenceNo;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "link_ref_no", nullable = false)
	private DisLinkInitEntity disLinkInitEntity;
}
