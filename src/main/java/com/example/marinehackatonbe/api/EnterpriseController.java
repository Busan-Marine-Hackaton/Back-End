package com.example.marinehackatonbe.api;

import com.example.marinehackatonbe.application.EnterpriseService;
import com.example.marinehackatonbe.domain.Enterprise;
import com.example.marinehackatonbe.dto.EnterpriseRankingResponse;
import com.example.marinehackatonbe.global.domain.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

	@Operation(summary = "기업 랭킹 조회", description = "모든 기업의 포인트 랭킹을 반환합니다.")
	@ApiResponse(responseCode = "200", description = "랭킹 조회 성공", content = @Content(schema = @Schema(implementation = List.class)))
	@GetMapping("/ranking")
	public ResponseEntity<CommonResponse<List<Enterprise>>> getRanking() {
		return ResponseEntity.ok().body(CommonResponse.ofSuccess("기업 랭킹 조회에 성공하였습니다.", enterpriseService.getRanking()));
	}

	@Operation(summary = "기업 포인트 및 랭킹 조회", description = "주어진 기업 ID로 해당 기업의 포인트와 현재 랭킹을 반환합니다.")
	@ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = EnterpriseRankingResponse.class)))
	@GetMapping("/{enterpriseId}/ranking")
	public ResponseEntity<CommonResponse<EnterpriseRankingResponse>> getEnterprisePointAndRanking(
		@Parameter(description = "기업 ID", required = true) @PathVariable Long enterpriseId) {
		EnterpriseRankingResponse response = enterpriseService.getEnterprisePointAndRanking(enterpriseId);
		return ResponseEntity.ok().body(CommonResponse.ofSuccess("기업 포인트와 랭킹 조회 성공", response));
	}
}
