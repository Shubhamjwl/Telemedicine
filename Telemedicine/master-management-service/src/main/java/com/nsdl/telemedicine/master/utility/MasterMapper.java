package com.nsdl.telemedicine.master.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nsdl.telemedicine.master.dto.MasterResponseDto;

public class MasterMapper implements RowMapper<MasterResponseDto> {

	@Override
	public MasterResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		MasterResponseDto dto=new MasterResponseDto();
		dto.setMasterName(rs.getString(1));
		dto.setMasterUnit(rs.getString(2));
		dto.setMasterValue(rs.getString(3));
		return dto;
	}

}
