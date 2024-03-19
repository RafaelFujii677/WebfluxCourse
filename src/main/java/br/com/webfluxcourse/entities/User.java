package br.com.webfluxcourse.entities;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document
public class User implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String name;
	@Indexed(unique = true)
	private String email;
	private String password;

}
