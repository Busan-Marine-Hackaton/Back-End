package com.example.marinehackatonbe.application;

import com.example.marinehackatonbe.domain.Member;
import com.example.marinehackatonbe.domain.PhotoAuthentication;
import com.example.marinehackatonbe.global.exception.CustomException;
import com.example.marinehackatonbe.global.exception.ExceptionContent;
import com.example.marinehackatonbe.infrastructure.MemberRepository;
import com.example.marinehackatonbe.infrastructure.PhotoAuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PhotoAuthenticationRepository photoAuthenticationRepository;
	@Transactional
	public void createMember(String name, String password) {
		if (password.length() != 4 || !password.matches("\\d+")) {
			throw new CustomException(ExceptionContent.NOT_FOUND_MEMBER);
		}
		Member newMember = Member.builder()
			.name(name)
			.password(password)
			.point(0)
			.build();
		memberRepository.save(newMember);
	}

	public Long login(String name, String password) {
		Member member = memberRepository.findByName(name)
			.orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_MEMBER));
		if(member.getPassword().equals(password)){ return member.getId();}
		else {
			throw new CustomException(ExceptionContent.WRONG_PASSWORD);
		}
	}

	public List<Member> getRanking() {
		List<Member> ranking = memberRepository.findAll();
		ranking.sort((m1, m2) -> Integer.compare(m2.getPoint(), m1.getPoint()));
		return ranking;
	}

	@Transactional
	public boolean uploadPhoto(Long memberId, MultipartFile photo) throws IOException {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_MEMBER));;

		byte[] photoBytes = photo.getBytes();
		PhotoAuthentication newPhotoAuthentication = PhotoAuthentication.builder()
				.member(member)
				.photo(photoBytes)
				.build();

		member.addPhotoPoint();
		photoAuthenticationRepository.save(newPhotoAuthentication);
		memberRepository.save(member);
		return true;
	}

	@Transactional
	public void addMemberPoints(Long memberId, int points) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_MEMBER));
		member.addPoint(points);
		memberRepository.save(member);
	}
}
