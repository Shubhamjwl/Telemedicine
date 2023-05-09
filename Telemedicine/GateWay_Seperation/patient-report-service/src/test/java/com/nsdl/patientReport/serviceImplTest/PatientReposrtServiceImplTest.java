package com.nsdl.patientReport.serviceImplTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.context.junit4.SpringRunner;

import com.nsdl.patientReport.dto.AppointmentDTO;
import com.nsdl.patientReport.dto.AppointmentRequestDTO;
import com.nsdl.patientReport.dto.MainRequestDTO;
import com.nsdl.patientReport.dto.MainResponseDTO;
import com.nsdl.patientReport.entity.ConsultPriscpDtl;
import com.nsdl.patientReport.exception.PatientReportException;
import com.nsdl.patientReport.repository.PatientReportRepo;
import com.nsdl.patientReport.repository.PrescriptionRepo;
import com.nsdl.patientReport.serviceImpl.PatientReposrtServiceImpl;

@RunWith(SpringRunner.class)
public class PatientReposrtServiceImplTest{
	
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Mock
	PatientReportRepo patientReportRepo;

	@Mock
	PrescriptionRepo prescriptionRepo;

	@InjectMocks
	private PatientReposrtServiceImpl patientReposrtServiceImpl;
	

	List<AppointmentDTO> appointmentDtls = new ArrayList<>(); 
	MainResponseDTO<List<AppointmentDTO>> response = new MainResponseDTO<List<AppointmentDTO>>();
	ConsultPriscpDtl prescription = new ConsultPriscpDtl();

	@Test
	public void getPatientAppointmentDetailsTest() throws Exception {
		MainRequestDTO<AppointmentRequestDTO> request = new MainRequestDTO<>();
		AppointmentRequestDTO appointmentRequestDTO = new AppointmentRequestDTO();
		appointmentRequestDTO.setDoc_user_id("AMOL");
		appointmentRequestDTO.setPatient_user_id("JEEVAN");
		request.setRequest(appointmentRequestDTO);
		response.setResponse(appointmentDtls);
		Mockito.when(patientReportRepo.getPatientAppointmentDetails(Mockito.anyString()))
		.thenReturn(appointmentDtls);

		assertThat(appointmentDtls).containsAll(patientReposrtServiceImpl.getPatientAppointmentDetails(request).getResponse());

	}

	@Test(expected = PatientReportException.class)
	public void getPatientAppointmentDetailsNegetiveTest()throws Exception {
		MainRequestDTO<AppointmentRequestDTO> request = new MainRequestDTO<>();
		AppointmentRequestDTO appointmentRequestDTO = new AppointmentRequestDTO();
		appointmentRequestDTO.setDoc_user_id("AMOL");
		appointmentRequestDTO.setPatient_user_id("");
		request.setRequest(appointmentRequestDTO);

		Mockito.when(patientReportRepo.getPatientAppointmentDetails(Mockito.anyString())).thenReturn(appointmentDtls);
		patientReposrtServiceImpl.getPatientAppointmentDetails(request);
	}
	
	@Test
	public void getDoctorAppointmentDetailsTest() throws Exception {
		MainRequestDTO<AppointmentRequestDTO> request = new MainRequestDTO<>();
		AppointmentRequestDTO appointmentRequestDTO = new AppointmentRequestDTO();
		appointmentRequestDTO.setDoc_user_id("AMOL");
		appointmentRequestDTO.setPatient_user_id("JEEVAN");
		request.setRequest(appointmentRequestDTO);
		response.setResponse(appointmentDtls);
		Mockito.when(patientReportRepo.getDoctorAppointmentDetails(Mockito.anyString()))
		.thenReturn(appointmentDtls);

		assertThat(appointmentDtls).containsAll(patientReposrtServiceImpl.getDoctorAppointmentDetails(request).getResponse());

	}

	@Test(expected = PatientReportException.class)
	public void getDoctorAppointmentDetailsNegetiveTest()throws Exception {
		MainRequestDTO<AppointmentRequestDTO> request = new MainRequestDTO<>();
		AppointmentRequestDTO appointmentRequestDTO = new AppointmentRequestDTO();
		appointmentRequestDTO.setDoc_user_id("");
		appointmentRequestDTO.setPatient_user_id("JEEVAN");
		request.setRequest(appointmentRequestDTO);

		Mockito.when(patientReportRepo.getDoctorAppointmentDetails(Mockito.anyString())).thenReturn(appointmentDtls);
		patientReposrtServiceImpl.getDoctorAppointmentDetails(request);
	}
	
	@Test
	public void downloadDocumentTest() throws Exception {
		MainRequestDTO<ConsultPriscpDtl> request = new MainRequestDTO<ConsultPriscpDtl>();
		ConsultPriscpDtl consultPriscpDtl = new ConsultPriscpDtl();
		consultPriscpDtl.setCpdApptIdFk("c2f4e41d-c760-41b2-80f1-d2cf4bf62aeb");
		consultPriscpDtl.setCpdPriscpPath("D:\\\\temptodelte\\\\sample.txt");
		consultPriscpDtl.setCpdDrTmpltName("document");
		consultPriscpDtl.setCpdDrUserIdFk("AjitK0123");
		consultPriscpDtl.setCpdOptiVersion("5");
		consultPriscpDtl.setCpdPtUserIdFk("SANDESH");
		consultPriscpDtl.setCpdCreatedBy("SANDESH");
		
		request.setId("Prescription");
		request.setRequest(consultPriscpDtl);
		request.setRequesttime(new Date());
		request.setVersion("1.0");
		
		
		Mockito.when(prescriptionRepo.findByCpdApptIdFk(request.getRequest().getCpdApptIdFk())).thenReturn(prescription);

		assertThat(prescription).isEqualToComparingFieldByField(patientReposrtServiceImpl.downloadPrescription(request));

	}

}


