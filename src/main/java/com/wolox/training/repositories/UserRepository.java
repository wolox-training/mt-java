package com.wolox.training.repositories;

import com.wolox.training.models.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE (:infixName IS NULL OR lower(u.name) like lower(concat('%', :infixName,'%')))"
            + " and (:startDate IS NULL OR :endDate IS NULL OR u.birthdate BETWEEN :startDate AND :endDate)")
    public List<User> findByNameIgnoreCaseContainingAndBirthdateBetween(
            @Param("infixName") String infix,
            @Param("startDate") LocalDate startBirthdate, @Param("endDate") LocalDate endBirthdate);

}
