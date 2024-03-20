package br.com.webfluxcourse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.webfluxcourse.entities.User;
import br.com.webfluxcourse.model.request.UserRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {

	@Mapping(target = "id", ignore = true)
	User toEntity(final UserRequest request);

}
