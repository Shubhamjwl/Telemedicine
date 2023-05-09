package com.nsdl.ndhm.controller;

import com.nsdl.ndhm.dto.datareport.ReportRequestDto;
import com.nsdl.ndhm.dto.datareport.RequestWrapper;
import com.nsdl.ndhm.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ReportController {
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    ReportService reportService;

    @PostMapping(path = "/get-report-data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, List<String>>> getReportAndData(@RequestBody RequestWrapper<ReportRequestDto> reportRequestDTO) throws IOException {
        logger.info("GetReportandData For telemedicine");
        return ResponseEntity.status(HttpStatus.OK).body(reportService.getReportData(reportRequestDTO));
    }

/*    @PostMapping(path = "/get-report-data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportResponseDto> getReportandData(@RequestBody RequestWrapper<ReportRequestDto> reportRequestDTO) {
        logger.info("GetReportandData For telemedicine");
        return ResponseEntity.status(HttpStatus.OK).body(reportService.getDataReportResponseDto());
    }*/
}
