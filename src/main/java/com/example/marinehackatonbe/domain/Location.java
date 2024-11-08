package com.example.marinehackatonbe.domain;

import com.example.marinehackatonbe.domain.enums.Condition;
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
}
