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
public class MainMenu {
	private String mainMenuName;
	private String route;
	private List<Functions> functions;
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mainMenuName == null) ? 0 : mainMenuName.hashCode());
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
		MainMenu other = (MainMenu) obj;
		if (mainMenuName == null) {
			if (other.mainMenuName != null)
				return false;
		} else if (!mainMenuName.equals(other.mainMenuName))
			return false;
		return true;
	}

}
