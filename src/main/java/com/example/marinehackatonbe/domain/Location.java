package com.example.marinehackatonbe.domain;

import com.example.marinehackatonbe.domain.enums.Condition;
import com.example.marinehackatonbe.dto.LocationDto;
import com.example.marinehackatonbe.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
public class Location extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private double startX;
	private double startY;
	private double endX;
	private double endY;
	private double x;
	private double y;

	private Integer springCount;

	@Enumerated(EnumType.STRING)
	private Condition springCondition;

	private Integer summerCount;

	@Enumerated(EnumType.STRING)
	private Condition summerCondition;

	private Integer fallCount;

	@Enumerated(EnumType.STRING)
	private Condition fallCondition;

	private Integer winterCount;

	@Enumerated(EnumType.STRING)
	private Condition winterCondition;

	public static Location from(LocationDto dto) {
		return Location.builder()
			.startX(dto.startX())
			.startY(dto.startY())
			.endX(dto.endX())
			.endY(dto.endY())
			.springCount(dto.springCount())
			.springCondition(dto.springCondition())
			.summerCount(dto.summerCount())
			.summerCondition(dto.summerCondition())
			.fallCount(dto.fallCount())
			.fallCondition(dto.fallCondition())
			.winterCount(dto.winterCount())
			.winterCondition(dto.winterCondition())
			.build();
	}
}
