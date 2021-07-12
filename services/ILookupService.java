package com.tcra.hrms.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.entity.AttachmentTypeEntity;
import com.tcra.hrms.entity.CertificationCategoryEntity;
import com.tcra.hrms.entity.CityEntity;
import com.tcra.hrms.entity.ContactTypeEntity;
import com.tcra.hrms.entity.CountryEntity;
import com.tcra.hrms.entity.EducationCourseEntity;
import com.tcra.hrms.entity.EducationInstitutionEntity;
import com.tcra.hrms.entity.EducationLevelEntity;
import com.tcra.hrms.entity.EmployeeRelativeCategoryEntity;
import com.tcra.hrms.entity.LocationAddressEntity;
import com.tcra.hrms.entity.NationalityEntity;
import com.tcra.hrms.entity.RefereeCategoryEntity;
import com.tcra.hrms.entity.YearEntity;
@Component
public interface ILookupService {
	
	//Load Lookup
	ResponseEntity<EducationLevelEntity[]> GetEducationLevel(String token);
	ResponseEntity<EducationCourseEntity[]> GetEducationCourses(String token);
	ResponseEntity<EducationCourseEntity[]> GetEducationCourses(int id,String token);
	ResponseEntity<EducationInstitutionEntity[]> GetEducationInstitution(String token);
	ResponseEntity<YearEntity[]> GetYear(String token);
	ResponseEntity<AttachmentTypeEntity[]> GetAttachmentType(String token);
	ResponseEntity<CountryEntity[]> GetCountry(String token);
	ResponseEntity<NationalityEntity[]> GetNationality(String token);
	ResponseEntity<RefereeCategoryEntity[]> GetRefereeCategory(String token);
	ResponseEntity<CertificationCategoryEntity[]> GetCertificationCategory(String token);
	ResponseEntity<EmployeeRelativeCategoryEntity[]> GetEmployeeRelativeCategoryEntity(String token);
	ResponseEntity<CityEntity[]> GetCityEntity(String token);
	ResponseEntity<LocationAddressEntity[]> GetLocationAddressEntity(String token);
	ResponseEntity<ContactTypeEntity[]> GetContactTypeEntity(String token);
}
