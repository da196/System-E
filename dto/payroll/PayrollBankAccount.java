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
public class PayrollBankAccount implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int employeeid;
	private String employeeFullName;
	private int employeebankaccountid;
	private String accountNumber;
	private String accountName;
	private String bankName;
	private int bankid;
	private String sortCode;
	private int payrollid;
    private Double amount;
    private String currency;
    private int currencyid;
    private String datepay;
    private int day;
    private int month;
    private int year;
    private int approved;
    private int active;
}
