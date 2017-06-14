<%@ page import="tracker.entity.Issue"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>

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
                <%
                    String h = request.getParameter("h");
                    String w = request.getParameter("w");
                    int total = ((Issue) pageContext.getAttribute("issue")).getStatistic().getTotal();
                    if (h == null && total > 100) {
                        int cellHeight = 500 / total;
                        if (cellHeight < 2) {
                            cellHeight = 2;
                        }
                        h = String.valueOf(cellHeight);
                    }
                    String hw = "";
                    hw += h == null && w == null ? "" : "?";
                    hw += h == null ? "" : "h=" + h;
                    hw += w == null ? "" : h == null ? "" : "&";
                    hw += w == null ? "" : "w=" + w;
                    pageContext.setAttribute("hw", hw);
                %>
                <tr>
                    <td>
                        ${issue.id}: ${issue.name} - 
                        Completed ${issue.statistic.completed} of ${issue.statistic.total} 
                        (<fmt:formatNumber type="percent" pattern="0.##%"
                                           value="${issue.statistic.completed / issue.statistic.total}" />)<br />
                        <img src="${appRoot}/monitoring/histogram/${issue.id}${hw}" alt="Histogram" />
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</body>
</html>