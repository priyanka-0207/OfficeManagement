package com.task.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.model.Department;
import com.task.service.DepartmentService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private DepartmentService departmentService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void createDepartmentTest() throws Exception {
		Department department = new Department();
		department.setName("IT");

		mockMvc.perform(post("/departments").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(department))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("IT"));
	}

	@Test
	public void getAllDepartmentsTest() throws Exception {
		mockMvc.perform(get("/departments")).andExpect(status().isOk());
	}

	@Test
	public void getDepartmentByIdTest() throws Exception {
		mockMvc.perform(get("/departments/{id}", 7)).andExpect(status().isOk());
	}

	@Test
	public void updateDepartmentTest() throws Exception {
		Department department = new Department();
		department.setName("HR");

		mockMvc.perform(put("/departments/{id}", 7).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(department))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("HR"));
	}

	@Test
	public void getEmployeeCountPerDepartmentTest() throws Exception {
		mockMvc.perform(get("/departments/employee-count")).andExpect(status().isOk());
	}
}
