package com.example.marinehackatonbe.infrastructure;

import com.example.marinehackatonbe.domain.PhotoAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoAuthenticationRepository extends JpaRepository<PhotoAuthentication, Long> {
}
