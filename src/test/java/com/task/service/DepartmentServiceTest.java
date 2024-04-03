package com.task.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.task.dao.DepartmentRepository;
import com.task.model.Department;

@SpringBootTest
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setId(1L);
        department.setName("IT");
    }
    
    @Test
    void testSaveDepartment() {
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        Department savedDepartment = departmentService.saveDepartment(department);
        assertNotNull(savedDepartment);
        assertEquals(department.getName(), savedDepartment.getName());
    }
    
    @Test
    void testFindAll() {
        when(departmentRepository.findAll()).thenReturn(List.of(department));
        List<Department> departments = departmentService.findAll();
        assertFalse(departments.isEmpty());
        assertEquals(1, departments.size());
    }

    @Test
    void testFindById_ExistingId() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        Optional<Department> foundDepartment = departmentService.findById(1L);
        assertTrue(foundDepartment.isPresent());
        assertEquals(department.getName(), foundDepartment.get().getName());
    }

    @Test
    void testFindById_NonExistingId() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Department> foundDepartment = departmentService.findById(1L);
        assertFalse(foundDepartment.isPresent());
    }

    @Test
    void testUpdate_ExistingId() {
        Department updatedDetails = new Department();
        updatedDetails.setName("HR");
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(updatedDetails);
        Department updatedDepartment = departmentService.update(1L, updatedDetails);
        assertNotNull(updatedDepartment);
        assertEquals("HR", updatedDepartment.getName());
    }

    @Test
    void testUpdate_NonExistingId() {
        Department updatedDetails = new Department();
        updatedDetails.setName("HR");
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            departmentService.update(1L, updatedDetails);
        });
        String expectedMessage = "Department not found for this id :: 1";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDelete_ExistingId() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        doNothing().when(departmentRepository).delete(any(Department.class));
        assertDoesNotThrow(() -> departmentService.delete(1L));
    }

    @Test
    void testDelete_NonExistingId() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            departmentService.delete(1L);
        });
        String expectedMessage = "Department not found for this id :: 1";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    void testGetEmployeeCountPerDepartment() {
        DepartmentRepository.DepartmentEmployeeCount mockCount = new DepartmentRepository.DepartmentEmployeeCount() {
            @Override
            public Long getDepartmentId() {
                return 1L;
            }
            @Override
            public String getDepartmentName() {
                return "IT";
            }
            @Override
            public Long getEmployeeCount() {
                return 10L;
            }
        };
        when(departmentRepository.findEmployeeCountPerDepartment()).thenReturn(List.of(mockCount));
        List<DepartmentRepository.DepartmentEmployeeCount> counts = departmentService.getEmployeeCountPerDepartment();
        assertNotNull(counts);
        assertFalse(counts.isEmpty());
        assertEquals(1, counts.size());

        DepartmentRepository.DepartmentEmployeeCount retrievedCount = counts.get(0);
        assertEquals(1L, retrievedCount.getDepartmentId());
        assertEquals("IT", retrievedCount.getDepartmentName());
        assertEquals(10L, retrievedCount.getEmployeeCount());
    }

}

