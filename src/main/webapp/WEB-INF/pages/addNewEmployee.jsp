<%--
  Created by IntelliJ IDEA.
  User: alexei
  Date: 31.07.2022
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Add New Employee</h1>
<form:form method="post" action="/test/save">
    <table >
        <tr>
            <td>firstName : </td>
            <td><form:input path="firstName"  /></td>
        </tr>
        <tr>
            <td>lastName : </td>
            <td><form:input path="lastName"  /></td>
        </tr>

<%--        <tr>--%>
<%--            <td>Gender (MALE/FEMALE): </td>--%>
<%--            <td><form:input path="gender"  /></td>--%>
<%--        </tr>--%>
<%--        this makes it into a selection drop-down menu--%>
        <tr>
            <td>Gender: </td>
            <td><form:select path="gender">
            <form:option value="MALE"/>
            <form:option value="FEMALE"/>
            </form:select>
            </td>
        </tr>

        <tr>
            <td>departmentId : </td>
            <td><form:input path="departmentId"  /></td>
        </tr>
        <tr>
            <td>jobTitle :</td>
            <td><form:input path="jobTitle" /></td>
        </tr>
        <tr>
            <td>dateOfBirth :</td>
            <td><form:input path="dateOfBirth" /></td>
        </tr>
        <tr>
            <td> </td>
            <td><input type="submit" value="Save" /></td>

        </tr>
    </table>
</form:form>
