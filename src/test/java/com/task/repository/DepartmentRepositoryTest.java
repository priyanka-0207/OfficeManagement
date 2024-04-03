package com.task.repository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.task.dao.DepartmentRepository;
import com.task.dao.EmployeeRepository;
import com.task.model.Department;
import com.task.model.Employee;

@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentRepositoryTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@BeforeEach
	public void setup() {
		employeeRepository.deleteAll();
	    departmentRepository.deleteAll();
	    
	    Department itDepartment = new Department();
	    itDepartment.setName("IT");
	    itDepartment = departmentRepository.save(itDepartment);

	    Department hrDepartment = new Department();
	    hrDepartment.setName("HR");
	    hrDepartment = departmentRepository.save(hrDepartment);

	    Employee employee1 = new Employee();
	    employee1.setName("Priyanka");

	    employee1.setDepartments(new ArrayList<>(Arrays.asList(itDepartment, hrDepartment)));
	    employee1 = employeeRepository.save(employee1);

	    itDepartment.getEmployees().add(employee1);
	    hrDepartment.getEmployees().add(employee1);
	    departmentRepository.save(itDepartment);
	    departmentRepository.save(hrDepartment);

	    Employee employee2 = new Employee();
	    employee2.setName("Maithili");
	    employee2.setDepartments(new ArrayList<>(Collections.singletonList(itDepartment)));
	    employee2 = employeeRepository.save(employee2);

	    itDepartment.getEmployees().add(employee2);
	    departmentRepository.save(itDepartment);
	}

	@Test
	public void testFindEmployeeCountPerDepartment() throws Exception {

		mockMvc.perform(get("/departments/employee-count").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].departmentId").exists()).andExpect(jsonPath("$[0].departmentName").exists())
				.andExpect(jsonPath("$[0].employeeCount").exists());
	}
}
