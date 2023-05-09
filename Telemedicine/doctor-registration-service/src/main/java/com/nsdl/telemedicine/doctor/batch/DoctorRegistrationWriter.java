package com.nsdl.telemedicine.doctor.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nsdl.telemedicine.doctor.entity.DoctorAuditDtlsEntity;
import com.nsdl.telemedicine.doctor.entity.DoctorRegDtlsEntity;
import com.nsdl.telemedicine.doctor.repository.DoctorAuditRepo;
import com.nsdl.telemedicine.doctor.repository.RegistrationRepo;

public class DoctorRegistrationWriter implements ItemWriter<DoctorRegDtlsEntity> {

	@Autowired
	RegistrationRepo registrationRepo;

	@Autowired
	DoctorAuditRepo doctorAuditRepo;

	private static final Logger LOGGER = LoggerFactory.getLogger(DoctorRegistrationWriter.class);

	@Override
	public void write(List<? extends DoctorRegDtlsEntity> entities) throws Exception {
		LOGGER.info("Received the information of {} doctors", entities.size());
		try {
			registrationRepo.saveAll(entities);

			// Save Data to Audit Table
			List<DoctorAuditDtlsEntity> audits = new ArrayList<>();
			for (DoctorRegDtlsEntity doctor : entities) {
				DoctorAuditDtlsEntity auditDtls = new DoctorAuditDtlsEntity();
				BeanUtils.copyProperties(doctor, auditDtls);
				auditDtls.setUserId(doctor.getDrdUserId());
				audits.add(auditDtls);
			}
			doctorAuditRepo.saveAll(audits);
		} catch (Exception e) {
			LOGGER.info("Registration failed for {} doctors",
					entities.stream().map(DoctorRegDtlsEntity::getDrdDrFirstName).collect(Collectors.toList()));
			e.printStackTrace();
		}

	}

}
