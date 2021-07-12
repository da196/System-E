package com.tcra.hrms.services.appraisal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tcra.hrms.configuration.AppConstants;
import com.tcra.hrms.configuration.HttpHeader;
import com.tcra.hrms.dto.appraisal.EmployeePerformance;
import com.tcra.hrms.dto.appraisal.PerformanceBehaviour;
import com.tcra.hrms.dto.appraisal.PerformancePlanning;

@Service
public class AppraisalServices implements IAppraisalServices {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResponseEntity<PerformancePlanning> CreateAgreement(String authToken, PerformancePlanning planning) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<PerformancePlanning> request = new HttpEntity<PerformancePlanning>(planning, headers);
		final ResponseEntity<PerformancePlanning> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/performanceEppa/addPerformanceEppaV2/", HttpMethod.POST, request,
				PerformancePlanning.class);
		return response;
	}

	@Override
	public ResponseEntity<?> GetEmployeePerformance(String authToken, int employeeId, int year) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Integer> request = new HttpEntity<Integer>(headers);
		final ResponseEntity<?> response = restTemplate.exchange(AppConstants.BASE_URL
				+ "/performanceEppa/getAllPerformanceEppaV3ByEmployeeIdAndFinancialYearId/" + employeeId + "/" + year,
				HttpMethod.GET, request, Object.class);
		return response;
	}

	@Override
	public ResponseEntity<?> GetEmployeeMidYearReview(String authToken, int eppaid) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Integer> request = new HttpEntity<Integer>(headers);
		final ResponseEntity<?> response = restTemplate
				.exchange(
						AppConstants.BASE_URL
								+ "/performanceEppaReviewMidYear/getAllPerformanceEppaReviewMidYearByEppaId/" + eppaid,
						HttpMethod.GET, request, Object.class);
		return response;
	}

	@Override
	public ResponseEntity<?> GetEmployeeAnnualYearReview(String authToken, int eppaid) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Integer> request = new HttpEntity<Integer>(headers);
		final ResponseEntity<?> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/performanceEppaReviewAnnual/getAllAnnualReviewByEppaId/" + eppaid,
				HttpMethod.GET, request, Object.class);
		return response;
	}

	@Override
	public ResponseEntity<?> GetEmployeeBehaviour(String authToken, int eppaid) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Integer> request = new HttpEntity<Integer>(headers);
		final ResponseEntity<?> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/performanceEab/getAllPerformanceEabByEppaIdV2/" + eppaid, HttpMethod.GET,
				request, Object.class);
		return response;
	}

	@Override
	public ResponseEntity<PerformanceBehaviour> UpdateBehaviour(String authToken, int id,
			PerformanceBehaviour behaviour) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<PerformanceBehaviour> request = new HttpEntity<PerformanceBehaviour>(behaviour, headers);
		final ResponseEntity<PerformanceBehaviour> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/performanceEab/updatePerformanceEab/" + id, HttpMethod.PUT, request,
				PerformanceBehaviour.class);
		return response;
	}

	@Override
	public ResponseEntity<PerformanceBehaviour> CreateBehaviour(String authToken,
			PerformanceBehaviour behaviour) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<PerformanceBehaviour> request = new HttpEntity<PerformanceBehaviour>(behaviour, headers);
		final ResponseEntity<PerformanceBehaviour> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/performanceEab/addPerformanceEab/", HttpMethod.POST, request,
				PerformanceBehaviour.class);
		return response;
	}

}
