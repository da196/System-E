package com.tcra.hrms.dto;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment implements Serializable{
	
	private static final long  serialVersionUID = 1L;
	
	private String comment;

}
