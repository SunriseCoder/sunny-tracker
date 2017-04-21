<%@ tag description="recursive tree node" pageEncoding="UTF-8" %>
<%@ attribute name="items" type="java.util.List" required="true" %>
<%@ attribute name="indent" type="java.lang.Integer" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:forEach var="issue" items="${items}"    varStatus="count">
    <div class="issue ${fn:toLowerCase(issue.status.name)}" style="margin-left: ${indent}px">
        <div class="id">${issue.id}</div>
        <div class="name">
            <a href="#" onclick="IssueUtils.showIssue(${issue.id})">${issue.name}</a>
            <c:if test="${not issue.status.name.equals('Closed')}">
                 &nbsp;&nbsp;&nbsp; <a href="#"  onclick="IssueUtils.createSubIssue(${issue.project.id},${issue.type.id},${issue.id});">(+)</a>
            </c:if>
        </div>
        <div class="status">${issue.status.name}</div>
        <c:choose>
            <c:when test="${issue.status.name.equals('Closed')}">
                <div class="priority">${issue.priority.name}</div>
            </c:when>
            <c:otherwise>
                <div class="priority ${fn:toLowerCase(issue.priority.name)}">${issue.priority.name}</div>
            </c:otherwise>
        </c:choose>
    </div>
    <c:if test="${not empty issue.childs}">
        <t:tree items="${issue.childs}" indent="${indent + 20}" />
    </c:if>
</c:forEach>
