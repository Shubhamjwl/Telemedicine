/*package com.nsdl.telemedicine.consultancy.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.context.junit4.SpringRunner;

import com.nsdl.telemedicine.consultancy.constant.ConsultationConstants;
import com.nsdl.telemedicine.consultancy.dto.AppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.DrAppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.PtAppointmentDTO;
import com.nsdl.telemedicine.consultancy.entity.AppointmentDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.DocMstrDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.PatientRegDtlsEntity;
import com.nsdl.telemedicine.consultancy.exception.ConsultationServiceException;
import com.nsdl.telemedicine.consultancy.repository.DocMstrDtlsRepository;
import com.nsdl.telemedicine.consultancy.service.impl.AppointmentServiceImpl;

@RunWith(SpringRunner.class)
public class AppointmentServiceImplTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@InjectMocks
	private AppointmentServiceImpl appointmentServiceImpl;

	@Mock
	private DocMstrDtlsRepository docMstrDtlsRepository;

	DateTimeFormatter sdfo = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Before
	public void setUp() throws ParseException {
	}

	@Test
	public void getAppointmentListByPatientIDTest() throws Exception {

		String ptRegID = "pt2";
		PtAppointmentDTO ptAppointmentDTO = new PtAppointmentDTO();
		ptAppointmentDTO.setPtRegID("pt2");
		List<AppointmentDTO> apptDtls = new ArrayList<>();
		AppointmentDTO appointmentDTO = new AppointmentDTO();
		appointmentDTO.setAppointmentDate("17 11 2020");
		appointmentDTO.setAppointmentID("12635127");
		appointmentDTO.setAppointmentTime("11.30");
		appointmentDTO.setDoctorName("AMOL");
		appointmentDTO.setStatus("Scheduled");
		apptDtls.add(appointmentDTO);
		ptAppointmentDTO.setApptDtls(apptDtls);

		PatientRegDtlsEntity patientRegDtls = new PatientRegDtlsEntity();
		List<AppointmentDtlsEntity> appointmentList = new ArrayList<AppointmentDtlsEntity>();
		AppointmentDtlsEntity appointment = new AppointmentDtlsEntity();
		appointment.setAdApptDateFk(LocalDate.parse("2020-11-17", sdfo));
		appointment.setAdApptSlotFk("11.30");
		appointment.setAdApptId("12635127");
		DocMstrDtlsEntity docMstrDtlsEntity = new DocMstrDtlsEntity();
		docMstrDtlsEntity.setDmdDrName("AMOL");
		appointment.setDocMstrDtlsEntity(docMstrDtlsEntity);
		appointment.setAdApptStatus("S");
		appointmentList.add(appointment);
		patientRegDtls.setAppointmentDtlsEntity(appointmentList);
		
		assertThat(ptAppointmentDTO)
				.isEqualToComparingFieldByField(appointmentServiceImpl.getAppointmentListByPatientID());
	}

	@Test(expected = ConsultationServiceException.class)
	public void getAppointmentListByPatientIDNegativeTest() throws Exception {

		String ptRegID = "";
		appointmentServiceImpl.getAppointmentListByPatientID();
	}

	@Test
	public void getAppointmentListByDrIDTest() throws Exception {

		String drRegID = "722";
		DrAppointmentDTO drAppointmentDTO = new DrAppointmentDTO();
		drAppointmentDTO.setDrRegID("722");

		List<AppointmentDTO> apptDtls = new ArrayList<>();
		AppointmentDTO appointmentDTO = new AppointmentDTO();
		appointmentDTO.setAppointmentDate("17 11 2020");
		appointmentDTO.setAppointmentID("12635127");
		appointmentDTO.setAppointmentTime("11.30");
		appointmentDTO.setPatientName("pt2");
		appointmentDTO.setDoctorName("dr2");
		appointmentDTO.setStatus("Scheduled");
		apptDtls.add(appointmentDTO);
		Map<String, List<AppointmentDTO>> apptDtlsMap = new LinkedHashMap<String, List<AppointmentDTO>>(); 
		apptDtlsMap.put(ConsultationConstants.TODAY.getValue(), apptDtls);
		drAppointmentDTO.setApptDtls(apptDtlsMap);

		DocMstrDtlsEntity docMstrDtls = new DocMstrDtlsEntity();
		docMstrDtls.setDmdDrName("dr2");
		List<AppointmentDtlsEntity> appointmentList = new ArrayList<AppointmentDtlsEntity>();
		AppointmentDtlsEntity appointment = new AppointmentDtlsEntity();
		appointment.setAdApptDateFk(LocalDate.parse("2020-11-17", sdfo));
		appointment.setAdApptSlotFk("11.30");
		appointment.setAdApptId("12635127");
		appointment.setAdApptStatus("S");
		PatientRegDtlsEntity patientRegDtls = new PatientRegDtlsEntity();
		patientRegDtls.setPrdName("pt2");
		appointment.setPatientRegDtlsEntity(patientRegDtls);
		appointmentList.add(appointment);
		docMstrDtls.setAppointmentDtlsEntity(appointmentList);

		Mockito.when(docMstrDtlsRepository.findByDmdUserId(Mockito.anyString())).thenReturn(docMstrDtls);

		assertThat(drAppointmentDTO)
				.isEqualToComparingFieldByField(appointmentServiceImpl.getAppointmentListByDrID("2020-11-17"));
	}

	@Test(expected = ConsultationServiceException.class)
	public void getAppointmentListByDrIDNegativeTest() throws Exception {

		String drRegID = "722";
		Mockito.when(docMstrDtlsRepository.findByDmdUserId(Mockito.anyString())).thenReturn(null);

		appointmentServiceImpl.getAppointmentListByDrID("2020-11-17");
	}

}
*/