package com.tcra.hrms.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RefereeCategoryEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private int code;

	private String name;

	private String abbreviation;

	private String description;

	private int approved;

	private int active;
}
