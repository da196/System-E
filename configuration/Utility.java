package com.tcra.hrms.configuration;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class Utility {

	public static String trimFileExtension(String fileName) {
		String[] splits = fileName.split("\\.");
		return StringUtils.delete(fileName, "." + splits[splits.length - 1]);
	}
	
	public static boolean uploadFile(MultipartFile file, String fileURL, RedirectAttributes redirectAttributes) {
		boolean uploadsuccess = false;
		double bytes = file.getSize();
        double kilobytes = (bytes / 1024);
		double megabytesFileSize = (kilobytes / 1024);
		if (!file.isEmpty()) {	
				try {
				   
	    			System.out.println("megabytesFileSize: " + megabytesFileSize);
					// String type = file.getOriginalFilename().split("\\.")[1];
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fileURL)));
					FileCopyUtils.copy(file.getInputStream(), stream);
					stream.close();
					uploadsuccess = true;
				} catch (Exception e) {
					System.err.println(e);
					uploadsuccess = false;
				}		
		}
		return uploadsuccess;
	}
	
	public static void checkFileSize(MultipartFile file) {
		
	}
}
