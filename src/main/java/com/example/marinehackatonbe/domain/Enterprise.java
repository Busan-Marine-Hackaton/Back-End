package com.example.marinehackatonbe.domain;

import com.example.marinehackatonbe.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "enterprises")
public class Enterprise extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "enterprise_id")
	private Long id;

	private String name;

	private String realId;

	private String password;

	private Integer point;

}
