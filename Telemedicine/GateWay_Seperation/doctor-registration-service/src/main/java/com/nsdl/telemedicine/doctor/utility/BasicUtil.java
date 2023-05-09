package com.nsdl.telemedicine.doctor.utility;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.web.multipart.MultipartFile;

/*@author: SayaliA
 * 
 */
public class BasicUtil {
	
	public static void saveDoctorRegistrationFile(String tempFilePath, MultipartFile file) {
		
		String fileName = file.getName();
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				File folderCreation = new File(tempFilePath);
				if (!folderCreation.exists()) {
					folderCreation.mkdir();
				}
				BufferedOutputStream buffStream = new BufferedOutputStream(
						new FileOutputStream(new File(tempFilePath + File.separator + fileName)));
				buffStream.write(bytes);
				buffStream.close();

				/*BufferedOutputStream buffStreamNew = new BufferedOutputStream(
						new FileOutputStream(new File(tempFilePath + File.separator + fileName + ".csv")));
				buffStreamNew.write(bytes);
				buffStreamNew.close();*/
			} catch (Exception e) {
				//logger.info("Exception occured while saving file ");
				//logger.error(e.getMessage());
			}
		} else {
		}
	}

	
	/**
	 * this Method use for get Server Path of Respective OS
	 * 
	 * @return
	 * @throws IOException
	 */
	/*public static String getFilePathBasedOnOS() throws IOException {
		final String os = System.getProperty("os.name").toLowerCase();
		return ((os.contains("windows")) ? LoadPropertyValues.WindowsDocumentPath
				: LoadPropertyValues.LinuxDocumentPath);
		//return "D:\\Telemedicine\\Files";
		return "/tmp";
	}*/
}
