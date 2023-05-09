package com.nsdl.appointment.service;

import java.util.List;

import com.nsdl.appointment.dto.ApptDtlsDto;
import com.nsdl.appointment.dto.AssignScribeToApptRequest;
import com.nsdl.appointment.dto.AssignedScribeStatus;
import com.nsdl.appointment.dto.AssignedScribeToApptResponse;
import com.nsdl.appointment.dto.CancelAppointmentRequest;
import com.nsdl.appointment.dto.CancelAppointmentResponse;
import com.nsdl.appointment.dto.DoctorDTO;
import com.nsdl.appointment.dto.DoctorSearchRequest;
import com.nsdl.appointment.dto.DoctorSlotRequest;
import com.nsdl.appointment.dto.MainRequestDTO;
import com.nsdl.appointment.dto.PatientDetailsDTO;
import com.nsdl.appointment.dto.RescheduleApptDTO;
import com.nsdl.appointment.dto.SaveAppDetailsRequest;
import com.nsdl.appointment.dto.SaveAppDetailsResponse;
import com.nsdl.appointment.dto.ScribeAppptDetailsDTO;
import com.nsdl.appointment.dto.SlotDetails;
import com.nsdl.appointment.dto.StatusResponse;

public interface AppointmentService {

	public List<DoctorDTO> getListOfDoctorBySearch(DoctorSearchRequest doctorSearchRequest);

	public List<SlotDetails> getAvailableSlotListByDoctor(DoctorSlotRequest doctorSlotRequest);

	public PatientDetailsDTO getPatientDetails(String patientId);

	public SaveAppDetailsResponse saveAppointmentDetails(SaveAppDetailsRequest appointmentRequest) throws Exception;

	public CancelAppointmentResponse cancelAppointment(CancelAppointmentRequest cancelAppointmentRequest);

	public ApptDtlsDto getAppointmentDetails(String apptId);

	public SaveAppDetailsResponse saveReschdulesAppDetails(RescheduleApptDTO request);

	public StatusResponse assignScribeToApptByDoctor(AssignScribeToApptRequest request);

	public AssignedScribeToApptResponse assignedApptListToScribe(String scribeId);

	public AssignedScribeStatus isScribeAssignedToAppt(MainRequestDTO<ScribeAppptDetailsDTO> request);

	public List<ApptDtlsDto> getPatientCompleteAppointmentDetails(String ptUserId);
	
	public void sendNotificationBeforeAppointmentStart();

}
