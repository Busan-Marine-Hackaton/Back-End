package com.example.marinehackatonbe.dto;

import com.example.marinehackatonbe.domain.enums.Condition;

public record LocationDto(double startX, double startY, double endX, double endY, Integer springCount, Condition springCondition, Integer summerCount, Condition summerCondition, Integer fallCount, Condition fallCondition, Integer winterCount, Condition winterCondition) {
}
