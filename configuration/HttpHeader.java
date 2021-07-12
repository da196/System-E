package com.tcra.hrms.configuration;

import java.util.Collections;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpHeader {
	
	public static HttpHeaders setHttpHeader(String authToken) {
		 HttpHeaders headers = new HttpHeaders();
		  headers.setContentType(MediaType.APPLICATION_JSON);
		  headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));		 
		  headers.add("Authorization", authToken);
		  
		  return headers;
	}

}
