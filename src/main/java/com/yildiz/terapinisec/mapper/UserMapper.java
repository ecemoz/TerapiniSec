package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.*;
import com.yildiz.terapinisec.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring" )
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    UserResponseDto toUserResponseDto(User user);

    User toUser(UserCreateDto userCreateDto);

    void updateUserFromDto(UserUpdateDto userUpdateDto, @MappingTarget User user);

    User toUser (UserLoginDto userLoginDto);

    UserLoginDto toUserLoginDto (User user);

    User toUser (UserDto userDto);

    UserDto toUserDto (User user);

    List<UserResponseDto> toUserResponseDtoList(List<User> users);
}