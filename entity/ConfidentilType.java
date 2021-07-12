package com.tcra.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`confidentialtype`")

public class ConfidentilType {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "`id`")
	private Long id;
	@Column(name = "`confidentialcode`")
	private String confidentialcode;

	@Column(name = "`description`")
	private String description;
	
	
}
