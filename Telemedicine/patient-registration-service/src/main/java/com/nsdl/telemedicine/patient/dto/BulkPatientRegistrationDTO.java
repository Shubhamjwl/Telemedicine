/**
 * 
 */
package com.nsdl.telemedicine.patient.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pegasus_girishk
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkPatientRegistrationDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String doctorUserID;
	
	private String excelFileOfBulkPatientDtls;
	
	private String fileName;
	
	private String ptEmailID;
}
