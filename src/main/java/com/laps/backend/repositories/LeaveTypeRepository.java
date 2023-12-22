package com.laps.backend.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.laps.backend.models.LeaveType;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Integer> {

	@Query("SELECT l FROM LeaveType l where l.id = :id")
	Optional<LeaveType> findById(@Param("id") Integer id);

	LeaveType save(LeaveType leaveType);
}
