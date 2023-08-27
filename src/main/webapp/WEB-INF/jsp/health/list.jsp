<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<%@ taglib uri="/WEB-INF/tld/formatting.tld" prefix="formatting" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Health Check</title>
</head>
<body>

    <jsp:include page="../includes/header.jsp" />

    <h1>Health Check</h1>

    <table>
        <tbody>
            <c:forEach var="drive" items="${drives}">
                <tr>
                    <td>
                        ${drive.name} - Free space:
                        <formatting:human-readable-size value="${drive.freeSpaceSize}" />
                        of
                        <formatting:human-readable-size value="${drive.totalSpaceSize}" />
                        (<fmt:formatNumber type="percent" pattern="0.##%"
                                           value="${drive.freeSpaceSize / drive.totalSpaceSize}" />)
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</body>
</html>
