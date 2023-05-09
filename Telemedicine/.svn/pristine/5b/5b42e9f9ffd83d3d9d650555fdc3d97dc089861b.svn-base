package com.nsdl.ndhm.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
/*@Table(name = "auth_confirm_dtls" , schema = "ndhm")*/
@Table(name = "auth_confirm_dtls")
public class AuthConfirmEntity {
	@Id
	private String requestId;
	private String timestamp;
	private String transactionId;
	@Column(columnDefinition = "TEXT")
	private String response;
	@Column(name = "isCalled", columnDefinition = "boolean default false")
	private boolean status;
	@Column(columnDefinition = "TEXT")
	private String token;
}
