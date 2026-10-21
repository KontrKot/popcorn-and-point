package ru.kontr.auth_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kontr.auth_service.entity.UserEntity;
import ru.kontr.auth_service.model.RegistrationRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "login", target = "userName")
    UserEntity mapUser(RegistrationRequest request);
}
