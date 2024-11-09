package com.example.marinehackatonbe.domain;

import com.example.marinehackatonbe.global.domain.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

	private String photoUrl;

	private String password;

	private Integer point;

	@JsonIgnore
	@OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Member> members = new ArrayList<>();

	public void addPoints(int points) {
		this.point += points;
	}

	public void addMember(Member member) {
		this.members.add(member);
		member.setEnterprise(this);
	}
}
