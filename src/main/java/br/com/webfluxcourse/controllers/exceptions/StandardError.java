package br.com.webfluxcourse.controllers.exceptions;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StandardError implements Serializable{
	private static final long serialVersionUID = 1L;

	private LocalDateTime timestamp;
	private String path;
	private Integer status;
	private String error;
	private String message;
}
