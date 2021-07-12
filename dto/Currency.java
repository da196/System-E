package com.tcra.hrms.dto;                                                                                             

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency implements Serializable  {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int code;
	private String name;
	private String description;
	private String active;
	private String abbreviation;
	private String isocode;
                                                                                                                                                                                    
}
