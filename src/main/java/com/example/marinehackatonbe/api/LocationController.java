package com.example.marinehackatonbe.api;

import com.example.marinehackatonbe.application.LocationService;
import com.example.marinehackatonbe.domain.Location;
import com.example.marinehackatonbe.dto.LocationDto;
import com.example.marinehackatonbe.global.domain.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {
	private final LocationService locationService;

	@Operation(summary = "모든 Location 조회", description = "모든 Location 리스트를 반환합니다.")
	@ApiResponse(responseCode = "200", description = "Location 조회 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
	@GetMapping
	public ResponseEntity<CommonResponse<List<Location>>> getAllLocations() {
		List<Location> locations = locationService.getAllLocations();
		return ResponseEntity.ok(CommonResponse.ofSuccess("Location 리스트가 반환되었습니다.", locations));
	}

	@Operation(summary = "Location 생성", description = "새로운 Location을 생성합니다.")
	@ApiResponse(responseCode = "200", description = "Location 생성 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
	@PostMapping
	public ResponseEntity<CommonResponse<Void>> createLocation(
		@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Location 정보") @RequestBody LocationDto locationDto) {
		locationService.createLocation(locationDto);
		return ResponseEntity.ok().body(CommonResponse.ofSuccess("Location이 성공적으로 생성되었습니다.", null));
	}
}
