package com.nsdl.ndhm.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
/*@Table(name = "on_confirm_patient_idnt_dtls" , schema = "ndhm")*/
@Table(name = "on_confirm_patient_idnt_dtls")
public class OnConfirmIdentifierEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private String type;

	private String value;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "confirm_id" ,nullable = false)
    private SaveAuthOnConfirmRespEntity saveAuthOnConfirmRespEntity;
}
