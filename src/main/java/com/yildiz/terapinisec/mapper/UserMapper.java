package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.*;
import com.yildiz.terapinisec.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring" )
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", ignore = true)
    UserResponseDto touserResponseDto(User user);

    User toUser(UserCreateDto userCreateDto);

    void updateUserFromDto(UserUpdateDto userUpdateDto, @MappingTarget User user);

    User toUser (UserLoginDto userLoginDto);

    UserLoginDto toUserLoginDto (User user);

    User toUser (UserDto userDto);

    UserDto toUserDto (User user);
}