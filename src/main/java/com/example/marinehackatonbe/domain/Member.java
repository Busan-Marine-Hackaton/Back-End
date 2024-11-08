package com.example.marinehackatonbe.domain;

import com.example.marinehackatonbe.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "members")
public class Member extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String name;

	private String password;

	private Integer point;

	public void addPoint(int point) {
		this.point += point;
	}

	public void deductPoint(int price) {
		this.point -= price;
	}

	public void addPhotoPoint() {
		this.point += 100;
	}
}
