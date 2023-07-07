package com.chris.services;


import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.hibernate.dialect.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.chris.entities.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService{
		
	  @Autowired
	  JdbcTemplate jdbcTemplate;

	    public EmployeeServiceImpl(JdbcTemplate jdbcTemplate) {
	        this.jdbcTemplate = jdbcTemplate;
	    }

	    @Override
	    public List<Employee> listarEmpleados() {
	    	
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	            .withProcedureName("ListarEmpleados2")
	            .withoutProcedureColumnMetaDataAccess()
	            .returningResultSet("RESULT", BeanPropertyRowMapper.newInstance(Employee.class));
	        
	      
	        Map<String, Object> result = jdbcCall.execute();
	      
	        @SuppressWarnings("unchecked")
			List<Employee> employees = (List<Employee>) result.get("RESULT");
	        return employees;
	    }

		@Override
		public Employee findById(Long id) {
		
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			        .withProcedureName("ObtenerEmpleadoPorId")
			        .withoutProcedureColumnMetaDataAccess()
			        .declareParameters(
			                new SqlParameter("p_employee_id", Types.NUMERIC),
			                new SqlOutParameter("p_employee", OracleTypes.CURSOR, new BeanPropertyRowMapper<>(Employee.class))
			        );

			    SqlParameterSource inParams = new MapSqlParameterSource()
			            .addValue("p_employee_id", id);

			    Map<String, Object> result = jdbcCall.execute(inParams);

			    @SuppressWarnings("unchecked")
			    List<Employee> employees = (List<Employee>) result.get("p_employee");

			    if (employees.isEmpty()) {
			        throw new NoSuchElementException("No se encontró ningún empleado con el ID proporcionado");
			    }

			    return employees.get(0);
		}

		@Override
		public Employee save(Employee employee) {
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
		            .withProcedureName("InsertarEmpleado");
		    
		    SqlParameterSource inParams = new MapSqlParameterSource()
		            .addValue("p_employee_id", employee.getEmployeeId())
		            .addValue("p_first_name", employee.getFirstName())
		            .addValue("p_last_name", employee.getLastName())
		            .addValue("p_email", employee.getEmail())
		            .addValue("p_phone_number", employee.getPhoneNumber())
		            .addValue("p_hire_date", employee.getHireDate())
		            .addValue("p_job_id", employee.getJobId())
		            .addValue("p_salary", employee.getSalary())
		            .addValue("p_commision_pct", employee.getCommission_pct())
		            .addValue("p_manager_id", employee.getManagerId())
		            .addValue("p_department_id", employee.getDepartmentId());

		    jdbcCall.execute(inParams);

		    return employee;
		}

		@Override
		public Employee update(Employee employee) {
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			        .withProcedureName("EditarEmpleado");

			    SqlParameterSource inParams = new MapSqlParameterSource()
			        .addValue("p_employee_id", employee.getEmployeeId())
			        .addValue("p_first_name", employee.getFirstName())
			        .addValue("p_last_name", employee.getLastName())
			        .addValue("p_email", employee.getEmail())
			        .addValue("p_phone_number", employee.getPhoneNumber())
			        .addValue("p_hire_date", employee.getHireDate())
			        .addValue("p_job_id", employee.getJobId())
			        .addValue("p_salary", employee.getSalary())
			        .addValue("p_commission_pct", employee.getCommission_pct())
			        .addValue("p_manager_id", employee.getManagerId())
			        .addValue("p_department_id", employee.getDepartmentId());

			    jdbcCall.execute(inParams);

			    return employee;
		}

		@Override
		public void delete(Long employeeId) {
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			        .withProcedureName("EliminarEmpleado");

			    SqlParameterSource inParams = new MapSqlParameterSource()
			        .addValue("p_employee_id", employeeId);

			    jdbcCall.execute(inParams);
			
		}
	    
	    
}
