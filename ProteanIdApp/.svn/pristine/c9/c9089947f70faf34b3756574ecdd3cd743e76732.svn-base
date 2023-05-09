package com.nsdl.authenticate.util.face;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;

public class FaceDecoder {
	private static final Logger LOGGER = LoggerFactory.getLogger(FaceDecoder.class);	
	
	private static FaceBDIR getFaceBDIRISO19794_5_2011(byte [] isoData) throws Exception
	{
		try(ByteArrayInputStream bais = new ByteArrayInputStream(isoData);
			DataInputStream inputStream = new DataInputStream(bais);) {
			FaceBDIR faceBDIR = new FaceBDIR(inputStream);
			return faceBDIR;
		}
	}

	
	public static byte[] convertFaceISO19794_5_2011ToImage(byte [] isoData) throws Exception
	{
		ImageData imageData = getFaceBDIRISO19794_5_2011 (isoData).getRepresentation()
				.getRepresentationData().getImageData();
		try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ImageIO.write(ImageIO.read(new ByteArrayInputStream(imageData.getImage())), "jpg", baos);
			return baos.toByteArray();
		} catch (IOException e) {
			LOGGER.error("Failed to get jpg image", e);
		}
		return imageData.getImage();
	}

	
}
