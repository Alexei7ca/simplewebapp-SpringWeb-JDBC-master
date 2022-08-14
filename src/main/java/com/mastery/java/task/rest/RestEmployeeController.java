package com.mastery.java.task.rest;

import com.mastery.java.task.dao.EmployeeDAO;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.EmployeeRestDTO;
import com.mastery.java.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/rest")
public class RestEmployeeController {

    @Autowired
    EmployeeService employeeService;


    @ResponseBody
    @GetMapping("/employees/all")
    public List<Employee> getAllEmployees() {
        return this.employeeService.getAllEmployees();
    }

    @ResponseBody
    @GetMapping("/employees")
    public List<Employee> getRangeEmployees(@RequestParam Integer pFrom, @RequestParam Integer pCount) {
        int from = (pFrom != null && pFrom>0) ? pFrom : 0;  //if (pFrom != null && pFrom>0) from = pFrom;
        int count = (pCount != null && pCount>0) ? pCount : 0;
        if (count>100) count=100;
        return this.employeeService.getRangeEmployees(from, count);
    }


    @GetMapping("/employee/{employeeId}")
    public Employee getEmployeeById(@PathVariable(value="employeeId") @Min(5) int employeeId) {  //added min for validation
        return employeeService.getEmployeeById(employeeId);
    }



    @ResponseBody
    @RequestMapping(value = "employees/count", method = RequestMethod.GET)
    public Integer getEmployeeCount(){
        return employeeService.getEmployeeCount();
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
        LocalDate employeeBD = employee.getDateOfBirth();
        if (employeeBD == null) throw new IllegalArgumentException("Date of birth is NULL");

// check for age - we make the check using LocalDate, this gives us more flexibility  (it accounts for all date nuance etc etc )
        LocalDate max = LocalDate.now().minus(18, ChronoUnit.YEARS);
        LocalDate min = LocalDate.now().minus(80, ChronoUnit.YEARS);
        if (employeeBD.isBefore(min) || employeeBD.isAfter(max)) throw new IllegalArgumentException("Age out of Boundaries");


//  check for age - we find out age first then make the check, overcomplicated, not flexible, not great option
//        Duration dur = Duration.between(employeeBD, LocalDate.now());
//        int currentAge = (int) (dur.toDays() / 365);
//        if (currentAge < 18 || currentAge>80) throw new IllegalArgumentException("Age out of Boundaries");

        return this.employeeService.createEmployee(employee);
    }



    @PutMapping("/employees/{employeeId}")
    public Employee updateEmployee(@PathVariable(value = "employeeId")  int employeeId, @RequestBody Employee employee){
        LocalDate employeeBD = employee.getDateOfBirth();
        if (employeeBD == null) throw new IllegalArgumentException("Date of birth is NULL");

        LocalDate max = LocalDate.now().minus(18, ChronoUnit.YEARS);
        LocalDate min = LocalDate.now().minus(80, ChronoUnit.YEARS);
        if (employeeBD.isBefore(min) || employeeBD.isAfter(max)) throw new IllegalArgumentException("Age out of Boundaries");

        return this.employeeService.updateEmployee(employeeId,employee);
    }


    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(value = "employeeId") int employeeId) {
        this.employeeService.deleteEmployee(employeeId);
    }



    @PostMapping("/employees/DTO")
    public EmployeeRestDTO createEmployeeByDTO(@Valid @RequestBody EmployeeRestDTO employeeDTO) {
        int age = employeeDTO.getAge();
        if (age > 80 || age < 18) throw new IllegalArgumentException("Age out of Boundaries");
        Employee employee = new Employee();

        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setGender(employeeDTO.getGender());
        employee.setDepartmentId(employeeDTO.getDepartmentId());
        employee.setJobTitle(employeeDTO.getJobTitle());
        
        LocalDate employeeBD = LocalDate.now().minus(age, ChronoUnit.YEARS);
        employee.setDateOfBirth(employeeBD);
        
        Employee employeeResult = employeeService.createEmployee(employee);
        EmployeeRestDTO result = new EmployeeRestDTO();

        result.setFirstName(employeeResult.getFirstName());
        result.setLastName(employeeResult.getLastName());
        result.setGender(employeeResult.getGender());
        result.setDepartmentId(employeeResult.getDepartmentId());
        result.setJobTitle(employeeResult.getJobTitle());

//        Duration dur = Duration.between(employeeResult.getDateOfBirth(), LocalDate.now());
        int currentAge = (int) ChronoUnit.YEARS.between(employeeResult.getDateOfBirth(), LocalDate.now());
        result.setAge(currentAge);
        
        return result;

    }

}




