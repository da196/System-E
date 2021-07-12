package com.tcra.hrms.services.training;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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



@Component
public interface ITrainingService {

	ResponseEntity<HrmsTrainingInitiator[]> GetTrainingInitiator(String authToken);
	ResponseEntity<HrmsTrainingSponsor[]> GetTrainingSponsor(String authToken);
	ResponseEntity<HrmsTrainingType[]> GetTrainingType(String authToken);
	ResponseEntity<TrainingResponse[]> GetTrainingCurrentFinancialYear(String authToken, int empid);
	ResponseEntity<TrainingResponse> GetTrainingDetails(String authToken, int trainingId);
	ResponseEntity<HrmsTraining> CreateTraining(String authToken, HrmsTraining hrmsTraining);
	
	ResponseEntity<HrmsTraining> UpdateTraining(String authToken, HrmsTraining hrmsTraining, int trainingId);
	
	ResponseEntity<HrmsTraining> DeleteTraining(String authToken, int trainingId);
	
	ResponseEntity<TrainingResponse[]> GetTrainingReport(String authToken, int quaterid , int categoryid, int financialyearid);
	
	//Get currency
	ResponseEntity<Currency[]> GetAllCurrency(String authToken);
	
	ResponseEntity<TrainingResponse[]> GetTrainingNotApprovedBySupervisor(String authToken, int supervisorid );
	
	ResponseEntity<TrainingApprovalStatus[]> GetTrainingApprovalStatus(String authToken, int trainingId );
	
	ResponseEntity<Comment> ApproveTraining(String authToken, Comment commentEntity, int trainingId, int status, int supervisorid);
	
	// Training Category
	
	ResponseEntity<HrmsTrainingCategory[]> GetTrainingCategory(String authToken);
	
	
	
}
