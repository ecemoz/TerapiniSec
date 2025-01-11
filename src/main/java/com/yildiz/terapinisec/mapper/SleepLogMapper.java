package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.SleepLogCreateDto;
import com.yildiz.terapinisec.dto.SleepLogDetailedDto;
import com.yildiz.terapinisec.dto.SleepLogResponseDto;
import com.yildiz.terapinisec.model.SleepLog;
import com.yildiz.terapinisec.model.User;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SleepLogMapper {

    public SleepLog toSleepLog(SleepLogCreateDto createDto, User sleeper) {
        if (createDto == null || sleeper == null) {
            return null;
        }

        return SleepLog.builder()
                .sleepDuration(createDto.getSleepDuration())
                .sleepQuality(createDto.getSleepQuality())
                .sleeper(sleeper)
                .build();
    }

    public SleepLogResponseDto toSleepLogResponseDto(SleepLog sleepLog) {
        if (sleepLog == null) {
            return null;
        }

        return SleepLogResponseDto.builder()
                .id(sleepLog.getId())
                .sleepDuration(sleepLog.getSleepDuration())
                .sleepQuality(sleepLog.getSleepQuality())
                .sleepDate(sleepLog.getSleepDate())
                .sleeperUsername(sleepLog.getSleeper() != null ? sleepLog.getSleeper().getUserName() : null)
                .build();
    }

    public SleepLogDetailedDto toSleepLogDetailedDto(SleepLog sleepLog) {
        if (sleepLog == null) {
            return null;
        }

        return SleepLogDetailedDto.builder()
                .id(sleepLog.getId())
                .sleepDuration(sleepLog.getSleepDuration())
                .sleepQuality(sleepLog.getSleepQuality())
                .sleepDate(sleepLog.getSleepDate())
                .sleeperUsername(sleepLog.getSleeper() != null ? sleepLog.getSleeper().getUserName() : null)
                .sleeperId(sleepLog.getSleeper() != null ? sleepLog.getSleeper().getId() : null)
                .build();
    }

    public List<SleepLogResponseDto> toSleepLogResponseDtoList(List<SleepLog> sleepLogs) {
        if (sleepLogs == null || sleepLogs.isEmpty()) {
            return List.of();
        }

        return sleepLogs.stream()
                .map(this::toSleepLogResponseDto)
                .collect(Collectors.toList());
    }

    public List<SleepLogDetailedDto> toSleepLogDetailedDtoList(List<SleepLog> sleepLogs) {
        if (sleepLogs == null || sleepLogs.isEmpty()) {
            return List.of();
        }

        return sleepLogs.stream()
                .map(this::toSleepLogDetailedDto)
                .collect(Collectors.toList());
    }
}
