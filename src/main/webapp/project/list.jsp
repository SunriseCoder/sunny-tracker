<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Project List</title>
</head>
<body>
	<h1>Project List</h1>
	<a href="${pageContext.request.contextPath}/">Home</a><br />

	<c:if test="${not empty errors}">
		<c:out value="Following errors has been occured due to creation project ${name}:" />
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
				<th width="50px">actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="project" items="${projectList}">
				<tr>
					<td>${project.id}</td>
					<td>${project.name}</td>
					<td>${project.position}</td>
					<td>
						<a href="${pageContext.request.contextPath}/project/edit/${project.id}.html">Edit</a><br />
						<a href="${pageContext.request.contextPath}/project/delete/${project.id}.html">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="${pageContext.request.contextPath}/">Home</a>
	<h1>Create new Project</h1>
	<form:form method="POST" commandName="project" action="${pageContext.request.contextPath}/project/create">
		Project name: <form:input path="name" /><form:errors path="name" cssStyle="color: red;" /><br />
		Position in list: <form:input path="position" /><form:errors path="position" cssStyle="color: red;" /><br />
		<input type="submit" value="Create" />
	</form:form>
</body>
</html>
