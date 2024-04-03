package com.task.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.task.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query("SELECT e FROM Employee e LEFT JOIN FETCH e.departments WHERE e.id = :id")
	Optional<Employee> findByIdWithDepartments(@Param("id") Long id);

}
