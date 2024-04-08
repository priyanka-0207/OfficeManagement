package com.task.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.dao.EmployeeRepository;
import com.task.model.Employee;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	public List<Employee> getAllEmployees() {
		return (List<Employee>) employeeRepository.findAll();
	}

	public Optional<Employee> getEmployeeById(Long id) {
		return employeeRepository.findByIdWithDepartments(id);
	}

	public int getTotalEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		return employees.size();
	}

	public Employee updateEmployee(Long id, Employee employeeDetails) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found for this id :: " + id));
		employee.setName(employeeDetails.getName());
		employee.setDepartments(employeeDetails.getDepartments());
		return employeeRepository.save(employee);
	}

	public void deleteEmployee(Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found for this id :: " + id));
		employeeRepository.delete(employee);
	}

}
