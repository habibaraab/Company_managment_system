package com.spring.Company.Repo;

import com.spring.Company.Model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {

}
