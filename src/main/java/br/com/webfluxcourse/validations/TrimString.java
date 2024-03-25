package br.com.webfluxcourse.validations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = { TrimStringValidator.class })
@Target(FIELD)
@Retention(RUNTIME)
public @interface TrimString {

	String message() default "field cannot have blank spaces at the beginning or at end";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
