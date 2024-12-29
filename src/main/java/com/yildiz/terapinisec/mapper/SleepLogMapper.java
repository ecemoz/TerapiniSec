package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.SleepLogCreateDto;
import com.yildiz.terapinisec.dto.SleepLogDetailedDto;
import com.yildiz.terapinisec.dto.SleepLogResponseDto;
import com.yildiz.terapinisec.model.SleepLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SleepLogMapper {

    @Mapping(source = "userId" , target = "sleeper.id")
    SleepLog toSleepLog(SleepLogCreateDto sleepLogCreateDto);

    @Mapping(source = "sleeper.username" , target = "sleeperUsername")
    SleepLogResponseDto toSleepLogResponseDto(SleepLog sleepLog);

    @Mapping(source = "sleeper.username" , target = "sleeperUsername")
    @Mapping(source = "sleeper.id" , target = "sleeperId")
    SleepLogDetailedDto toSleepLogDetailedDto(SleepLog sleepLog);

    List<SleepLogResponseDto> toSleepLogResponseDtoList(List<SleepLog> sleepLogs);

    List<SleepLogDetailedDto> toSleepLogDetailedDtoList(List<SleepLog> sleepLogs);
}