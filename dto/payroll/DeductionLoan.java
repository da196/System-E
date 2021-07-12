package com.tcra.hrms.dto.payroll;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductionLoan implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int loantypeCode;
	private String loantype;
	private Double amountdeductionloan;
	private Double amountloanbalance;
}
