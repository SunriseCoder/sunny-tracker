<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Issue Priority List</title>
</head>
<body>
    <h1>Issue Priority List</h1>
    <a href="${pageContext.request.contextPath}/">Home</a><br />

    <c:if test="${not empty errors}">
        <c:out value="Following errors has been occured due to creation issue priority ${name}:" />
        <c:forEach var="error" items="${errors}">
            <font color="red">${error}</font>
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
                <th width="50px">position</th>
                <th width="50px">issue position</th>
                <th width="50px">default</th>
                <th width="50px">actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="issuePriority" items="${issuePriorityList}">
                <tr>
                    <td>${issuePriority.id}</td>
                    <td>${issuePriority.name}</td>
                    <td>${issuePriority.position}</td>
                    <td>${issuePriority.issuePosition}</td>
                    <td>${issuePriority.selected}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/issue-priority/edit/${issuePriority.id}.html">Edit</a><br />
                        <a href="${pageContext.request.contextPath}/issue-priority/delete/${issuePriority.id}.html">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/">Home</a>
    <h1>Create new Issue Priority</h1>
    <form:form method="POST" commandName="issuePriority" action="${pageContext.request.contextPath}/issue-priority/create">
        Issue Priority name: <form:input path="name" /><form:errors path="name" cssStyle="color: red;" /><br />
        Position in the List: <form:input path="position" /><form:errors path="position" cssStyle="color: red;" /><br />
        Issue Position: <form:input path="issuePosition" /><form:errors path="issuePosition" cssStyle="color: red;" /><br />
        <input type="submit" value="Create" />
    </form:form>
</body>
</html>
