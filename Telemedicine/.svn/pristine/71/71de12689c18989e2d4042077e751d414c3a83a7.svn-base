

package com.fvu.Request;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;





@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "fileHeader",
    "batchHeader",
    "ddoHeader",
     "subscriberDtl"
})
@XmlRootElement(name = "file")
public class RequestFile {

   

	@XmlElement(name = "file-header", required = true)
    protected FileHeaderType fileHeader;
    @XmlElement(name = "batch-header", required = true)
    protected BatchHeaderType batchHeader;
    @XmlElement(name = "ddo-header", nillable=true)
    protected List<DdoHeaderType> ddoHeader;
    @XmlElement(name = "subscriber-dtl", required = true)
    protected List<SubscriberDdtlType> subscriberDtl;

    @Override
   	public String toString() {
   		return "RequestFile [fileHeader=" + fileHeader + ", batchHeader=" + batchHeader + ", ddoHeader=" + ddoHeader
   				+ ", subscriberDtl=" + subscriberDtl + "]";
   	}
    /**
     * Gets the value of the fileHeader property.
     * 
     * @return
     *     possible object is
     *     {@link FileHeaderType }
     *     
     */
    public FileHeaderType getFileHeader() {
        return fileHeader;
    }

    /**
     * Sets the value of the fileHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link FileHeaderType }
     *     
     */
    public void setFileHeader(FileHeaderType value) {
        this.fileHeader = value;
    }

    /**
     * Gets the value of the batchHeader property.
     * 
     * @return
     *     possible object is
     *     {@link BatchHeaderType }
     *     
     */
    public BatchHeaderType getBatchHeader() {
        return batchHeader;
    }

    /**
     * Sets the value of the batchHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link BatchHeaderType }
     *     
     */
    public void setBatchHeader(BatchHeaderType value) {
        this.batchHeader = value;
    }

    /**
     * Gets the value of the ddoHeader property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ddoHeader property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDdoHeader().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DdoHeaderType }
     * 
     * 
     */
    public List<DdoHeaderType> getDdoHeader() {
        if (ddoHeader == null) {
            ddoHeader = new ArrayList<DdoHeaderType>();
        }
        return this.ddoHeader;
    }

	public List<SubscriberDdtlType> getSubscriberDtl() {
		return subscriberDtl;
	}

	public void setSubscriberDtl(List<SubscriberDdtlType> subscriberDtl) {
		this.subscriberDtl = subscriberDtl;
	}

	public void setDdoHeader(List<DdoHeaderType> ddoHeader) {
		this.ddoHeader = ddoHeader;
	}

    /**
     * Gets the value of the subscriberDtl property.
     * 
     * @return
     *     possible object is
     *     {@link SubscriberDdtlType }
     *     
     */
    

    
}
