package com.nsdl.telemedicine.doctor.exception;

public class DoctorRegistrationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final ServiceErrors list;

	public DoctorRegistrationException(ServiceErrors list) {
		this.list = list;
	}

	public ServiceErrors getList() {
		return list;
	}
}
