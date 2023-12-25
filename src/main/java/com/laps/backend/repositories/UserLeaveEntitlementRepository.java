package com.laps.backend.repositories;

import com.laps.backend.models.User;
import com.laps.backend.models.UserLeaveEntitlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserLeaveEntitlementRepository extends JpaRepository<UserLeaveEntitlement, Long> {
    @Query("SELECT u FROM UserLeaveEntitlement u WHERE u.user.id = :userId")
    UserLeaveEntitlement findByUserId(Long userId);
}
