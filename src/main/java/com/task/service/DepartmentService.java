package com.task.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.dao.DepartmentRepository;
import com.task.model.Department;

@Service
public class DepartmentService {
	@Autowired
	private DepartmentRepository departmentRepository;

	public Department saveDepartment(Department department) {
		return departmentRepository.save(department);
	}

	public List<Department> findAll() {
		return departmentRepository.findAll();
	}

	public Optional<Department> findById(Long id) {
		return departmentRepository.findById(id);
	}
	
	public int getTotalDepartments() {
		List<Department> departments = departmentRepository.findAll();
		return departments.size();
	}

	public Department update(Long id, Department departmentDetails) {
		Department department = departmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Department not found for this id :: " + id));
		department.setName(departmentDetails.getName());
		return departmentRepository.save(department);
	}
	
	public void delete(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found for this id :: " + id));
        departmentRepository.delete(department);
    }
	
	public List<DepartmentRepository.DepartmentEmployeeCount> getEmployeeCountPerDepartment() {
        return departmentRepository.findEmployeeCountPerDepartment();
    }
}
