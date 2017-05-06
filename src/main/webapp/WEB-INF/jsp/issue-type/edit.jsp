<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Edit Issue Type</title>
</head>
<body>
	<h1>Edit Issue Type</h1>
	<a href="${pageContext.request.contextPath}/">Home page</a><br />
	<form:form method="POST" commandName="issueType"
		action="${pageContext.request.contextPath}/issue-type/save/${issueType.id}">
		<table>
			<tbody>
				<tr>
					<td>Issue Type name:</td>
					<td><form:input path="name" /></td>
					<td><form:errors path="name" cssStyle="color: red;" /></td>
				</tr>
				<tr>
					<td>Position in list:</td>
					<td><form:input path="position" /></td>
					<td><form:errors path="position" cssStyle="color: red;" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="Save" /></td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</form:form>
	<a href="${pageContext.request.contextPath}/">Home page</a>
</body>
</html>
