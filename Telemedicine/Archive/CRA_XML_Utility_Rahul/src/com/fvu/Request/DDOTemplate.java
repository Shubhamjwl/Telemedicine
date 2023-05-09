package com.fvu.Request;

import java.math.BigDecimal;

public class DDOTemplate {
private String lineNo;
private String record_type;
private String batchNo;
private String ddoRegNo;
private BigDecimal totalGovtCont;
private BigDecimal totalSubSCont;
private Integer totalSubscriber;
public String getLineNo() {
	return lineNo;
}
public void setLineNo(String lineNo) {
	this.lineNo = lineNo;
}
public String getRecord_type() {
	return record_type;
}
public void setRecord_type(String record_type) {
	this.record_type = record_type;
}
public String getBatchNo() {
	return batchNo;
}
public void setBatchNo(String batchNo) {
	this.batchNo = batchNo;
}
public String getDdoRegNo() {
	return ddoRegNo;
}
public void setDdoRegNo(String ddoRegNo) {
	this.ddoRegNo = ddoRegNo;
}
public BigDecimal getTotalGovtCont() {
	return totalGovtCont;
}
public void setTotalGovtCont(BigDecimal totalGovtCont) {
	this.totalGovtCont = totalGovtCont;
}
public BigDecimal getTotalSubSCont() {
	return totalSubSCont;
}
public void setTotalSubSCont(BigDecimal totalSubSCont) {
	this.totalSubSCont = totalSubSCont;
}
@Override
public String toString() {
	return "DDOTemplate [lineNo=" + lineNo + ", record_type=" + record_type + ", batchNo=" + batchNo + ", ddoRegNo="
			+ ddoRegNo + ", totalGovtCont=" + totalGovtCont + ", totalSubSCont=" + totalSubSCont + ", totalSubscriber="
			+ totalSubscriber + "]";
}
public Integer getTotalSubscriber() {
	return totalSubscriber;
}
public void setTotalSubscriber(Integer totalSubscriber) {
	this.totalSubscriber = totalSubscriber;
}

}
