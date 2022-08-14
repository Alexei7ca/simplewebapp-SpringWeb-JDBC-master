package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDAO;
import com.mastery.java.task.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


//Controller calls Service, service calls DAO
//DAO save in database
//Controller convert User to Employee
//Service do smth with objects

@Service
public class EmployeeService {

    @Autowired
    EmployeeDAO employeeDAO;


    public List<Employee> getAllEmployees() {
        return this.employeeDAO.getItems();
    }

    public Employee getEmployeeById( int employeeId) {
        return employeeDAO.getItemById(employeeId);
    }

    public List<Employee> getRangeEmployees(int from, int count) {
        return this.employeeDAO.getItems(from, count);
    }


    public Integer getEmployeeCount(){
        return employeeDAO.getItemsCount();
    }


    public Employee createEmployee(Employee employee) {
        return this.employeeDAO.addEmployee(employee);
    }


    public Employee updateEmployee(int employeeId, Employee employee){
        Employee currentEmployee = employeeDAO.getItemById(employeeId);
        currentEmployee.setFirstName(employee.getFirstName());
        currentEmployee.setLastName(employee.getLastName());
        currentEmployee.setGender(employee.getGender());
        currentEmployee.setDepartmentId(employee.getDepartmentId());
        currentEmployee.setJobTitle(employee.getJobTitle());
        currentEmployee.setDateOfBirth(employee.getDateOfBirth());
        return this.employeeDAO.updateEmployee(currentEmployee);
    }

    public void deleteEmployee(int employeeId) {
        this.employeeDAO.deleteEmployee(employeeId);
    }




}
