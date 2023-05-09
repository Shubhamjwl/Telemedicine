package com.nsdl.telemedicine.patient.utility;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nsdl.telemedicine.patient.constant.AuthConstant;
import com.nsdl.telemedicine.patient.constant.AuthErrorConstant;
import com.nsdl.telemedicine.patient.dto.ExceptionJSONInfoDTO;
import com.nsdl.telemedicine.patient.dto.PatientResponseDto;
import com.nsdl.telemedicine.patient.dto.RequestWrapper;
import com.nsdl.telemedicine.patient.dto.ResponseWrapper;
import com.nsdl.telemedicine.patient.exception.DateParsingException;
import com.nsdl.telemedicine.patient.loggers.PatientLoggingClientInfo;

@Component
@PatientLoggingClientInfo
public class AuthUtil {

	private static final Logger LOGGER = LogManager.getLogger(AuthUtil.class);

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	@Value("${PATIENT_PHOTO_PATH}")
	private String byteDataPatientPath;

	@Value(value = "${PROFILE_PHOTO_SIZE}")
	private String profilephotosize;

	public ResponseEntity<?> postApiRequest(String url, HttpMethod httpMethodType, MediaType mediaType, Object body,
			Class<?> responseClass) {
		RestTemplate restTemplate = restTemplateBuilder.build();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);
		HttpEntity<?> request = new HttpEntity<>(body, headers);
		return restTemplate.exchange(url, httpMethodType, request, responseClass);
	}


	public static ResponseWrapper<?> getMainResponseDto(RequestWrapper<?> mainRequestDto ){
		ResponseWrapper<?> response=new ResponseWrapper<>();
		if(mainRequestDto.getRequest()==null) {
			return response;
		}
		response.setId(mainRequestDto.getId());
		response.setVersion(mainRequestDto.getVersion());
		return response;
	}

	public static List<ExceptionJSONInfoDTO> getExceptionList(List<ExceptionJSONInfoDTO> list,String errorCode,String errorMsg){
		if(list==null)
			list=new ArrayList<ExceptionJSONInfoDTO>();
		ExceptionJSONInfoDTO error=new ExceptionJSONInfoDTO(errorCode,errorMsg);
		error.setErrorCode(errorCode);
		error.setMessage(errorMsg);
		list.add(error);
		return list;
	}

	public PatientResponseDto savePatientProfilePhoto(String profilePhoto , Long mobNo , String userID) {
		// TODO Auto-generated method stub
		PatientResponseDto response = new PatientResponseDto();
		String path = null;
		try{
			File byteStorePath = null;
			byteStorePath = new File(byteDataPatientPath);
			String temppath;
			LOGGER.info("Loading the patient profile photo to file system");
			if(null != profilePhoto && !profilePhoto.isEmpty())
			{
				if(profilePhoto.getBytes().length<Long.parseLong(profilephotosize))
				{
					String[] strings = profilePhoto.split(",");
					String extension = "jpeg"; 
					byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
					createDirectory(AuthConstant.patientProfileDirectory);
					temppath = byteStorePath + File.separator + AuthConstant.patientProfileDirectory;
					path = temppath + File.separator + mobNo+"_"+userID+"."+ extension;
					response.setMessage(path);
					File file = new File(path);
					try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
						outputStream.write(data);
					} catch (IOException e) {
						e.printStackTrace();
					}
					LOGGER.info("loaded Patient profile photo successfully to file system");
				}
				else
				{
					LOGGER.error("Size limit exceed, Please selct profile photo up to 1 MB");
					throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.SIZE_LIMIT, AuthConstant.SIZE_LIMIT));
				}
			}
		}catch(Exception e)
		{
			LOGGER.error("Exception while uploading patients profile photo.");
			e.printStackTrace();
		}
		return response;
	}

	public  String getFilePathBasedOnOS(){
		return byteDataPatientPath;
	}

	public void createDirectory(String folder_name) {

		String directoryFilePath = getFilePathBasedOnOS() + "/" + folder_name;
		File file = new File(directoryFilePath);
		if (!file.exists()) {
			try {
				file.mkdir();
			} catch (Exception e) {
			}
		} else {
		}
	}

}
