package com.tcra.hrms.dto;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City implements Serializable{
	private int id;
	private int countryid;
	private String name;
	private String abbreviation;
	private String description;
	private int approved;
	private int active;
}
