package com.chris.services;

import java.util.List;

import org.springframework.stereotype.Service;


import com.chris.entities.Employee;

@Service
public interface EmployeeService {
	
    List<Employee> listarEmpleados();
    
    public Employee findById(Long id);
    
    public Employee save(Employee employee);
    
    public Employee update(Employee employee);
    
    void delete(Long employeeId);
}


