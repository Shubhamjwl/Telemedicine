

package com.fvu.Request;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fvu.utility.AppUtility;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ddoHeaderType", propOrder = {
    "lineNo",
    "recordType",
    "batchNo",
    "ddoSerialNo",
    "ddoRegNo",
    "totalSubscriber",
    "controlTotalGovtContr",
    "controlTotalSubContr",
   
})
public class DdoHeaderType {

    @XmlElement(name = "line-no", required = true)
    private String lineNo;
    @XmlElement(name = "record-type", required = true)
    private String recordType;
    @XmlElement(name = "batch-no", required = true)
    private String batchNo;
    @XmlElement(name = "ddo-serial-no", required = true)
    private String ddoSerialNo;
    @XmlElement(name = "ddo-reg-no", required = true)
    private String ddoRegNo;
    @XmlElement(name = "total-subscriber")
    private Integer totalSubscriber;
    @XmlElement(name = "control-total-govt-contr")
    private BigDecimal controlTotalGovtContr;
    @XmlElement(name = "control-total-sub-contr")
    private BigDecimal controlTotalSubContr;
  /*  @XmlElement(required = true)
    private String ddoHash; */
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
	public String getDdoSerialNo() {
		return ddoSerialNo;
	}
	public void setDdoSerialNo(String ddoSerialNo) {
		this.ddoSerialNo = ddoSerialNo;
	}
	public String getDdoRegNo() {
		return ddoRegNo;
	}
	public void setDdoRegNo(String ddoRegNo) {
		this.ddoRegNo = ddoRegNo;
	}
	public Integer getTotalSubscriber() {
		return totalSubscriber;
	}
	public void setTotalSubscriber(Integer totalSubscriber) {
		this.totalSubscriber = totalSubscriber;
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


	 @Override
		public String toString() {
			return AppUtility.xNull(recordType)+ "^" + AppUtility.xNull(batchNo)
					+ "^" + AppUtility.xNull(ddoSerialNo) + "^" + AppUtility.xNull(ddoRegNo) + "^" + totalSubscriber
					+ "^" + controlTotalGovtContr.setScale(2,RoundingMode.HALF_DOWN) + "^" + controlTotalSubContr.setScale(2,RoundingMode.HALF_DOWN)+"^^";
		}
}
