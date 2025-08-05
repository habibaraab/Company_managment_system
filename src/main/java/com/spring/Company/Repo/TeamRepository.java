package com.spring.Company.Repo;

import com.spring.Company.Model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    @Query("SELECT t FROM Team t WHERE t.managerId.id = :idOfManager")
    List<Team> findByManagerId(@Param("idOfManager") int managerId);

    Optional<Team> findFirstByMembers_Id(int memberId);
}
