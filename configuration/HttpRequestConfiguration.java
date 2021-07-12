package com.tcra.hrms.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.tcra.hrms.entity.ResponseCodeEntity;

public class HttpRequestConfiguration {

	private static URL url = null;
	private static HttpURLConnection con = null;
	private static BufferedReader reader = null;
	private static String responseLine;
	StringBuffer responseContent = new StringBuffer();
	//private static String prefix = "Bearer ";
	
	public static String connectToExternalSystem(String uri, String contentToSend, String method, String token) throws IOException {		
		StringBuilder response = new StringBuilder();
		ResponseCodeEntity responseCodeEntity = new ResponseCodeEntity();
		url = new URL (uri);
		con = (HttpURLConnection)url.openConnection();
	
		if(method.toUpperCase().equals("POST")) {
			con.setRequestMethod(method);				
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Authorization",  token);
			con.setConnectTimeout(50000);
			con.setReadTimeout(50000);
			con.setDoOutput(true);
						
			try(OutputStream os = con.getOutputStream()){
				byte[] input = contentToSend.getBytes("utf-8");
				os.write(input, 0, input.length);	
				//os.close();
			}
			int responseCode = con.getResponseCode();
			
			responseCodeEntity.setStatusCode(responseCode);
			responseCodeEntity.setToken(con.getHeaderField("authorization"));
			System.out.println("Token: " + responseCodeEntity.getToken()+ " Status Code: " + responseCodeEntity.getStatusCode());
			
			if (responseCode == 200) {
				System.out.println(responseCode);
			try{					
				reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
				while ((responseLine = reader.readLine()) != null) {
					response.append(responseLine.trim());
				}				
			}finally {
				con.disconnect();	
				reader.close();
			}
			}else {
				
			}
		}else {
			con.setRequestMethod(method);				
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Authorization", token);
			con.setConnectTimeout(50000);
			con.setReadTimeout(50000);
			int responseCode = con.getResponseCode();		
			responseCodeEntity.setStatusCode(responseCode);
			responseCodeEntity.setToken(con.getHeaderField("authorization"));
			try {
			if(responseCode > 299) {			
				reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				while((responseLine = reader.readLine()) != null) {
					response.append(responseLine);
				}
			}else {
				reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				while((responseLine = reader.readLine()) != null) {
					response.append(responseLine);
				}			
			}
			}finally {
				con.disconnect();	
				reader.close();
			}			
		}
		return response.toString();
	}
}
