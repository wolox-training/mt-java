package com.wolox.training.repositories;

import com.wolox.training.models.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    public Optional<Users> findByUsername(String username);

}
