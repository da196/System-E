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
public class District implements Serializable{
	private int id;
	private int cityid;
	private String name;
	private String abbreviation;
	private String description;
	private int approved;
	private int active;
}
