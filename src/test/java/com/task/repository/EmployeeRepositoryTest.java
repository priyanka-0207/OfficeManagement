package com.task.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.task.dao.EmployeeRepository;
import com.task.model.Department;
import com.task.model.Employee;
import com.task.service.EmployeeService;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeRepositoryTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private List<Employee> employeeList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(5L, "Priyanka", new ArrayList<Department>()));
        employeeList.add(new Employee(7L, "Sarvesh", new ArrayList<Department>()));
    }

    @Test
    public void testGetEmployeeById() {
        Long id = 5L;
        given(employeeRepository.findByIdWithDepartments(id)).willReturn(Optional.empty());
        Optional<Employee> result = employeeService.getEmployeeById(id);
        verify(employeeRepository, times(1)).findByIdWithDepartments(id);
        assertThat(result).isEmpty();
    }


}
