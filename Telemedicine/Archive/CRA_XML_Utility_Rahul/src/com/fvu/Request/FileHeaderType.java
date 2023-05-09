

package com.fvu.Request;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fvu.utility.AppUtility;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fileHeaderType", propOrder = {
    "lineNo",
    "recordType",
    "uploadedBy",
    "paoRegNo",
    "noOfBatches",
    "salDisbDate",
    "fileFlag",
    "month",
    "year","recordHash",
    "version","fileHash","filler1","filler2","filler3","filler4"
})
public class FileHeaderType {

   
	@XmlElement(name = "line-no", required = true)
    private String lineNo;
    @XmlElement(name = "record-type", required = true)
    private String recordType;
    @XmlElement(name = "uploaded-by", required = true)
    private String uploadedBy;
    @XmlElement(name = "pao-reg-no", required = true)
    private String paoRegNo;
    @XmlElement(name = "no-of-batches")
    private Integer noOfBatches;
    @XmlElement(name = "sal-disb-date", required = true)
    private String salDisbDate;
    @XmlElement(name = "file-flag", required = true)
    private String fileFlag;
    @XmlElement(name="month")
    private String month;
    @XmlElement(name="year")
    private String year;
    private String recordHash;
    public String getRecordHash() {
		return recordHash;
	}

	public void setRecordHash(String recordHash) {
		this.recordHash = recordHash;
	}
	@XmlElement(required = true)
    private double version;
    private String fileHash;
    private String filler1;
    private Integer filler2;
    private String filler3;
    private Integer filler4;
    public String getFileHash() {
		return fileHash;
	}

	public void setFileHash(String fileHash) {
		this.fileHash = fileHash;
	}

	public String getFiller1() {
		return filler1;
	}

	public void setFiller1(String filler1) {
		this.filler1 = filler1;
	}

	public Integer getFiller2() {
		return filler2;
	}

	public void setFiller2(Integer filler2) {
		this.filler2 = filler2;
	}

	public String getFiller3() {
		return filler3;
	}

	public void setFiller3(String filler3) {
		this.filler3 = filler3;
	}

	public Integer getFiller4() {
		return filler4;
	}

	public void setFiller4(Integer filler4) {
		this.filler4 = filler4;
	}

	/**
     * Gets the value of the lineNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineNo() {
        return lineNo;
    }

    /**
     * Sets the value of the lineNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineNo(String value) {
        this.lineNo = value;
    }

    /**
     * Gets the value of the recordType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordType() {
        return recordType;
    }

    /**
     * Sets the value of the recordType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordType(String value) {
        this.recordType = value;
    }

    /**
     * Gets the value of the uploadedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUploadedBy() {
        return uploadedBy;
    }

    /**
     * Sets the value of the uploadedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUploadedBy(String value) {
        this.uploadedBy = value;
    }

    /**
     * Gets the value of the paoRegNo property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public String getPaoRegNo() {
        return paoRegNo;
    }

    /**
     * Sets the value of the paoRegNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPaoRegNo(String value) {
        this.paoRegNo = value;
    }

    /**
     * Gets the value of the noOfBatches property.
     * 
     */
    public Integer getNoOfBatches() {
        return noOfBatches;
    }

    /**
     * Sets the value of the noOfBatches property.
     * 
     */
    public void setNoOfBatches(Integer value) {
        this.noOfBatches = value;
    }

    /**
     * Gets the value of the salDisbDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalDisbDate() {
        return salDisbDate;
    }

    /**
     * Sets the value of the salDisbDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalDisbDate(String value) {
        this.salDisbDate = value;
    }

    /**
     * Gets the value of the fileFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileFlag() {
        return fileFlag;
    }

    /**
     * Sets the value of the fileFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileFlag(String value) {
        this.fileFlag = value;
    }

    /**
     * Gets the value of the month property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public String getMonth() {
        return month;
    }

    /**
     * Sets the value of the month property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMonth(String value) {
        this.month = value;
    }

    /**
     * Gets the value of the year property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public String getYear() {
        return year;
    }

    /**
     * Sets the value of the year property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setYear(String value) {
        this.year = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public double getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVersion(double value) {
        this.version = value;
    }
    
   /* @Override
    public String toString() {
 		return  AppUtility.xNull(recordType) + "^"
 				+  AppUtility.xNull(uploadedBy) + "^" + AppUtility.xNull(paoRegNo) + "^" + AppUtility.xNull(String.valueOf(noOfBatches)) + "^"
 				+ AppUtility.xNull(salDisbDate) + "^"+ AppUtility.xNull(fileFlag) + "^" + AppUtility.xNull(month) + "^" + AppUtility.xNull(year) + "^"+version;
 				
 		}*/
    
    @Override
    public String toString() {
 		return  AppUtility.xNull(recordType) + "^"
 				+  AppUtility.xNull(uploadedBy) + "^" + AppUtility.xNull(paoRegNo) + "^" + AppUtility.xNull(String.valueOf(noOfBatches)) + "^"
 				+ AppUtility.nullCheck(salDisbDate) +"^"+  AppUtility.xNull(fileFlag) + "^" +AppUtility.nullCheck(month)+ "^" + AppUtility.nullCheck(year) + "^"+version;
 				
 		}

}
