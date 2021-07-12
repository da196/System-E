package com.tcra.hrms.entity.training;

import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class HrmsTrainingCategory {

	private int id;

	private int code;

	private String name;

	private String description;

	private int approved;

	private int active;


}
