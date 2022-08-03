<%--
  Created by IntelliJ IDEA.
  User: alexei
  Date: 29.07.2022
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Employees List</h1>
<table border="2" width="70%" cellpadding="2">
    <tr><th>employeeId</th><th>firstName</th><th>lastName</th><th>Gender</th><th>departmentId</th><th>jobTitle</th><th>dateOfBirth</th><th>Edit</th><th>Delete</th></tr>
    <c:forEach var="employee" items="${list}">
        <tr>
            <td>${employee.employeeId}</td>
            <td>${employee.firstName}</td>
            <td>${employee.lastName}</td>
            <td>${employee.gender}</td>
            <td>${employee.departmentId}</td>
            <td>${employee.jobTitle}</td>
            <td>${employee.dateOfBirth}</td>
         <td><a href="editEmployee/${employee.employeeId}">Edit</a></td>   <%--to do--%>
         <td><a href="deleteEmployee/${employee.employeeId}">Delete</a></td>  <%--to do--%>
        </tr>
    </c:forEach>
</table>
<br/>
<a href="http://localhost:8080/test/addNewEmployee">Add Employee</a>  <%--to do--%>
