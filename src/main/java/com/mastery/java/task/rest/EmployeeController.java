package com.mastery.java.task.rest;

import com.mastery.java.task.dao.EmployeeDAO;
import com.mastery.java.task.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;



@Controller
public class EmployeeController {
    @Autowired
    DataSource dataSource;
    @Autowired
    EmployeeDAO employeeDAO;


    /**
     * Тестируем работу Spring MVC
     */
    @RequestMapping(value = "welcome", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        model.addAttribute("message", "Spring 5 MVC: everything works great. v5");
        return "hello";   //Дальше запрос будет обработан hello.jsp
    }

    /**
     * Тестируем работу JDBC и Базы данных в памяти (H2)
     */
    @RequestMapping(value = "database", method = RequestMethod.GET)
    public String testDatabse(ModelMap model) throws Exception {

        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT 2+2*2");
        results.first();
        int total = results.getInt(1);

        model.addAttribute("message", "SELECT 2+2*2 == " + total);
        return "hello";   //Дальше запрос будет обработан hello.jsp
    }

    /**
     * Тестируем работу Spring Template: получаем количество строк в таблице employee
     */
    @RequestMapping(value = "employees/count", method = RequestMethod.GET)
    public String getEmployeeCount(ModelMap model) throws Exception {
        int count = employeeDAO.getItemsCount();
        model.addAttribute("message", "SELECT COUNT(*) FROM employee => " + count);
        return "hello";   //Дальше запрос будет обработан hello.jsp
    }

    /**
     * Тестируем работу Spring Template: получаем объекты employee из таблицы employee
     */
    @RequestMapping(value = "employees", method = RequestMethod.GET)
    public String getEmployees(ModelMap model) throws Exception {

        var list = employeeDAO.getItems();

        String message = list.stream().map(employee -> employee.getFirstName() + ", ").collect(Collectors.joining());

        model.addAttribute("message", "SELECT * FROM employee => " + message);
        return "hello";   //Дальше запрос будет обработан hello.jsp
    }

    //seems to be working alright, still have to add buttons
    @RequestMapping(value = "viewEmployees", method = RequestMethod.GET)
    public String viewEmployee(Model model){
        List<Employee> list = employeeDAO.getItems();
        model.addAttribute("list",list);
        return "viewEmployees";
    }

    //this one redirects you to the form to fill up for new employee
    @RequestMapping("addNewEmployee")
    public String showForm(Model model){
        model.addAttribute("command", new Employee());
        return "addNewEmployee";
    }

    //this one should save the new employee
    //HTTP Status 400 – Bad Request - Description -
    // The server cannot or will not process the request due to something that is perceived to be a client error
    // (e.g., malformed request syntax, invalid request message framing, or deceptive request routing).
    @RequestMapping(value="/save",method = RequestMethod.POST)
    public String save(@ModelAttribute("employee") Employee employee){
        employeeDAO.updateEmployee(employee);
        return "redirect:/test/viewEmployees";
    }

   //seems fine, not saving tho, gotta check the "editSave" method
    @RequestMapping(value="/editEmployee/{employeeId}")
    public String edit(@PathVariable int employeeId, Model model){
        Employee employee = employeeDAO.getItemById(employeeId);
        model.addAttribute("command",employee);
        return "editEmployee";
    }

    @RequestMapping(value="/editSave",method = RequestMethod.POST)
    public String editSave(@ModelAttribute("employee") Employee employee){
        employeeDAO.updateEmployee(employee);
        return "redirect:/test/viewEmployees";
    }

    @RequestMapping(value="/deleteEmployee/{employeeId}",method = RequestMethod.GET) //works.
    public String delete(@PathVariable int employeeId){
        employeeDAO.deleteEmployee(employeeId);
        return "redirect:/test/viewEmployees";
    }
}
