package com.nsdl.ndhm.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
/* @Table(name = "on_confirm_resp_dtls", schema = "ndhm") */
@Table(name = "on_confirm_resp_dtls")
public class SaveAuthOnConfirmRespEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long confirmId;
	private String healthId;
	private String requestId;
	private String timeStamp;
	@Column(columnDefinition = "TEXT")
	private String token;
	private String name;
	private String yearOfBirth;
	@Column(columnDefinition = "TEXT")
	private String jsonResp;
	private String expiry;
	private String limits;
	private String perpose;
	private String grantStatus;

	@OneToMany(mappedBy = "saveAuthOnConfirmRespEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OnConfirmIdentifierEntity> identities = new ArrayList<OnConfirmIdentifierEntity>();

}
