package com.example.marinehackatonbe.domain;

import com.example.marinehackatonbe.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
public class PhotoAuthentication extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	private Member member;
	@Lob
	private byte[] photo;

}
