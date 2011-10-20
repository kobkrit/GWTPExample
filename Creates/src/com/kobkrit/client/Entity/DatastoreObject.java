package com.kobkrit.client.Entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.PrePersist;

public class DatastoreObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private Integer version = 0;

	/**
	 * Auto-increment version # whenever persisted
	 */
	@PrePersist
	void onPersist() {
		this.version++;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
