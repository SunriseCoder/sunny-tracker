<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="custom" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Create Issue</title>
<script>
function parentChanged() {
    var par = document.getElementById('parent.id').value;
    var vis = par == 0 ? '' : 'hidden';
    document.getElementById('project.id').style.visibility = vis;
    document.getElementById('type.id').style.visibility = vis;
}
</script>
</head>
<body onload="parentChanged();">
    <h1>Create Issue</h1>
    <a href="${pageContext.request.contextPath}/">Home page</a><br />
    <form:form method="POST" commandName="issue" action="${pageContext.request.contextPath}/issue/create">
        <table>
            <tbody>
                <tr>
                    <td>Name:</td>
                    <td><form:input path="name" /></td>
                    <td><form:errors path="name" cssStyle="color: red;" /></td>
                </tr>
                <tr>
                    <td>Description:</td>
                    <td><form:textarea path="description" rows="20" cols="100" /></td>
                    <td><form:errors path="description" cssStyle="color: red;" /></td>
                </tr>
                <tr>
                    <td>Parent issue:</td>
                    <td>
                        <form:select path="parent.id" multiple="false" onchange="parentChanged()">
                            <custom:recursive-options rootItems="${rootIssues}" propertyOfChildren="childs" itemValue="id"
                                    itemLabelExpression="name + ' - (' + (status == null ? 'null' : status.name) + ')'" indentString="---- " />
                        </form:select>
                    </td>
                    <td><form:errors path="parent.id" cssStyle="color: red;" /></td>
                </tr>
                <tr>
                    <td>Project:</td>
                    <td><form:select path="project.id" items="${projects}" itemLabel="name" multiple="false" itemValue="id" /></td>
                    <td><form:errors path="project.id" cssStyle="color: red;" /></td>
                </tr>
                <tr>
                    <td>Issue Type:</td>
                    <td><form:select path="type.id" items="${issueTypes}" itemLabel="name" multiple="false" itemValue="id" /></td>
                    <td><form:errors path="type.id" cssStyle="color: red;" /></td>
                </tr>
                <tr>
                    <td>Issue Status:</td>
                    <td><form:select path="status.id" items="${issueStatuses}" itemLabel="name" multiple="false" itemValue="id" /></td>
                    <td><form:errors path="status.id" cssStyle="color: red;" /></td>
                </tr>
                <tr>
                    <td>Issue Priority:</td>
                    <td><form:select path="priority.id" items="${issuePriorities}" itemLabel="name" multiple="false" itemValue="id" /></td>
                    <td><form:errors path="priority.id" cssStyle="color: red;" /></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Save" /></td>
                </tr>
            </tbody>
        </table>
    </form:form>
    <a href="${pageContext.request.contextPath}/">Home page</a>
</body>
</html>
