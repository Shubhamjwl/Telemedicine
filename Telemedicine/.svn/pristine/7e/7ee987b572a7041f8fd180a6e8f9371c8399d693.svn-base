package com.nsdl.telemedicine.review.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CORSFilter implements Filter {
	
	private static final Logger LOGGER  = LoggerFactory.getLogger(CORSFilter.class);
	
    public void destroy() {
    }
    public static String VALID_METHODS = "DELETE, HEAD, GET, OPTIONS, POST, PUT";
    String headerList = "verFlag,accessToken,mdOS,maVer,mosVer,mIMEMI,mdUUID,newUserFlag,logoutTimestamp,dob,sectorCat,loginTimestamp,updateFlag,sotFlag,message,status,statusCode,salt";


	
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) resp;

        // No Origin header present means this is not a cross-domain request
        String origin = httpReq.getHeader("Origin");
        LOGGER.info("ORIGIN :: " + origin);
         if (origin == null) {
            // Return standard response if OPTIONS request w/o Origin header
           if ("OPTIONS".equalsIgnoreCase(httpReq.getMethod())) {
                httpResp.setHeader("Allow", VALID_METHODS);
                httpResp.setStatus(200);
                return;
            }
        } else {
            // This is a cross-domain request, add headers allowing access
            httpResp.setHeader("Access-Control-Allow-Origin", origin);
            httpResp.setHeader("Access-Control-Allow-Methods", VALID_METHODS);
            httpResp.setHeader("Access-Control-Expose-Headers", headerList);

            String headers = httpReq.getHeader("Access-Control-Request-Headers");
            LOGGER.info("HEADERS :: " + headers);
            if (headers != null){
            	LOGGER.info("HEADERS ALLOWED :: " + headers);
                httpResp.setHeader("Access-Control-Allow-Headers", headers);
            }

            // Allow caching cross-domain permission
            httpResp.setHeader("Access-Control-Max-Age", "3600");
        }
        // Pass request down the chain, except for OPTIONS
        if (!"OPTIONS".equalsIgnoreCase(httpReq.getMethod())) {
            chain.doFilter(req, resp);
        }
        LOGGER.info("FILTER END");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}