package com.tcra.hrms.dto.payroll;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payslip implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int year;
	private int month;
	private int day;
	private String payrollPeriod;
	private int employeeid;
	private String employeeFullName;
	private String designation;
	private String department;
	private String fileNumber;
	private String office;
	private String officetype;
	// date
	private String dateofEmployment;
	// pay
	private String modeofPayment;
	private String salaryScale;
	private Double basicsalaryAmount;
	private Double grosssalaryAmount;
	private Double netsalaryAmount;
	private Double payeAmount;
	private Double pensionAmount;
	private String pensionName;
	private Double amountotherincome;
	private Double totalDeductionAmount;
	private Double amounttaxable;
	private Double totalbankAccountAmountTransfered;
	// list
	private List<Allowance> allowancelist;
	private List<PayrollBankAccount> bankAccountTranserlist;
	private List<DeductionLoan> deductionLoanlist;
	private List<DeductionVoluntary> deductionVoluntarylist;
}
