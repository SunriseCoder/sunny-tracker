<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Issue List</title>
</head>
<body>

    <jsp:include page="../includes/header.jsp" />

    <h1>Issue Monitoring</h1>

    <table>
        <tbody>
            <c:forEach var="issue" items="${issueList}">
                <tr>
                    <td>
                        ${issue.id}: ${issue.name}<br />
                        <img src="${appRoot}/monitoring/histogram/${issue.id}" alt="Histogram" />
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</body>
</html>
