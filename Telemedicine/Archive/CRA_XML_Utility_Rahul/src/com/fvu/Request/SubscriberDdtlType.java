


package com.fvu.Request;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fvu.utility.AppUtility;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subscriberDdtlType", propOrder = {
		"lineNo",
		"batchNo",
		"ddoSerialNo",
		"serialNo",
    "recordType",
    "ddoRegNo",
    "pran",
    "govtContrAmt",
    "subscriberContrAmt",
    "totalContrSubscriber",
    "contrType",
    "contrMonth",
    "contrYear",
    "remarks","filler"
})
public class SubscriberDdtlType implements Comparable<SubscriberDdtlType>{

	@XmlElement(name = "line-no", required = true)
	 private String lineNo;
	 @XmlElement(name = "batch-no", required = true)
	 private String batchNo;
	 @XmlElement(name = "ddo-serial-no", required = true)
	  private String ddoSerialNo;
	 @XmlElement(name = "serial-no", required = true)
	  private String serialNo;
	 
    @XmlElement(name = "record-type", required = true)
    private String recordType;
    @XmlElement(name = "ddo-reg-no", required = true)
    private String ddoRegNo;
    @XmlElement(required = true)
    private String pran;
    @XmlElement(name = "govt-contr-amt")
    private BigDecimal govtContrAmt;
    @XmlElement(name = "subscriber-contr-amt")
    private BigDecimal subscriberContrAmt;
    @XmlElement(name = "total-contr-subscriber")
    private BigDecimal totalContrSubscriber;
    @XmlElement(name = "contr-type", required = true)
    private String contrType;
    @XmlElement(name = "contr-month")
    private String contrMonth;
    @XmlElement(name = "contr-year")
    private String contrYear;
    @XmlElement(required = true)
    private String remarks;
    private String filler;
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getDdoSerialNo() {
		return ddoSerialNo;
	}
	public void setDdoSerialNo(String ddoSerialNo) {
		this.ddoSerialNo = ddoSerialNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getFiller() {
		return filler;
	}
	public void setFiller(String filler) {
		this.filler = filler;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getDdoRegNo() {
		return ddoRegNo;
	}
	public void setDdoRegNo(String ddoRegNo) {
		this.ddoRegNo = ddoRegNo;
	}
	public String getPran() {
		return pran;
	}
	public void setPran(String pran) {
		this.pran = pran;
	}
	public BigDecimal getGovtContrAmt() {
		return govtContrAmt;
	}
	public void setGovtContrAmt(BigDecimal govtContrAmt) {
		this.govtContrAmt = govtContrAmt;
	}
	public BigDecimal getSubscriberContrAmt() {
		return subscriberContrAmt;
	}
	public void setSubscriberContrAmt(BigDecimal subscriberContrAmt) {
		this.subscriberContrAmt = subscriberContrAmt;
	}
	public BigDecimal getTotalContrSubscriber() {
		return totalContrSubscriber;
	}
	public void setTotalContrSubscriber(BigDecimal totalContrSubscriber) {
		this.totalContrSubscriber = totalContrSubscriber;
	}
	public String getContrType() {
		return contrType;
	}
	public void setContrType(String contrType) {
		this.contrType = contrType;
	}
	public String getContrMonth() {
		return contrMonth;
	}
	public void setContrMonth(String contrMonth) {
		this.contrMonth = contrMonth;
	}
	public String getContrYear() {
		return contrYear;
	}
	public void setContrYear(String contrYear) {
		this.contrYear = contrYear;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ddoRegNo == null) ? 0 : ddoRegNo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubscriberDdtlType other = (SubscriberDdtlType) obj;
		if (ddoRegNo == null) {
			if (other.ddoRegNo != null)
				return false;
		} else if (!ddoRegNo.equals(other.ddoRegNo))
			return false;
		return true;
	}

	@Override
	public int compareTo(SubscriberDdtlType obj) {
		 //return this.getDdoRegNo().compareTo( obj.getDdoRegNo());
		 return obj.getDdoRegNo().compareTo(this.getDdoRegNo());
	}

	public String toString() {
		return  recordType + "^"+AppUtility.xNull(batchNo)+"^" +AppUtility.xNull(ddoSerialNo)+"^"+AppUtility.xNull(serialNo)+"^"
				+AppUtility.xNull(pran) + "^" + govtContrAmt.setScale(2,RoundingMode.HALF_DOWN) + "^" + subscriberContrAmt.setScale(2,RoundingMode.HALF_DOWN)
				+ "^" + AppUtility.xNull(filler)+totalContrSubscriber.setScale(2,RoundingMode.HALF_DOWN) + "^" + AppUtility.xNull(contrType) + "^"
				+ AppUtility.xNull(contrMonth) + "^" + AppUtility.xNull(contrYear) + "^" + AppUtility.xNull(remarks)+"^";
	}
	

}
