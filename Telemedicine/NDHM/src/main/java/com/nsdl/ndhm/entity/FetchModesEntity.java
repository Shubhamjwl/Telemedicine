package com.nsdl.ndhm.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
/*@Table(name = "fetch_modes_dtls" , schema = "ndhm")*/
@Table(name = "fetch_modes_dtls")
public class FetchModesEntity {

	@Id
	private String requestId;
	private String timestamp;
	@Column(columnDefinition = "TEXT")
	private String response;
	@Column(name = "isCalled", columnDefinition = "boolean default false")
	private boolean status;

}
