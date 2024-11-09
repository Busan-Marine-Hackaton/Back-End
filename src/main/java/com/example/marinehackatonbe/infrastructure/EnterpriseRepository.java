package com.example.marinehackatonbe.infrastructure;

import com.example.marinehackatonbe.domain.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {
	Optional<Enterprise> findByNameAndRealId(String name, String realId);
	boolean existsByName(String name);
}
