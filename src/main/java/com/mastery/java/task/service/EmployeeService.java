package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDAO;
import com.mastery.java.task.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    //Controller calls Service, service calls DAO
    //DAO save in database
    //Controller convert User to Employee
    //Service do smth with objects

    @Autowired
    EmployeeDAO employeeDAO;

    public List<Employee> getAllEmployees() {
        return this.employeeDAO.getItems();
    }


}
