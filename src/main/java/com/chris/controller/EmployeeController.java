package com.chris.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chris.entities.Employee;
import com.chris.services.EmployeeService;

@RestController
@RequestMapping("/rest")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	//listar employee
	@GetMapping("/empleados")
	public List<Employee> listarEmpleados(){
		return employeeService.listarEmpleados();
	}
	
	//obtener employee por id
	@GetMapping("/empleados/{id}")
	public ResponseEntity<Employee> obtenerEmpleadoXId(@PathVariable("id") Long id){
		
		Employee empleado = employeeService.findById(id);
		return ResponseEntity.ok(empleado);
	}
	
	//crear employee
	@PostMapping("/empleados")
	public ResponseEntity<Employee> crearEmpleado(@RequestBody Employee employee){
		
		Employee nuevoEmpleado = employeeService.save(employee);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
	}
	
	//editar employee
	@PutMapping("/empleados/{id}")
	public ResponseEntity<Employee> actualizarEmpleado(@PathVariable("id") Long id,
				@RequestBody Employee employee){
		
		Employee empleadoExistente = employeeService.findById(id);
		
		empleadoExistente.setFirstName(employee.getFirstName());
        empleadoExistente.setLastName(employee.getLastName());
        empleadoExistente.setEmail(employee.getEmail());
        empleadoExistente.setPhoneNumber(employee.getPhoneNumber());
        empleadoExistente.setHireDate(employee.getHireDate());
        empleadoExistente.setJobId(employee.getJobId());
        empleadoExistente.setSalary(employee.getSalary());
        empleadoExistente.setCommission_pct(employee.getCommission_pct());
        empleadoExistente.setManagerId(employee.getManagerId());
        empleadoExistente.setDepartmentId(employee.getDepartmentId());
        
        Employee empleadoActualizado = employeeService.update(empleadoExistente);
		
		return ResponseEntity.ok(empleadoActualizado);
	}
	
	//eliminar employee
	@DeleteMapping("/empleados/{id}")
	public ResponseEntity<Void> eliminarEmpleado(@PathVariable("id") Long id){
		
		employeeService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
	

}
