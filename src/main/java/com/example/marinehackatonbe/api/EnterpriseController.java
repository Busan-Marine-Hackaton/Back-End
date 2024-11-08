package com.example.marinehackatonbe.api;

import com.example.marinehackatonbe.application.EnterpriseService;
import com.example.marinehackatonbe.global.domain.CommonResponse;
import com.example.marinehackatonbe.global.exception.CustomException;
import com.example.marinehackatonbe.global.exception.ExceptionContent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enterprise")
@RequiredArgsConstructor
public class EnterpriseController {
	private final EnterpriseService enterpriseService;

	@Operation(summary = "기업 등록", description = "기업명, 아이디, 비밀번호로 기업을 등록합니다.")
	@ApiResponse(responseCode = "200", description = "기업 등록 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
	@PostMapping()
	public ResponseEntity<CommonResponse<Void>> registerEnterprise(
		@Parameter(description = "기업 이름", required = true) @RequestParam String name,
		@Parameter(description = "기업 아이디", required = true) @RequestParam String realId,
		@Parameter(description = "기업 비밀번호", required = true) @RequestParam String password) {
		enterpriseService.createEnterprise(name, realId, password);
		return ResponseEntity.ok().body(CommonResponse.ofSuccess("기업이 등록되었습니다.", null));
	}

	@Operation(summary = "로그인", description = "기업명, 아이디, 비밀번호로 로그인합니다.")
	@ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
	@PutMapping("/login")
	public ResponseEntity<CommonResponse<Long>> login(
		@Parameter(description = "기업 이름", required = true) @RequestParam String name,
		@Parameter(description = "기업 아이디", required = true) @RequestParam String realId,
		@Parameter(description = "기업 비밀번호", required = true) @RequestParam String password) {
		Long enterpriseId = enterpriseService.login(name, realId, password);
		return ResponseEntity.ok().body(CommonResponse.ofSuccess("로그인에 성공하였습니다.", enterpriseId));
	}
}