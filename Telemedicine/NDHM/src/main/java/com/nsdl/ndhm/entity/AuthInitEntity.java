package com.nsdl.ndhm.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
/*@Table(name = "auth_init_dtls", schema = "ndhm")*/
@Table(name = "auth_init_dtls")
@NamedNativeQueries(
	    value={
	            // cast is used for Hibernate, to prevent No Dialect mapping for JDBC type: 1111
	            @NamedNativeQuery(
	                  name = "getCareContextDetails",
	                 query = "select cast(getcarecontext_by_linkrefno(?) as text)"
	            )
	     }
	)
public class AuthInitEntity {
	@Id
	private String requestId;
	private String timestamp;
	private String transactionId;
	@Column(columnDefinition = "TEXT")
	private String response;
	@Column(name = "isCalled", columnDefinition = "boolean default false")
	private boolean status;
	private String expiry;
}
