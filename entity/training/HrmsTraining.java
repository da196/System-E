package com.tcra.hrms.entity.training;

import java.io.Serializable;
import java.util.Date;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class HrmsTraining  implements Serializable{

	private static final long serialVersionUID = 1L;


	private int id;

	private String description;

	private String institution;

	private String institutionaddress;

	private Double trainingcost;

	private String attachment;
	
	private String feestructureattachment;

	private int currencyid;

	private int trainingpurposeid;

	private int unplanned;

	private int delayed;
	
	private String delayedreason;

	private int unattended;

	private String unattendedreason;

	private String dateexpectedstart;

	private String date_expected_end;

	private int financialyearid;

	private int trainingcategoryid;

	private int trainingtypeid;

	private int traininginitiatorid;

	private int trainingsponsorid;

	private int employeeid;

	private int approved;

	private int active;
	
	private String name;

}
