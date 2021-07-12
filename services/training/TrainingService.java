package com.tcra.hrms.services.training;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.tcra.hrms.configuration.AppConstants;
import com.tcra.hrms.configuration.HttpHeader;
import com.tcra.hrms.dto.Comment;
import com.tcra.hrms.dto.Currency;
import com.tcra.hrms.dto.training.TrainingResponse;
import com.tcra.hrms.entity.training.HrmsTraining;
import com.tcra.hrms.entity.training.HrmsTrainingApproval;
import com.tcra.hrms.entity.training.HrmsTrainingCategory;
import com.tcra.hrms.entity.training.HrmsTrainingInitiator;
import com.tcra.hrms.entity.training.HrmsTrainingSponsor;
import com.tcra.hrms.entity.training.HrmsTrainingType;
import com.tcra.hrms.entity.training.TrainingApprovalStatus;


@Service
public class TrainingService implements ITrainingService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger logger = Logger.getLogger(TrainingService.class); // log4j
	
	@Override
	public ResponseEntity<HrmsTrainingCategory[]> GetTrainingCategory(String authToken) {
			
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
			final HttpEntity<HrmsTrainingCategory> request = new HttpEntity<HrmsTrainingCategory> (headers);
			final ResponseEntity<HrmsTrainingCategory[]> response = restTemplate.exchange(AppConstants.BASE_URL+ 
					"/trainingCategory/getAllTrainingCategory", 
					  HttpMethod.GET, 
					  request, 
					  HrmsTrainingCategory[].class);	
			
			System.out.println(response.getStatusCode());
			return response;
	
	}
	
	@Override
	public ResponseEntity<HrmsTrainingInitiator[]> GetTrainingInitiator(String authToken) {
			
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
			final HttpEntity<HrmsTrainingInitiator> request = new HttpEntity<HrmsTrainingInitiator> (headers);
			final ResponseEntity<HrmsTrainingInitiator[]> response = restTemplate.exchange(AppConstants.BASE_URL+ 
					"/trainingInitiator/getAllTrainingInitiator", 
					  HttpMethod.GET, 
					  request, 
					  HrmsTrainingInitiator[].class);			
			return response;
	
	}
	
	@Override
	public ResponseEntity<HrmsTrainingSponsor[]> GetTrainingSponsor(String authToken) {
			
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
			final HttpEntity<HrmsTrainingSponsor> request = new HttpEntity<HrmsTrainingSponsor> (headers);
			final ResponseEntity<HrmsTrainingSponsor[]> response = restTemplate.exchange(AppConstants.BASE_URL+ 
					"/trainingSponsor/getAllTrainingSponsor", 
					  HttpMethod.GET, 
					  request, 
					  HrmsTrainingSponsor[].class);			
			return response;
	}
	
	@Override
	public ResponseEntity<HrmsTrainingType[]> GetTrainingType(String authToken) {
			
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
			final HttpEntity<HrmsTrainingType> request = new HttpEntity<HrmsTrainingType> (headers);
			final ResponseEntity<HrmsTrainingType[]> response = restTemplate.exchange(AppConstants.BASE_URL+ 
					"/trainingType/getAllTrainingType", 
					  HttpMethod.GET, 
					  request, 
					  HrmsTrainingType[].class);			
			return response;
	
	}
	
	//@Override
//	public ResponseEntity<TrainingResponse[]> GetTrainingCurrentFinancialYear(String authToken) {
//			
//			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
//			final HttpEntity<TrainingResponse> request = new HttpEntity<TrainingResponse> (headers);
//			final ResponseEntity<TrainingResponse[]> response = restTemplate.exchange(AppConstants.BASE_URL+ 
//					"/training/getTrainingCurrentYear", 
//					  HttpMethod.GET, 
//					  request, 
//					  TrainingResponse[].class);			
//			return response;
//	
//	}
	
	@Override
	public ResponseEntity<HrmsTraining> CreateTraining(String authToken, HrmsTraining hrmsTraining) {
//		logger.info("Create loan heslb");
		if(!StringUtils.isEmpty(authToken)) {
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
			final HttpEntity<HrmsTraining> request = new HttpEntity<HrmsTraining> (hrmsTraining,headers);		
			final ResponseEntity<HrmsTraining> response = restTemplate.exchange(AppConstants.BASE_URL+
					"/training/addTraining", 
					  HttpMethod.POST, 
					  request, 
					  HrmsTraining.class);			
			return response;
		}
		return null;
	}

	@Override
	public ResponseEntity<TrainingResponse> GetTrainingDetails(String authToken, int trainingId) {
//		logger.info("Create loan heslb");
		if(!StringUtils.isEmpty(authToken)) {
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
			final HttpEntity<TrainingResponse> request = new HttpEntity<TrainingResponse> (headers);		
			final ResponseEntity<TrainingResponse> response = restTemplate.exchange(AppConstants.BASE_URL+
					"/training/getTrainingById/"+ trainingId , 
					  HttpMethod.GET, 
					  request, 
					  TrainingResponse.class);			
			return response;
		}
		return null;
	}
	
	@Override
	public ResponseEntity<HrmsTraining> UpdateTraining(String authToken, HrmsTraining hrmsTraining, int trainingId ) {
		logger.info("Start consuming the endpoint {%s} " + hrmsTraining);
		if(!StringUtils.isEmpty(authToken)) {
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
			final HttpEntity<HrmsTraining> request = new HttpEntity<HrmsTraining> (hrmsTraining,headers);		
			final ResponseEntity<HrmsTraining> response = restTemplate.exchange(AppConstants.BASE_URL+
					"/training/updateTraining/" + trainingId, 
					  HttpMethod.PUT, 
					  request, 
					  HrmsTraining.class);			
			return response;
		}
		return null;
	}
	
	@Override
	public ResponseEntity<HrmsTraining> DeleteTraining(String authToken, int trainingId ) {
		logger.info("Start consuming the endpoint");
		if(!StringUtils.isEmpty(authToken)) {
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
			final HttpEntity<HrmsTraining> request = new HttpEntity<HrmsTraining> (headers);		
			final ResponseEntity<HrmsTraining> response = restTemplate.exchange(AppConstants.BASE_URL+
					"/training/deleteTraining/" + trainingId, 
					  HttpMethod.DELETE, 
					  request, 
					  HrmsTraining.class);			
			return response;
		}
		return null;
	}
	
	// Get Training Report
	@Override
	public ResponseEntity<TrainingResponse[]> GetTrainingReport(String authToken, int quaterid, int categoryid, int financialyearid) {
			
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
			final HttpEntity<TrainingResponse> request = new HttpEntity<TrainingResponse> (headers);
			final ResponseEntity<TrainingResponse[]> response = restTemplate.exchange(AppConstants.BASE_URL+ 
					"/training/getHrmsTrainingByFinancialYearAndQuaterId/" + financialyearid + "/" + quaterid + "/" + categoryid,
					  HttpMethod.GET, 
					  request, 
					  TrainingResponse[].class);			
			return response;
	
	}
	
	@Override
	public ResponseEntity<Currency[]> GetAllCurrency(String authToken) {
		if(restTemplate != null) {
			
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
			HttpEntity<Currency> request = new HttpEntity<Currency> (headers);		
			ResponseEntity<Currency[]> response = restTemplate.exchange(
					AppConstants.BASE_URL+"/currency/getAllCurrencies", 
					  HttpMethod.GET, 
					  request, 
					  Currency[].class);
			
			return response;
		}
		return null;
	}
	
	
	@Override
	public ResponseEntity<TrainingResponse[]> GetTrainingCurrentFinancialYear(String authToken, int employeeID) {
			
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
			final HttpEntity<TrainingResponse> request = new HttpEntity<TrainingResponse> (headers);
			final ResponseEntity<TrainingResponse[]> response = restTemplate.exchange(AppConstants.BASE_URL+ 
					"/training/getTrainingByEmployeeId/" + employeeID, 
					  HttpMethod.GET, 
					  request, 
					  TrainingResponse[].class);			
			return response;
	
	}
	
	@Override
	public ResponseEntity<TrainingResponse[]> GetTrainingNotApprovedBySupervisor(String authToken, int supervisorid) {
			
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
			final HttpEntity<TrainingResponse> request = new HttpEntity<TrainingResponse> (headers);
			final ResponseEntity<TrainingResponse[]> response = restTemplate.exchange(AppConstants.BASE_URL+ 
					"/training/getTrainingNotApprovedBySupervisor/" + supervisorid, 
					  HttpMethod.GET, 
					  request, 
					  TrainingResponse[].class);			
			return response;
	
	}
	
	@Override
	public ResponseEntity<Comment> ApproveTraining(String authToken, Comment commentEntity, int trainingId, int status, int supervisorid) {
		logger.info("Training Parameters --> " + commentEntity.getComment() + " --> " + trainingId + " --> " + status + " --> " + supervisorid);
		if(!StringUtils.isEmpty(authToken)) {
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
			final HttpEntity<Comment> request = new HttpEntity<Comment> (commentEntity,headers);		
			final ResponseEntity<Comment> response = restTemplate.exchange(AppConstants.BASE_URL+
					"/training/ApproveTrainingV2/" + trainingId + "/" + supervisorid + "/" +  status , 
					  HttpMethod.PUT, 
					  request, 
					  Comment.class);			
			return response;
		}
		return null;
	}
	
	@Override
	public ResponseEntity<TrainingApprovalStatus[]> GetTrainingApprovalStatus(String authToken, int trainingid) {
			
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
			final HttpEntity<TrainingApprovalStatus> request = new HttpEntity<TrainingApprovalStatus> (headers);
			final ResponseEntity<TrainingApprovalStatus[]> response = restTemplate.exchange(AppConstants.BASE_URL+ 
					"/training/getTrainingApprovers/" + trainingid, 
					  HttpMethod.GET, 
					  request, 
					  TrainingApprovalStatus[].class);			
			return response;
	
	}
	
	
}
