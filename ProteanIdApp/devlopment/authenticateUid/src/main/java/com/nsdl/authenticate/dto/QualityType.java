package com.nsdl.authenticate.dto;


public class QualityType {

	protected RegistryIDType algorithm;
	protected Long score;
	protected String qualityCalculationFailed;

	/**
	 * Gets the value of the algorithm property.
	 * 
	 * @return possible object is {@link RegistryIDType }
	 * 
	 */
	public RegistryIDType getAlgorithm() {
		return algorithm;
	}

	/**
	 * Sets the value of the algorithm property.
	 * 
	 * @param value allowed object is {@link RegistryIDType }
	 * 
	 */
	public void setAlgorithm(RegistryIDType value) {
		this.algorithm = value;
	}

	/**
	 * Gets the value of the score property.
	 * 
	 * @return possible object is {@link Long }
	 * 
	 */
	public Long getScore() {
		return score;
	}

	/**
	 * Sets the value of the score property.
	 * 
	 * @param value allowed object is {@link Long }
	 * 
	 */
	public void setScore(Long value) {
		this.score = value;
	}

	/**
	 * Gets the value of the qualityCalculationFailed property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getQualityCalculationFailed() {
		return qualityCalculationFailed;
	}

	/**
	 * Sets the value of the qualityCalculationFailed property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setQualityCalculationFailed(String value) {
		this.qualityCalculationFailed = value;
	}

}
