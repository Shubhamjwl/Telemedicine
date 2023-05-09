/**
 * 
 */
package com.nsdl.telemedicine.review.dto;

import java.util.Date;

import lombok.Data;

/**
 * @author Pegasus_girishk
 *
 */

@Data
public class PatientRevDtlsDTO {

//	private Long prdIdPk;
	
	private String prdDrUserIdFk;

	private String prdPtUserIdFk;

	private String prdReview;

	private Date prdReviewDate;

	private Long prdRating;

	private String prdCreatedBy;

}
