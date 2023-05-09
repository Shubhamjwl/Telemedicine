package com.nsdl.ipan.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MEDICINES", schema = "master")
public class Medicines implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "USERS_ID")
	private Long solrId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSolrId() {
		return solrId;
	}

	public void setSolrId(Long solrId) {
		this.solrId = solrId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, solrId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medicines other =(Medicines) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(solrId, other.solrId);
	}

	@Override
	public String toString() {
		return "Advice [id=" + id + ", name=" + name + ", solrid=" + solrId + "]";
	}

}
