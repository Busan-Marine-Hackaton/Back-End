package com.example.marinehackatonbe.infrastructure;

import com.example.marinehackatonbe.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
