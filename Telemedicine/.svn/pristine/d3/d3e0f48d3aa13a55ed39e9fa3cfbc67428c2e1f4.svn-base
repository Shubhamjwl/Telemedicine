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
/*@Table(name = "add_context_dtls"  , schema = "ndhm")*/
@Table(name = "add_context_dtls")
public class AddContextEntity {
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
