package com.example.marinehackatonbe.application;

import com.example.marinehackatonbe.domain.Location;
import com.example.marinehackatonbe.dto.LocationDto;
import com.example.marinehackatonbe.infrastructure.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationService {
	private final LocationRepository locationRepository;

	public List<Location> getAllLocations() {
		return locationRepository.findAll();
	}

	public void createLocation(LocationDto locationDto) {
		Location location = Location.from(locationDto);
		locationRepository.save(location);
	}
}
