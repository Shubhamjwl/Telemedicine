package com.nsdl.telemedicine.service;

import com.nsdl.telemedicine.dto.CategoryFlagRequest;
import com.nsdl.telemedicine.dto.CategoryFlagResponse;

public interface FeatureCategoryMapService {

	 CategoryFlagResponse getCategoryFlagByEmailId(CategoryFlagRequest featureFlagRequest);

}
