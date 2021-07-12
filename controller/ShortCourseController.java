package com.tcra.hrms.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcra.hrms.auth.IAuthenticationFacade;
import com.tcra.hrms.configuration.AppConstants;
import com.tcra.hrms.configuration.HttpStatusCode;
import com.tcra.hrms.configuration.Utility;
import com.tcra.hrms.dto.ShortCourse;
import com.tcra.hrms.entity.EmploymentHistoryEntityById;
import com.tcra.hrms.services.IShortCourseService;
import com.tcra.hrms.utils.DateUtils;

@Controller
public class ShortCourseController {
	static final Logger logger = Logger.getLogger(ShortCourseController.class); // log4j

	@Autowired
	private IAuthenticationFacade authenticationFacade;
	@Autowired
	private IShortCourseService shortCourseService;

	@RequestMapping(value = "/short-course-add.htm", method = RequestMethod.POST)
	public String AddShortCourse(Model model, @ModelAttribute("shortCourse") @Valid ShortCourse shortCourse,
			BindingResult bindingResult, @RequestParam("fileUpload") MultipartFile fileUpload,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {

		final String authToken = authenticationFacade.getAuthenticationToken();
		final int empID = authenticationFacade.getUser().getEmpId();
		shortCourse.setEmployeeid(empID);

		if (!fileUpload.isEmpty()) {
			final Timestamp timestampNow = new Timestamp(System.currentTimeMillis());
			final Long fileId = timestampNow.getTime();

			final String extensionType = FilenameUtils.getExtension(fileUpload.getOriginalFilename());
			if (extensionType.toLowerCase().equals("pdf") || extensionType.toLowerCase().equals("png")
					|| extensionType.toLowerCase().equals("jpeg")) {
				final String fileName = Utility.trimFileExtension(fileUpload.getOriginalFilename());
				final String newFileName = fileName + "_" + fileId + "." + extensionType;
				final String fileURL = AppConstants.UPLOAD_DIRECTORY + newFileName;

				boolean isFileUpload = Utility.uploadFile(fileUpload, fileURL, redirectAttributes);
				if (isFileUpload) {
					shortCourse.setAttachmenttypeid(4); // short course type?
					shortCourse.setAttachmentname(fileName);
					shortCourse.setUri(AppConstants.URL + newFileName);
				}
			} // END file extension check
		} // END - has file to upload -

		// proceed with add course to database
		logger.info("Date start is " + shortCourse.getDatestart());
		logger.info("Date end " + shortCourse.getDatend());

		// parse dates
		try {
			final String start = DateUtils.toYYYYMMDD(shortCourse.getDatestart());
			final String end = DateUtils.toYYYYMMDD(shortCourse.getDatend());
			shortCourse.setDatestart(start);
			shortCourse.setDatend(end);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Date start is " + shortCourse.getDatestart());
		logger.info("Date end " + shortCourse.getDatend());

		final ResponseEntity<ShortCourse> result = shortCourseService.Add(authToken, shortCourse);
		if (result != null) {
			int statusCode = result.getStatusCodeValue();
			HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
		} else {
			redirectAttributes.addFlashAttribute("error", "Failure to add short course, please try again!");
		}
		return "redirect:/employee-personal-information.htm";
	}

	@RequestMapping(value = "/short-course-update.htm", method = RequestMethod.POST)
	public String UpdateShortCourse(Model model, @ModelAttribute("shortCourse") @Valid ShortCourse shortCourse,
			BindingResult bindingResult, @RequestParam("fileUpload") MultipartFile fileUpload,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {

		final String authToken = authenticationFacade.getAuthenticationToken();
		final int empID = authenticationFacade.getUser().getEmpId();
		final int courseID = shortCourse.getId();

		// update data
		shortCourse.setEmployeeid(empID);

		if (!fileUpload.isEmpty()) {
			final Timestamp timestampNow = new Timestamp(System.currentTimeMillis());
			final Long fileId = timestampNow.getTime();

			final String extensionType = FilenameUtils.getExtension(fileUpload.getOriginalFilename());
			if (extensionType.toLowerCase().equals("pdf") || extensionType.toLowerCase().equals("png")
					|| extensionType.toLowerCase().equals("jpeg")) {
				final String fileName = Utility.trimFileExtension(fileUpload.getOriginalFilename());
				final String newFileName = fileName + "_" + fileId + "." + extensionType;
				final String fileURL = AppConstants.UPLOAD_DIRECTORY + newFileName;

				boolean isFileUpload = Utility.uploadFile(fileUpload, fileURL, redirectAttributes);
				if (isFileUpload) {
					shortCourse.setAttachmenttypeid(4); // short course type?
					shortCourse.setAttachmentname(fileName);
					shortCourse.setUri(AppConstants.URL + newFileName);
				}
			} // END file extension check
		} // END - has file to upload -
			// proceed with add course to database

		logger.info("Date start is " + shortCourse.getDatestart());
		logger.info("Date end " + shortCourse.getDatend());

		// parse dates
		try {
			final String start = DateUtils.toYYYYMMDD(shortCourse.getDatestart());
			final String end = DateUtils.toYYYYMMDD(shortCourse.getDatend());
			shortCourse.setDatestart(start);
			shortCourse.setDatend(end);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Date start is " + shortCourse.getDatestart());
		logger.info("Date end " + shortCourse.getDatend());

		final ResponseEntity<ShortCourse> result = shortCourseService.Update(authToken, courseID, shortCourse);
		if (result != null) {
			int statusCode = result.getStatusCodeValue();
			HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
		} else {			
			redirectAttributes.addFlashAttribute("error", "Failure to update short course, please try again!");
		}
		return "redirect:/employee-personal-information.htm";
	}

	@RequestMapping(value = "/short-course.htm/{id}", method = RequestMethod.GET)
	public @ResponseBody ShortCourse Get(@PathVariable("id") int id, HttpServletRequest request, Model model) {
		final String authToken = authenticationFacade.getAuthenticationToken();
		final ResponseEntity<ShortCourse> response = shortCourseService.Get(authToken, id);
		return response.getBody();
	}

	@RequestMapping(value = "/short-course-delete.htm/{id}", method = RequestMethod.GET)
	public @ResponseBody int Delete(@PathVariable("id") int id, HttpServletRequest request, Model model) {
		final String authToken = authenticationFacade.getAuthenticationToken();
		final ResponseEntity<Integer> response = shortCourseService.Delete(authToken, id);
		if (response == null) {
			return 0;
		}
		int statusCode = response.getStatusCodeValue();
		return statusCode;
	}
}
