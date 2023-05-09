 


package com.fvu.Request;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fvu.utility.AppUtility;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "batchHeaderType", propOrder = {
    "lineNo",
    "recordType",
    "batchNo",
    "contrFileType",
    "paoRegNo",
    "dateFileCreation",
    "batchId",
    "txnId",
    "totalDdo",
    "noSubscriberRecords",
    "controlTotalGovtContr",
    "controlTotalSubContr",
    "grandTotal"
})
public class BatchHeaderType {

    @XmlElement(name = "line-no", required = true)
    private String lineNo;
    @XmlElement(name = "record-type", required = true)
    private String recordType;
    @XmlElement(name = "batch-no", required = true)
    private String batchNo;
    @XmlElement(name = "contr-file-type", required = true)
    private String contrFileType;
    @XmlElement(name = "pao-reg-no", required = true)
    private String paoRegNo;
    @XmlElement(name = "date-file-creation", required = true)
    private String dateFileCreation;
    @XmlElement(name = "batch-id", required = true)
    private String batchId;
    @XmlElement(name = "txn-id", required = true)
   
    private String txnId;
    @XmlElement(name = "total-ddo", required = true)
    private Integer totalDdo;
    @XmlElement(name = "no-subscriber-records", required = true)
    private Integer noSubscriberRecords;
    @XmlElement(name = "control-total-govt-contr")
    private BigDecimal controlTotalGovtContr;
    @XmlElement(name = "control-total-sub-contr")
    private BigDecimal controlTotalSubContr;
    @XmlElement(name = "grand-total")
    private BigDecimal grandTotal;

        public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	public String getRecordType() {
		return recordType;
	}





	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}





	public String getBatchNo() {
		return batchNo;
	}





	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}





	public String getContrFileType() {
		return contrFileType;
	}





	public void setContrFileType(String contrFileType) {
		this.contrFileType = contrFileType;
	}





	public String getPaoRegNo() {
		return paoRegNo;
	}





	public void setPaoRegNo(String paoRegNo) {
		this.paoRegNo = paoRegNo;
	}





	public String getDateFileCreation() {
		return dateFileCreation;
	}





	public void setDateFileCreation(String dateFileCreation) {
		this.dateFileCreation = dateFileCreation;
	}





	public String getBatchId() {
		return batchId;
	}





	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}





	public String getTxnId() {
		return txnId;
	}





	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}





	public Integer getTotalDdo() {
		return totalDdo;
	}





	public void setTotalDdo(Integer totalDdo) {
		this.totalDdo = totalDdo;
	}





	public Integer getNoSubscriberRecords() {
		return noSubscriberRecords;
	}





	public void setNoSubscriberRecords(Integer noSubscriberRecords) {
		this.noSubscriberRecords = noSubscriberRecords;
	}





	public BigDecimal getControlTotalGovtContr() {
		return controlTotalGovtContr;
	}





	public void setControlTotalGovtContr(BigDecimal controlTotalGovtContr) {
		this.controlTotalGovtContr = controlTotalGovtContr;
	}





	public BigDecimal getControlTotalSubContr() {
		return controlTotalSubContr;
	}





	public void setControlTotalSubContr(BigDecimal controlTotalSubContr) {
		this.controlTotalSubContr = controlTotalSubContr;
	}





	public BigDecimal getGrandTotal() {
		return grandTotal;
	}





	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
	}





		@Override
		public String toString() {
			return AppUtility.xNull(recordType) + "^"
					+ AppUtility.xNull(batchNo) + "^" + AppUtility.xNull(contrFileType) + "^" + AppUtility.xNull(paoRegNo) + "^"
					+ AppUtility.xNull(dateFileCreation) + "^" + AppUtility.xNull(batchId)+ "^" + AppUtility.xNull(txnId) + "^" + AppUtility.xNull(String.valueOf(totalDdo))
					+ "^" + AppUtility.xNull(String.valueOf(noSubscriberRecords)) + "^" + AppUtility.xNull(String.valueOf(controlTotalGovtContr.setScale(2,RoundingMode.HALF_DOWN)))
					+ "^" + AppUtility.xNull(String.valueOf(controlTotalSubContr.setScale(2,RoundingMode.HALF_DOWN))) + "^^" + AppUtility.xNull(String.valueOf(grandTotal.setScale(2,RoundingMode.HALF_DOWN)))+"^";
		}


}
