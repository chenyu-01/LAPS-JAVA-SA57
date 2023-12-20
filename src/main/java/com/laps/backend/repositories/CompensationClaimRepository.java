package com.laps.backend.repositories;

import com.laps.backend.models.CompensationClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompensationClaimRepository extends JpaRepository<CompensationClaim, Long> {
    // Query methods for CompensationClaim
}
