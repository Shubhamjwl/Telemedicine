package com.nsdl.telemedicine.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nsdl.telemedicine.constant.MarketPlaceConstant;
import com.nsdl.telemedicine.dto.CategoryDto;
import com.nsdl.telemedicine.dto.CategoryFlagRequest;
import com.nsdl.telemedicine.dto.CategoryFlagResponse;
import com.nsdl.telemedicine.entity.FeatureCategoryMapDtlsEntity;
import com.nsdl.telemedicine.repository.FeatureCategoryMapDtlsRepo;
import com.nsdl.telemedicine.service.FeatureCategoryMapService;

import ch.qos.logback.classic.Logger;

@Service
public class FeatureCategoryMapServiceImpl implements FeatureCategoryMapService {

	@Autowired
	private FeatureCategoryMapDtlsRepo featureFlagDtlsRepo;
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(FeatureCategoryMapServiceImpl.class);


	@Override
	public CategoryFlagResponse getCategoryFlagByEmailId(CategoryFlagRequest categoryFlagRequest) {
		CategoryFlagResponse featureFlagResponse = new CategoryFlagResponse();
		List<CategoryDto> categoryDtoList = new ArrayList<CategoryDto>();
		List<FeatureCategoryMapDtlsEntity> categoryEntityList = featureFlagDtlsRepo
				.findByDrEmailId(categoryFlagRequest.getDrEmailId());
		
			for (String categoryType : MarketPlaceConstant.CATEGORY_NAME) {
				Optional<FeatureCategoryMapDtlsEntity> entity = categoryEntityList.stream()
						.filter(e -> e.getDfmdCategoryName().equalsIgnoreCase(categoryType)).findFirst();
				CategoryDto categoryDto = new CategoryDto();
				if (entity.isPresent()) {
					categoryDto.setCategoryName(entity.get().getDfmdCategoryName());
					categoryDto.setCategoryFlag(entity.get().getDfmdFlag());
				} else {
					categoryDto.setCategoryName(categoryType);
					categoryDto.setCategoryFlag(false);
				}
				categoryDtoList.add(categoryDto);
			}			
		featureFlagResponse.setDrEmailId(categoryFlagRequest.getDrEmailId());
		featureFlagResponse.setCategoryDtls(categoryDtoList);
		logger.info("Mapped category flag to response");
		return featureFlagResponse;
	}

}
