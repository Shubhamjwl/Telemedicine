package com.nsdl.authenticate.util.face;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class RepresentationData extends AbstractImageInfo
{

	private ImageData imageData;

    public RepresentationData (ImageData imageData)
    {
    	setImageData (imageData);
    }

    public RepresentationData (DataInputStream inputStream) throws IOException
	{
    	readObject(inputStream);
	}
    
    protected void readObject(DataInputStream inputStream) throws IOException {    	
    	setImageData (new ImageData (inputStream));
    }

    public int getRecordLength ()
    {
        return getImageData().getRecordLength ();
    }

    public void writeObject (DataOutputStream outputStream) throws IOException
    {
    	getImageData().writeObject (outputStream);
        outputStream.flush ();
    }

	public ImageData getImageData() {
		return imageData;
	}

	public void setImageData(ImageData imageData) {
		this.imageData = imageData;
	}

	@Override
	public String toString() {
		return "\nRepresentationData [RepresentationDataRecordLength=" + getRecordLength () + ", imageData=" + imageData + "]\n";
	}	
}
