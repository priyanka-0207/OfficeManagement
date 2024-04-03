package com.task.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.task.dao.EmployeeRepository;
import com.task.model.Employee;

public class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeService employeeService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAllEmployees() {
		Employee employee1 = new Employee(); 
		Employee employee2 = new Employee();
		doReturn(Arrays.asList(employee1, employee2)).when(employeeRepository).findAll();

		List<Employee> result = employeeService.getAllEmployees();

		assertEquals(2, result.size(), "getAllEmployees should return 2 employees");
	}

	@Test
	public void testGetEmployeeById() {
		Employee employee = new Employee();
		employee.setId(1L);
		doReturn(Optional.of(employee)).when(employeeRepository).findByIdWithDepartments(1L);

		Optional<Employee> result = employeeService.getEmployeeById(1L);

		assertTrue(result.isPresent(), "Employee with id 1 should be found");
		assertEquals(1L, result.get().getId(), "The id of the employee should be 1");
	}

	@Test
	public void testSaveEmployee() {
		Employee employee = new Employee();
		doReturn(employee).when(employeeRepository).save(any(Employee.class));

		Employee result = employeeService.saveEmployee(employee);

		assertEquals(employee, result, "The saved employee should be returned");
	}
	
	
}
