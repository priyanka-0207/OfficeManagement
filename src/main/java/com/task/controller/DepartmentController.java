package com.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.dao.DepartmentRepository;
import com.task.model.Department;
import com.task.service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@PostMapping
	public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
		return ResponseEntity.ok(departmentService.saveDepartment(department));
	}

	@GetMapping
	public ResponseEntity<List<Department>> getAllDepartments() {
		return ResponseEntity.ok(departmentService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
		return departmentService.findById(id).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping("/total")
	public ResponseEntity<Integer> getTotalDepartments() {
		int totalEmployees = departmentService.getTotalDepartments();
		return ResponseEntity.ok(totalEmployees);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Department> updateDepartment(@PathVariable Long id,
			@RequestBody Department departmentDetails) {
		return ResponseEntity.ok(departmentService.update(id, departmentDetails));
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.ok().build();
    }
	
	@GetMapping("/employee-count")
    public ResponseEntity<List<DepartmentRepository.DepartmentEmployeeCount>> getEmployeeCountPerDepartment() {
        List<DepartmentRepository.DepartmentEmployeeCount> counts = departmentService.getEmployeeCountPerDepartment();
        return ResponseEntity.ok(counts);
    }

}
