package com.example.marinehackatonbe.api;

import com.example.marinehackatonbe.application.MemberService;
import com.example.marinehackatonbe.domain.Member;
import com.example.marinehackatonbe.global.domain.CommonResponse;
import com.example.marinehackatonbe.global.exception.CustomException;
import com.example.marinehackatonbe.global.exception.ExceptionContent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PostMapping()
	public ResponseEntity<CommonResponse<Void>> registerMember(@RequestParam String name, @RequestParam String password) {
		memberService.createMember(name, password);
		return ResponseEntity.ok().body(CommonResponse.ofSuccess("멤버가 등록되었습니다.",null));
	}

	@PutMapping("/login")
	public ResponseEntity<CommonResponse<Void>> login(@RequestParam String name, @RequestParam String password) {
		memberService.login(name, password);
		return ResponseEntity.ok().body(CommonResponse.ofSuccess("로그인에 성공하였습니다.",null));
	}

	@PostMapping("/uploadPhoto")
	public ResponseEntity<CommonResponse<Void>> uploadPhoto(@RequestParam Long memberId, @RequestParam("photo") MultipartFile photo) {
		try {
			if (memberService.uploadPhoto(memberId, photo)) {
				return ResponseEntity.ok().body(CommonResponse.ofSuccess("사진 업로드에 성공하였습니다.", null));
			} else {
				throw new CustomException(ExceptionContent.FAIL_PHOTO_UPLOAD);
			}
		} catch (IOException e) {
			throw new CustomException(ExceptionContent.FAIL_PHOTO_UPLOAD);
		}
	}

	@GetMapping("/ranking")
	public ResponseEntity<List<Member>> getRanking() {
		return ResponseEntity.ok(memberService.getRanking());
	}
}
