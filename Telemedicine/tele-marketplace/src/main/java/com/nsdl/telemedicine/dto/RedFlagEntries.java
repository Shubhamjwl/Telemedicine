package com.nsdl.telemedicine.dto;

import java.util.HashMap;

import lombok.Data;

@Data
public class RedFlagEntries {

	private HashMap<Integer, RedFlagData> entries;

}
