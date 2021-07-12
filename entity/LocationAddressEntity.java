package com.tcra.hrms.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class LocationAddressEntity implements Serializable{


	private static final long serialVersionUID = 1L;
	private int id;
	private String abbreviation;
	private String description;
	private String name;
	private int active;
	private int approved;
	private int code;
}
