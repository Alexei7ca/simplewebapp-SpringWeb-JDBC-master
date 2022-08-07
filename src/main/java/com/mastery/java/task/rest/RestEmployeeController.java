package com.mastery.java.task.rest;

import com.mastery.java.task.dao.EmployeeDAO;
import com.mastery.java.task.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class RestEmployeeController {

    @Autowired
    EmployeeDAO employeeDAO;


// not working
    @ResponseBody
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return this.employeeDAO.getItems();
    }

    @GetMapping("/employee/{employeeId}")
    public Employee getEmployeeById(@PathVariable(value="employeeId") int employeeId) {
        return employeeDAO.getItemById(employeeId);
    }
//this ones working but wanna return int
    @ResponseBody
    @RequestMapping(value = "employees/count", method = RequestMethod.GET)
    public Integer getEmployeeCount(){
        return employeeDAO.getItemsCount();
    }

// this ones works too
//    @ResponseBody
//    @RequestMapping(value = "employees/count", method = RequestMethod.GET)
//    public ResponseEntity<String> getEmployeeCount(){
//        int count =  employeeDAO.getItemsCount();
//        return ResponseEntity.ok().body(String.valueOf(count));
//    }

//    @ResponseBody
//    @RequestMapping(value = "employees/count", method = RequestMethod.GET)
//    public ResponseEntity<Integer> getEmployeeCount(){
//        int count =  employeeDAO.getItemsCount();
//        return ResponseEntity.ok().body(count);
//    }


//    @PostMapping(path = "/employees",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return this.employeeDAO.addEmployee(employee);
    }

    @PutMapping("/employees/{employeeId}")
    public Employee updateEmployee(@PathVariable(value = "employeeId")  int employeeId, @RequestBody Employee employee){
        Employee currentEmployee = employeeDAO.getItemById(employeeId);
        currentEmployee.setFirstName(employee.getFirstName());
        currentEmployee.setLastName(employee.getLastName());
        currentEmployee.setGender(employee.getGender());
        currentEmployee.setDepartmentId(employee.getDepartmentId());
        currentEmployee.setJobTitle(employee.getJobTitle());
        currentEmployee.setDateOfBirth(employee.getDateOfBirth());
        return this.employeeDAO.updateEmployee(currentEmployee);
    }

    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(value = "employeeId") int employeeId) {
        this.employeeDAO.deleteEmployee(employeeId);
    }

}




