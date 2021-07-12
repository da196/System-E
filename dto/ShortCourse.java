package com.tcra.hrms.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
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
public class ShortCourse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String coursename;
	private String description;
	private String institution;
	private int countryid;
	private String countryname;		
	private int attchmentid;
	private String attachmentdescription;
	private String attachmentname;
	private int attachmenttypeid;
	private String attachmenttypename;
	private String uri;	
	private String approvalComment;
	private int approved;
	private int active;
	private int employeeid;
	private String employeename;
	private int expire;
	private String datestart;
	private String datend;	
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date datestart;
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date datend;	
//	// exclude @Transient
//	private String datePickerStart;
//    private String datePickerEnd;  
    
}
