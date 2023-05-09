package com.nsdl.auth.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubFunctions {
	private String subFunctionName;
	private String subFunctionRoute;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subFunctionName == null) ? 0 : subFunctionName.hashCode());
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
		SubFunctions other = (SubFunctions) obj;
		if (subFunctionName == null) {
			if (other.subFunctionName != null)
				return false;
		} else if (!subFunctionName.equals(other.subFunctionName))
			return false;
		return true;
	}
	
	
	
}
