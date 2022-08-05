package com.mastery.java.task.rest;

import com.mastery.java.task.dao.EmployeeDAO;
import com.mastery.java.task.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RestEmployeeController {

    @Autowired
    EmployeeDAO employeeDAO;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return this.employeeDAO.getItems();
    }

    @GetMapping("/employee/{employeeId}")
    public Employee getEmployeeById(@PathVariable(value="employeeId") int employeeId) {
        return employeeDAO.getItemById(employeeId);
    }

    @RequestMapping(value = "employees/count", method = RequestMethod.GET)
    public int getEmployeeCount(){
        return employeeDAO.getItemsCount();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return this.employeeDAO.addEmployee(employee);
    }

    @PutMapping("/employees/{employeeId}")
    public Employee updateEmployee(@PathVariable(value = "employeeId") int employeeId){
        Employee employee = employeeDAO.getItemById(employeeId);
        return this.employeeDAO.updateEmployee(employee);
    }

    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(value = "employeeId") int employeeId) {
        this.employeeDAO.deleteEmployee(employeeId);
    }

}




