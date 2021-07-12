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
public class EmploymentHistoryByEmpId implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String instituteName;
	private String jobTitle;
	private String fromDate;
	private String toDate;
}