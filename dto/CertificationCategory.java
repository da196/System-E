package com.tcra.hrms.dto;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CertificationCategory implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int code;
	private String name;
	private String abbreviation;
	private String description;
	private int active;
	private int approved;	

}
