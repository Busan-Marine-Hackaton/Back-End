package com.example.marinehackatonbe.api;

import com.example.marinehackatonbe.application.LocationService;
import com.example.marinehackatonbe.domain.Location;
import com.example.marinehackatonbe.dto.LocationDto;
import com.example.marinehackatonbe.global.domain.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {
	private final LocationService locationService;

	@GetMapping
	public ResponseEntity<CommonResponse<List<Location>>> getAllLocations() {
		List<Location> locations = locationService.getAllLocations();
		return ResponseEntity.ok(CommonResponse.ofSuccess("쓰레기 위치가 성공적으로 반환되었습니다.", locations));
	}

	@PostMapping
	public ResponseEntity<CommonResponse<Void>> createLocation(@RequestBody LocationDto locationDto) {
		locationService.createLocation(locationDto);
		return ResponseEntity.ok().body(CommonResponse.ofSuccess("Location이 성공적으로 생성되었습니다.", null));
	}
}
