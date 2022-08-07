package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
        //"INSERT INTO schema.tableName (column1, column2) VALUES (?, ?)",
        //    var1, var2
//        String sql="insert into employee(first_name, last_name, gender, department_id, job_title, date_of_birth) values('"+employee.getFirstName()+"','"+employee.getLastName()+"',"+employee.getGender()+","+employee.getDepartmentId()+","+employee.getJobTitle()+",'"+employee.getDateOfBirth()+"')";
        String sql="insert into employee(first_name, last_name, gender, department_id, job_title, date_of_birth) values(?,?,?::gender,?,?,?)" ; //prepareStatement("insert into customers values (?,?,?,?,?,?::gender,?,?,?,?,?)");
        Object[] params = new Object[]{
                employee.getFirstName(),employee.getLastName(),employee.getGender().toString(),employee.getDepartmentId(),employee.getJobTitle(),employee.getDateOfBirth() //we're casting employee.getGender().toString()
        };
        int[] types = new int[]{
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,//column &quot;gender&quot; is of type gender but expression is of type character varying</p> //fixed by (?,?::gender,?,?,?,?,?) + casting employee.getGender().toString()
                Types.INTEGER,
                Types.VARCHAR,
                Types.DATE
        };
        template.update(sql,params,types);
        String newEmployeeId ="SELECT MAX(employeeId) FROM employee";
        int id = template.queryForObject(newEmployeeId, Integer.class);
        return getItemById(id);
    }

    public Employee updateEmployee(Employee employee) {
        //working.
//        String sql="update employee set first_name='"+employee.getFirstName()+"',last_name='"+employee.getLastName()+"',gender='"+employee.getGender()+"',department_id='"+employee.getDepartmentId()+"', job_title='"+employee.getJobTitle()+"',date_of_birth='"+employee.getDateOfBirth()+"' where employeeId="+employee.getEmployeeId()+"";
        String sql="update employee set first_name=?,last_name=?,gender=?::gender,department_id=?, job_title=?,date_of_birth=? where employeeId=?";

        Object[] params = new Object[]{
                employee.getFirstName(),employee.getLastName(),employee.getGender().toString(),employee.getDepartmentId(),employee.getJobTitle(),employee.getDateOfBirth(),employee.getEmployeeId() //we're casting employee.getGender().toString()
        };
        int[] types = new int[]{
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,//column &quot;gender&quot; is of type gender but expression is of type character varying</p> //fixed by (?,?::gender,?,?,?,?,?) + casting employee.getGender().toString()
                Types.INTEGER,
                Types.VARCHAR,
                Types.DATE,
                Types.INTEGER
        };

         template.update(sql,params,types);
        return employee;
    }

    public void deleteEmployee(int employeeId) {
        //working.
        String sql="delete from employee where employeeId=?";
        template.update(sql,employeeId);
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


