package com.nsdl.authenticate.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BiometricRecord {

	private VersionType version;
	private VersionType cbeffversion;
	private BIRInfo birInfo;
	private List<BIR> segments;
	
	public BiometricRecord() {
		this.segments = new ArrayList<BIR>();
	}
	
	public BiometricRecord(VersionType version, VersionType cbeffversion, BIRInfo birInfo) {
		this.version = version;
		this.cbeffversion = cbeffversion;
		this.birInfo = birInfo;
		this.segments = new ArrayList<BIR>();
	}	

}
