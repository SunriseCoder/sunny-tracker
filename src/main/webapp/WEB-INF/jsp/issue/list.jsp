<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Issue List</title>
</head>
<body>
    <h1>Issue List</h1>
    <a href="${appRoot}/">Home</a><br />

    <c:if test="${not empty errors}">
        <c:out value="Following errors has been occured due to creation issue ${name}:" />
        <c:forEach var="error" items="${errors}">
            <font color="red">${error}</font><br />
        </c:forEach>
    </c:if>
    <c:if test="${not empty error}">
        <font color="red">${error}</font>
    </c:if>
    <c:if test="${not empty message}">
        <font color="green">${message}</font>
    </c:if>

    <table>
        <thead>
            <tr>
                <th width="50px">id</th>
                <th width="300px">name</th>
                <th width="300px">parent</th>
                <th width="50">type</th>
                <th width="50">status</th>
                <th width="50">priority</th>
                <th width="100">project</th>
                <th width="50px">actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="issue" items="${issueList}">
                <tr>
                    <td>${issue.id}</td>
                    <td>${issue.name}</td>
                    <td>
                        <c:if test="${not empty issue.parent}">${issue.parent.name}</c:if>
                    </td>
                    <td>${issue.type.name}</td>
                    <td>${issue.status.name}</td>
                    <td>${issue.priority.name}</td>
                    <td>${issue.project.name}</td>
                    <td>
                        <a href="${appRoot}/issue/edit/${issue.id}.html">Edit</a><br />
                        <a href="${appRoot}/issue/delete/${issue.id}.html">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a href="${appRoot}/">Home</a>
    <h1>Create new Issue</h1>
    <form:form method="POST" commandName="issue" action="${appRoot}/issue/create">
        Name: <form:input path="name" /><form:errors path="name" cssStyle="color: red;" /><br />
        Description: <form:input path="description" /><form:errors path="description" cssStyle="color: red;" /><br />
        Parent issue: <form:select path="parent.id" items="${issues}" itemLabel="name" multiple="false" itemValue="id" /><form:errors path="parent.id" cssStyle="color: red;" /><br />
        Project: <form:select path="project.id" items="${projects}" itemLabel="name" multiple="false" itemValue="id" /><form:errors path="project.id" cssStyle="color: red;" /><br />
        Issue Type: <form:select path="type.id" items="${issueTypes}" itemLabel="name" multiple="false" itemValue="id" /><form:errors path="type.id" cssStyle="color: red;" /><br />
        Issue Status: <form:select path="status.id" items="${issueStatuses}" itemLabel="name" multiple="false" itemValue="id" /><form:errors path="status.id" cssStyle="color: red;" /><br />
        Issue Priority: <form:select path="priority.id" items="${issuePriorities}" itemLabel="name" multiple="false" itemValue="id" /><form:errors path="priority.id" cssStyle="color: red;" /><br />
        
        <input type="submit" value="Create" />
    </form:form>
</body>
</html>
