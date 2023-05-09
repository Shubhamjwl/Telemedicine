package com.nsdl.telemedicine.consultancy.dto;

import java.util.List;

import lombok.Data;

@Data
public class PrescriptionDtlsDTO {
	private List<ChiefComplaintsDtls> chiefCompDtls;
	private List<DiagnosisResponseDTO> diagnosisDtls;
	private List<InvestigationDetailsDTO> investigationDtls;
	private List<ConsultMedicationDtlsDTO> medicationDtls;
	private List<ConsultAdviceDTO> adviceDtl;

}
