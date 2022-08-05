<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Edit Employee</h1>
<form:form method="POST" action="/test/editSave">
    <table >
        <tr>
            <td></td>
            <td><form:hidden  path="employeeId" /></td>
        </tr>
        <tr>
            <td>firstName : </td>
            <td><form:input path="firstName"  /></td>
        </tr>
        <tr>
            <td>lastName : </td>
            <td><form:input path="lastName"  /></td>
        </tr>
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
            <td><input type="submit" value="Edit Save" /></td>
        </tr>
    </table>
</form:form>