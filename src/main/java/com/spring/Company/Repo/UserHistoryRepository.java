package com.spring.Company.Repo;

import com.spring.Company.Model.User;
import com.spring.Company.Model.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Integer> {
    List<UserHistory> getUserHistoryById(int id);

}
