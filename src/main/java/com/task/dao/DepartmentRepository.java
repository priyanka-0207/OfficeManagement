package com.task.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.task.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

	@Query("SELECT d.id as departmentId, d.name as departmentName, COUNT(e.id) as employeeCount FROM Department d JOIN d.employees e GROUP BY d.id")
	List<DepartmentEmployeeCount> findEmployeeCountPerDepartment();

	public static interface DepartmentEmployeeCount {
		Long getDepartmentId();

		String getDepartmentName();

		Long getEmployeeCount();
	}
}
