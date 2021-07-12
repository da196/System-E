package com.tcra.hrms.utils;

import com.tcra.hrms.dto.payroll.PayrollDate;

public class DateUtils {
	
	public static PayrollDate toPayrollDate(String date) {
		PayrollDate cycle = null;
		if(date!=null) {
			final String[] dateParts = date.split("-");
			if(dateParts!=null) {
				cycle = new PayrollDate();
				// add year
				final String year = dateParts[2];
				if(year!=null) {
					cycle.setYear(Integer.parseInt(year));
				}
				// add month
				final String month = dateParts[1];
				if(month!=null) {
					cycle.setMonth(Integer.parseInt(month));
				}
				// add day
				final String day = dateParts[0];
				if(day!=null) {
					cycle.setDay(Integer.parseInt(day));
				}
			}
		}		
		return cycle;
	}
	
	public static String toYYYYMMDD(String date) {
		if(date!=null) {
			final String[] dateParts = date.split("-");
			if(dateParts!=null) {
				StringBuilder dateBuilder = new StringBuilder();
				// add year
				final String year = dateParts[2];
				if(year!=null) {
					dateBuilder.append(year);
				}
				// add month
				final String month = dateParts[1];
				if(year!=null) {
					dateBuilder.append("-");
					dateBuilder.append(month);
				}
				// add day
				final String day = dateParts[0];
				if(year!=null) {
					dateBuilder.append("-");
					dateBuilder.append(day);
				}
				if(dateBuilder!=null && dateBuilder.length()>0) {
					return dateBuilder.toString();
				}
			}
		}		
		return null;
	}

}
