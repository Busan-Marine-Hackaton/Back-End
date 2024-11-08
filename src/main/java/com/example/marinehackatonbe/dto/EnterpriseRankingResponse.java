package com.example.marinehackatonbe.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EnterpriseRankingResponse {
	private Long enterpriseId;
	private String name;
	private String realId;
	private String photoUrl;
	private Integer point;
	private int ranking;
}
