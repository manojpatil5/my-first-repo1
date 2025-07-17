package com.SeyedMohammad.EmployeeManagementSystem;

import com.SeyedMohammad.EmployeeManagementSystem.model.Employee;
import com.SeyedMohammad.EmployeeManagementSystem.repository.EmployeeRepository;
import com.SeyedMohammad.EmployeeManagementSystem.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @InjectMocks
    EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
    }

    @Test
    public void testGetAllEmployees() {
        // Arrange: Prepare a list of employees
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        // Mock behavior: When employeeRepository.findAll() is called, return the 'employees' list
        when(employeeRepository.findAll()).thenReturn(employees);

        // Act: Call the service method
        List<Employee> result = employeeService.getAllEmployees();

        // Assert: Verify the service method returns the expected data
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());

        // Verify: Ensure employeeRepository.findAll() was called exactly once
        verify(employeeRepository, times(1)).findAll();
    }


    @Test
    public void testGetEmployeeById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        Optional<Employee> result = employeeService.getEmployeeById(1L);
        assertTrue(result.isPresent());
        assertEquals(employee.getFirstName(), result.get().getFirstName());

    }
    @Test
    public void testSaveEmployee() {
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.saveEmployee(employee);
        assertNotNull(result);
        assertEquals(employee.getFirstName(), result.getFirstName());
    }

    @Test
    public void testDeleteEmployee() {
        doNothing().when(employeeRepository).deleteById(1L);

        employeeService.deleteEmployee(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }
}
