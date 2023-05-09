/**
 * 
 */
package com.nsdl.telemedicine.review.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.nsdl.telemedicine.review.constant.PatientReviewConstants;
import com.nsdl.telemedicine.review.dto.MainRequestDTO;
import com.nsdl.telemedicine.review.dto.MainResponseDTO;
import com.nsdl.telemedicine.review.dto.PatientRevDtlsDTO;
import com.nsdl.telemedicine.review.dto.UserDTO;
import com.nsdl.telemedicine.review.entity.PatientRevDtlsEntity;
import com.nsdl.telemedicine.review.entity.PatientRevDtlsEntityAudited;
import com.nsdl.telemedicine.review.exception.PatientReviewServiceException;
import com.nsdl.telemedicine.review.repository.PatientReviewAuditedRepository;
import com.nsdl.telemedicine.review.repository.PatientReviewRepository;

/**
 * @author Pegasus_girishk
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ PatientReviewServiceImpl.class })
public class PatientReviewServiceImplTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@InjectMocks
	private PatientReviewServiceImpl patientReviewServiceImpl;
	
	@Mock
	private PatientReviewRepository patientReviewRepository;
	
	@Mock
	private PatientReviewAuditedRepository patientReviewAuditedRepository;
	
	UserDTO userDto = new UserDTO();

	@Before
	public void setUp() throws ParseException {
		userDto.setUserName("PATIENT123");
		ReflectionTestUtils.setField(patientReviewServiceImpl, "userDto",userDto);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void savePatientReviewDtlsTest() throws Exception {
		MainRequestDTO<PatientRevDtlsDTO> reviewRequest = new MainRequestDTO<PatientRevDtlsDTO>();
		PatientRevDtlsDTO reviewDetails = new PatientRevDtlsDTO();
		reviewDetails.setPrdDrUserIdFk("GIRISHDOC");
		reviewDetails.setPrdRating(4L);
		reviewDetails.setPrdReview("Good");
		reviewRequest.setRequest(reviewDetails);

		MainResponseDTO mainResponseDto = new MainResponseDTO();
		mainResponseDto.setResponse(PatientReviewConstants.PATIENT_REVIEW_INSERTED.getValue());
		mainResponseDto.setStatus(true);
		
		PatientRevDtlsEntity existedReviewRequestEntity = new PatientRevDtlsEntity();
		PatientRevDtlsEntity persistedPatientRevDtlsEntity = new PatientRevDtlsEntity();
		PatientRevDtlsEntityAudited reviewRequestEntityAudited = new PatientRevDtlsEntityAudited();

		Mockito.when(patientReviewRepository.findPatientReviews(reviewRequest.getRequest().getPrdDrUserIdFk().toLowerCase(),userDto.getUserName().toLowerCase())).thenReturn(existedReviewRequestEntity);
		Mockito.when(patientReviewRepository.save(Mockito.any(PatientRevDtlsEntity.class))).thenReturn(persistedPatientRevDtlsEntity);
		Mockito.when(patientReviewAuditedRepository.save(Mockito.any(PatientRevDtlsEntityAudited.class))).thenReturn(reviewRequestEntityAudited);
		assertThat(mainResponseDto.getResponse()).isEqualToComparingFieldByField(patientReviewServiceImpl.savePatientReviewDtls(reviewRequest).getResponse());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test(expected = PatientReviewServiceException.class)
	public void savePatientReviewDtlsNegativeTest() throws Exception {
		MainRequestDTO<PatientRevDtlsDTO> reviewRequest = new MainRequestDTO<PatientRevDtlsDTO>();
		PatientRevDtlsDTO reviewDetails = null;
		reviewRequest.setRequest(reviewDetails);

		MainResponseDTO mainResponseDto = new MainResponseDTO();
		mainResponseDto.setResponse(PatientReviewConstants.PATIENT_REVIEW_INSERTED.getValue());
		mainResponseDto.setStatus(true);
		
		PatientRevDtlsEntity existedReviewRequestEntity = new PatientRevDtlsEntity();
		PatientRevDtlsEntity persistedPatientRevDtlsEntity = new PatientRevDtlsEntity();
		PatientRevDtlsEntityAudited reviewRequestEntityAudited = new PatientRevDtlsEntityAudited();

		Mockito.when(patientReviewRepository.findPatientReviews(Mockito.anyString().toLowerCase(),Mockito.anyString().toLowerCase())).thenReturn(existedReviewRequestEntity);
		Mockito.when(patientReviewRepository.save(Mockito.any(PatientRevDtlsEntity.class))).thenReturn(persistedPatientRevDtlsEntity);
		Mockito.when(patientReviewAuditedRepository.save(Mockito.any(PatientRevDtlsEntityAudited.class))).thenReturn(reviewRequestEntityAudited);
		assertThat(mainResponseDto.getResponse()).isEqualToComparingFieldByField(patientReviewServiceImpl.savePatientReviewDtls(reviewRequest).getResponse());
	}
	
	@Test
	public void getNumberOfLikesToDoctorTest() throws Exception {
		Long likes = 15000L;
		Mockito.when(patientReviewRepository.getNumberOfLikesToDoctor(Mockito.anyString())).thenReturn(likes);
		assertThat(likes).isEqualTo(patientReviewServiceImpl.getNumberOfLikesToDoctor(Mockito.anyString()));
	}
	
	@Test
	public void getNumberOfCommentsToDoctorTest() throws Exception {
		Long comments = 15000L;
		Mockito.when(patientReviewRepository.getNumberOfCommentsToDoctor(Mockito.anyString())).thenReturn(comments);
		assertThat(comments).isEqualTo(patientReviewServiceImpl.getNumberOfCommentsToDoctor(Mockito.anyString()));
	}
}
