<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}" />

<table>
    <tr>
        <td>Pages:</td>
        <td><a href="${appRoot}/">Home</a></td>
        <td><a href="${appRoot}/project">Projects</a></td>
        <td><a href="${appRoot}/issue-type">Issue Types</a></td>
        <td><a href="${appRoot}/issue-status">Issue Statuses</a></td>
        <td><a href="${appRoot}/issue-priority">Issue Priorities</a></td>
        <td><a href="${appRoot}/issue">Issues</a></td>
        <td><a href="${appRoot}/monitoring?h=0&w=0">Monitoring</a></td>
    </tr>
</table>
