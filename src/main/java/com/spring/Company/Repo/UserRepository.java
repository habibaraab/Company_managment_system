package com.spring.Company.Repo;

import com.spring.Company.Mapper.UserProjection;
import com.spring.Company.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);

    @Query("SELECT u.id AS id, u.name AS name, u.title AS title, u.role AS role, " +
            "u.email AS mail, u.phone AS phone, u.level AS level, " +
            "u.manager.id AS managerId, u.department.id AS departmentId , u.password AS password " +
            "FROM User u WHERE u.id = :id")
    Optional<UserProjection> findUserById(@Param("id") int id);


}
