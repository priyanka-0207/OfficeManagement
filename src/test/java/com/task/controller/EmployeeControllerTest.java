package com.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.model.Employee;
import com.task.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	@Autowired
	private ObjectMapper objectMapper;

	private Employee employee;

	@BeforeEach
	void setUp() {
		employee = new Employee();
		employee.setId(1L);
		employee.setName("Test Name");
	}

	@Test
	void testCreateEmployee() throws Exception {
		given(employeeService.saveEmployee(employee)).willReturn(employee);

		mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employee))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(employee.getName()));
	}

	@Test
	void testGetEmployeeById() throws Exception {
		given(employeeService.getEmployeeById(1L)).willReturn(Optional.of(employee));
		mockMvc.perform(get("/employees/{id}", 1L)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(employee.getName()));
	}

	@Test
	void testGetAllEmployees() throws Exception {
		given(employeeService.getAllEmployees()).willReturn(Arrays.asList(employee));
		mockMvc.perform(get("/employees")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value(employee.getName()));
	}

	@Test
	void testUpdateEmployee() throws Exception {
		given(employeeService.updateEmployee(1L, employee)).willReturn(employee);
		mockMvc.perform(put("/employees/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employee))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(employee.getName()));
	}

	@Test
	void testDeleteEmployee() throws Exception {
		mockMvc.perform(delete("/employees/{id}", 1L)).andExpect(status().isOk());
	}
}
