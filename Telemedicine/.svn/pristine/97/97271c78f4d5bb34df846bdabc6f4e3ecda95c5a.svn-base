package com.nsdl.telemedicine.consultancy.service;

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
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.nsdl.telemedicine.consultancy.constant.ConsultationConstants;
import com.nsdl.telemedicine.consultancy.dto.AppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.DrAppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.PtAppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.UserDTO;
import com.nsdl.telemedicine.consultancy.entity.AppointmentDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.DocMstrDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.PatientRegDtlsEntity;
import com.nsdl.telemedicine.consultancy.exception.ConsultationServiceException;
import com.nsdl.telemedicine.consultancy.repository.AppointmentDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.DocMstrDtlsRepository;
import com.nsdl.telemedicine.consultancy.service.impl.AppointmentServiceImpl;

/**
 * @author Pegasus_girishk
 *
 */
@RunWith(SpringRunner.class)
public class AppointmentServiceImplTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@InjectMocks
	private AppointmentServiceImpl appointmentServiceImpl;

	@Mock
	private DocMstrDtlsRepository docMstrDtlsRepository;
	
	@Mock
	private AppointmentDtlsRepository appointmentDtlsRepository;

	DateTimeFormatter sdfo = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	UserDTO userDTO = new UserDTO();
	List<AppointmentDTO> appointmentDtls = new ArrayList<AppointmentDTO>();
	List<AppointmentDTO> appointmentDtls1 = new ArrayList<AppointmentDTO>();

	@Before
	public void setUp() throws ParseException {
		userDTO.setUserName("pt2");
		userDTO.setRole("DOCTOR");
		ReflectionTestUtils.setField(appointmentServiceImpl, "userDTO", userDTO);
		
		AppointmentDTO appointmentDTO = new AppointmentDTO();
		appointmentDTO.setAppointmentID("12635127");
		appointmentDTO.setAppointmentDate("2020-11-17");
		appointmentDTO.setAppointmentTime("11.30");
		appointmentDTO.setDoctorSpeciality("MD");
		appointmentDTO.setDoctorName("AMOL");
		appointmentDTO.setDoctorUserId("MONIKADOC1");
		appointmentDTO.setStatus("Scheduled");
		appointmentDTO.setPatientName("TESTPATIENT");
		appointmentDTO.setPatientRegId("pt2");
		appointmentDtls1.add(appointmentDTO);//list without profile photo
		AppointmentDTO appointmentDTO1 =  new AppointmentDTO();
		BeanUtils.copyProperties(appointmentDTO, appointmentDTO1);
		appointmentDTO1.setProfilePhoto("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/4RCiRXhpZgAATU0AKgAAAAgABAE7AAIAAAAQAAAISodpAAQAAAABAAAI");
		appointmentDtls.add(appointmentDTO1); //list with profile photo
	}

	@Test
	public void getAppointmentListByPatientIDTest() throws Exception {
		PtAppointmentDTO ptAppointmentDTO = new PtAppointmentDTO();
		ptAppointmentDTO.setPtRegID("pt2");
		ptAppointmentDTO.setApptDtls(appointmentDtls);

		List<AppointmentDtlsEntity> appointmentList = new ArrayList<AppointmentDtlsEntity>();
		AppointmentDtlsEntity appointment = new AppointmentDtlsEntity();
		appointment.setAdApptDateFk(LocalDate.parse("2020-11-17", sdfo));
		appointment.setAdApptSlotFk("11.30");
		appointment.setAdApptId("12635127");
		appointment.setAdApptStatus("Scheduled");
		appointment.setAdApptBookedFor("TESTPATIENT");
		
		PatientRegDtlsEntity patientRegDtls = new PatientRegDtlsEntity();
		patientRegDtls.setPrdUserId("pt2");
		appointment.setPatientRegDtlsEntity(patientRegDtls);
		
		DocMstrDtlsEntity docMstrDtlsEntity = new DocMstrDtlsEntity();
		docMstrDtlsEntity.setDmdDrName("AMOL");
		docMstrDtlsEntity.setProfilePhoto("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/4RCiRXhpZgAATU0AKgAAAAgABAE7AAIAAAAQAAAISodpAAQAAAABAAAI");
		docMstrDtlsEntity.setDmdUserId("MONIKADOC1");
		docMstrDtlsEntity.setDmdSpecialiazation("MD");
		appointment.setDocMstrDtlsEntity(docMstrDtlsEntity);
		
		appointmentList.add(appointment);
		
		Mockito.when(appointmentDtlsRepository.findByPrdUserIdOrderByAdApptDateFkAdApptSlotFk(Mockito.anyString())).thenReturn(appointmentList);
		
		assertThat(ptAppointmentDTO).isEqualToComparingFieldByField(appointmentServiceImpl.getAppointmentListByPatientID());
	}

	@Test(expected = ConsultationServiceException.class)
	public void getAppointmentListByPatientIDNegativeTest() throws Exception {
		PtAppointmentDTO ptAppointmentDTO = new PtAppointmentDTO();
		ptAppointmentDTO.setPtRegID("pt2");
		ptAppointmentDTO.setApptDtls(appointmentDtls);
		List<AppointmentDtlsEntity> appointmentList = null; //condition for negative testing.
		Mockito.when(appointmentDtlsRepository.findByPrdUserIdOrderByAdApptDateFkAdApptSlotFk(Mockito.anyString())).thenReturn(appointmentList);
		assertThat(ptAppointmentDTO).isEqualToComparingFieldByField(appointmentServiceImpl.getAppointmentListByPatientID());
	}
	
	@Test
	public void getConsultationListByDoctorIDTest() throws Exception {
		String appointmentDate = "2021-02-25";
		DrAppointmentDTO response = new DrAppointmentDTO();
		List<AppointmentDTO> todayAppointmentList = new ArrayList<AppointmentDTO>();
		List<AppointmentDTO> tomorrowAppointmentList = new ArrayList<AppointmentDTO>();
		Map<String, List<AppointmentDTO>> apptDtls = new LinkedHashMap<String, List<AppointmentDTO>>();
		todayAppointmentList = appointmentDtls1;
		apptDtls.put(ConsultationConstants.TODAY.getValue(), todayAppointmentList);
		apptDtls.put(ConsultationConstants.TOMORROW.getValue(), tomorrowAppointmentList);
		response.setDrRegID("MONIKADOC1");
//		response.setApptDtls(apptDtls);
		
		List<AppointmentDtlsEntity> appointmentList = new ArrayList<AppointmentDtlsEntity>();
		AppointmentDtlsEntity appointment = new AppointmentDtlsEntity();
		appointment.setAdApptDateFk(LocalDate.parse("2020-11-17", sdfo));
		appointment.setAdApptSlotFk("11.30");
		appointment.setAdApptId("12635127");
		appointment.setAdApptStatus("Scheduled");
		appointment.setAdApptBookedFor("TESTPATIENT");
		PatientRegDtlsEntity patientRegDtls = new PatientRegDtlsEntity();
		patientRegDtls.setPrdUserId("pt2");
		patientRegDtls.setPrdName("TESTPATIENT");
		appointment.setPatientRegDtlsEntity(patientRegDtls);
		DocMstrDtlsEntity docMstrDtlsEntity = new DocMstrDtlsEntity();
		docMstrDtlsEntity.setDmdDrName("AMOL");
		docMstrDtlsEntity.setProfilePhoto("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/4RCiRXhpZgAATU0AKgAAAAgABAE7AAIAAAAQAAAISodpAAQAAAABAAAI");
		docMstrDtlsEntity.setDmdUserId("MONIKADOC1");
		docMstrDtlsEntity.setDmdSpecialiazation("MD");
		appointment.setDocMstrDtlsEntity(docMstrDtlsEntity);
		appointmentList.add(appointment);
		
		Mockito.when(appointmentDtlsRepository.findByAdApptDateFkAndDmdUserIdAndOrderByAdApptDateFkAdApptSlotFk(LocalDate.parse(appointmentDate, sdfo),userDTO.getUserName())).thenReturn(appointmentList);
		assertThat(response).isEqualToIgnoringNullFields(appointmentServiceImpl.getAppointmentListByDrID(appointmentDate));
	}
	
	@Test(expected = ConsultationServiceException.class)
	public void getConsultationListByDoctorIDNegativeTest() throws Exception {
		String appointmentDate = "2021-02-25";
		DrAppointmentDTO response = new DrAppointmentDTO();
		List<AppointmentDTO> todayAppointmentList = new ArrayList<AppointmentDTO>();
		List<AppointmentDTO> tomorrowAppointmentList = new ArrayList<AppointmentDTO>();
		Map<String, List<AppointmentDTO>> apptDtls = new LinkedHashMap<String, List<AppointmentDTO>>();
		todayAppointmentList = appointmentDtls1;
		apptDtls.put(ConsultationConstants.TODAY.getValue(), todayAppointmentList);
		apptDtls.put(ConsultationConstants.TOMORROW.getValue(), tomorrowAppointmentList);
		response.setDrRegID("MONIKADOC1");
//		response.setApptDtls(apptDtls);
		List<AppointmentDtlsEntity> appointmentList = new ArrayList<AppointmentDtlsEntity>(); 
		Mockito.when(appointmentDtlsRepository.findByAdApptDateFkAndDmdUserIdAndOrderByAdApptDateFkAdApptSlotFk(LocalDate.parse(appointmentDate, sdfo),userDTO.getUserName())).thenReturn(appointmentList);
		assertThat(response).isEqualToIgnoringNullFields(appointmentServiceImpl.getAppointmentListByDrID("date")); //condition for negative testing.
	}
}
