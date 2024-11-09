package com.example.marinehackatonbe.api;

import com.example.marinehackatonbe.application.MemberService;
import com.example.marinehackatonbe.domain.Member;
import com.example.marinehackatonbe.global.domain.CommonResponse;
import com.example.marinehackatonbe.global.exception.CustomException;
import com.example.marinehackatonbe.global.exception.ExceptionContent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

	@Operation(summary = "멤버 등록", description = "이름과 비밀번호로 멤버를 등록합니다.")
	@ApiResponse(responseCode = "200", description = "멤버 등록 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
	@PostMapping()
	public ResponseEntity<CommonResponse<Void>> registerMember(
		@Parameter(description = "멤버 이름", required = true) @RequestParam String name,
		@Parameter(description = "멤버 비밀번호", required = true) @RequestParam String password) {
		memberService.createMember(name, password);
		return ResponseEntity.ok().body(CommonResponse.ofSuccess("멤버가 등록되었습니다.", null));
	}

	@Operation(summary = "로그인", description = "이름과 비밀번호로 로그인합니다.")
	@ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
	@PutMapping("/login")
	public ResponseEntity<CommonResponse<Long>> login(
		@Parameter(description = "멤버 이름", required = true) @RequestParam String name,
		@Parameter(description = "멤버 비밀번호", required = true) @RequestParam String password) {
		Long memberId = memberService.login(name, password);
		return ResponseEntity.ok().body(CommonResponse.ofSuccess("로그인에 성공하였습니다.", memberId));
	}

	@Operation(summary = "사진 업로드", description = "멤버 ID를 통해 사진을 업로드합니다.")
	@ApiResponse(responseCode = "200", description = "사진 업로드 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
	@PostMapping(value = "/uploadPhoto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommonResponse<Void>> uploadPhoto(
		@Parameter(description = "멤버 ID", required = true) @RequestParam Long memberId,
		@Parameter(description = "업로드할 사진 파일", required = true, content = @Content(mediaType = "multipart/form-data")) @RequestParam("photo") MultipartFile photo) {
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

	@Operation(summary = "랭킹 조회", description = "모든 멤버의 랭킹을 반환합니다.")
	@ApiResponse(responseCode = "200", description = "랭킹 조회 성공", content = @Content(schema = @Schema(implementation = List.class)))
	@GetMapping("/ranking")
	public ResponseEntity<List<Member>> getRanking() {
		return ResponseEntity.ok(memberService.getRanking());
	}

	@Operation(summary = "멤버 포인트 추가", description = "멤버 ID와 포인트를 받아 해당 멤버의 포인트를 추가합니다.")
	@ApiResponse(responseCode = "200", description = "포인트 추가 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
	@PutMapping("/{memberId}/addPoints")
	public ResponseEntity<CommonResponse<Void>> addMemberPoints(
		@Parameter(description = "멤버 ID", required = true) @PathVariable Long memberId,
		@Parameter(description = "추가할 포인트", required = true) @RequestParam int points) {
		memberService.addMemberPoints(memberId, points);
		return ResponseEntity.ok().body(CommonResponse.ofSuccess("포인트가 성공적으로 추가되었습니다.", null));
	}
}
