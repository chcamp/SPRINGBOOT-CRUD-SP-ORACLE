package com.chris.dao;

import org.springframework.data.repository.CrudRepository;

import com.chris.entities.Employee;

public interface EmployeeRepository  extends CrudRepository<Employee, Long>{

}
