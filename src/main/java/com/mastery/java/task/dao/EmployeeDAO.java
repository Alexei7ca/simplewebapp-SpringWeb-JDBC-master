package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class EmployeeDAO {

    @Autowired
    JdbcTemplate template;

    //not sure if needed here, recheck later
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }


    public int getItemsCount() {
        int count = template.queryForObject("SELECT COUNT(*) FROM employee;", Integer.class);
        return count;
    }

    public List<Employee> getItems() {
        String query = "SELECT * FROM employee";
        List<Employee> results = template.query(query, new EmployeeRowMapper());
        return results;
    }

    public Employee getItemById(int employeeId) {
        String query = "SELECT * FROM employee WHERE employeeId = ?";
        Employee employee = template.queryForObject( query, new Object[] { employeeId }, new EmployeeRowMapper());
        return employee;
    }

    public Employee addEmployee(Employee employee) {
        //TODO: вроде как да
        String sql="insert into employee(firstName, lastName, gender, departmentId, jobTitle, dateOfBirth) values('"+employee.getFirstName()+"',"+employee.getLastName()+","+employee.getGender()+","+employee.getDepartmentId()+","+employee.getJobTitle()+",'"+employee.getDateOfBirth()+"')";
        template.update(sql);
        String newEmployeeId ="SELECT MAX(employeeId) FROM employee";
        int id = template.queryForObject(newEmployeeId, Integer.class);
        return getItemById(id);
    }

    public Employee updateEmployee(Employee employee) {
        //TODO: remains to be seen
        String sql="update employee set firstName='"+employee.getFirstName()+"',lastName='"+employee.getLastName()+"',gender='"+employee.getGender()+"',departmentId='"+employee.getDepartmentId()+"', jobTitle="+employee.getJobTitle()+",dateOfBirth='"+employee.getDateOfBirth()+"' where employeeId="+employee.getEmployeeId()+"";
         template.update(sql);
        String UpdateEmployeeId ="SELECT SCOPE_IDENTITY()";
        int id = template.queryForObject(UpdateEmployeeId, Integer.class);
        return getItemById(id);
    }

    public void deleteEmployee(int employeeId) {
        //working.
        String sql="delete from employee where employeeId="+employeeId+"";
        template.update(sql);
    }


    /**
     *  С помощью этого класса мы заполняем объект Employee из объекта ResultSet
     */
    public static class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.employeeId = rs.getInt("employeeId");
            employee.setFirstName(rs.getString("first_name"));
            employee.setLastName(rs.getString("last_name"));
            employee.setGender(Gender.valueOf(rs.getString("gender")));
            employee.setDepartmentId(Integer.parseInt(rs.getString("department_id")));
            employee.setJobTitle(rs.getString("job_title"));
            employee.setDateOfBirth(LocalDate.parse(rs.getString("date_of_birth")));
            return employee;
        }
    }
}


