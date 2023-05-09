package com.nsdl.ndhm.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.nsdl.ndhm.dto.datareport.RequestWrapper;

public interface ReportService {
    Map<String, List<String>> getReportData(RequestWrapper<?> reportRequestDTO) throws IOException;

}
