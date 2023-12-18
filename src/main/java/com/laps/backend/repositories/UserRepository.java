package com.laps.backend.repositories;

import com.laps.backend.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String username);
    // Query methods for User
    List<User> findByName(String name);

    List<User> findByRole(Role role);
}

