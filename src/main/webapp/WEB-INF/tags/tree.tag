<%@ tag description="recursive tree node" %>
<%@ attribute name="items" type="java.util.List" required="true" %>
<%@ attribute name="indent" type="java.lang.Integer" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}" />

<c:forEach var="issue" items="${items}" varStatus="count">

    <div class="issue status${fn:toLowerCase(issue.status.attentionRate)}" style="margin-left: ${indent}px">

        <div class="id">${issue.id}</div>

        <div class="name">
            <!-- Edit Issue -->
            <a class="link" onclick="FrameUtils.showUrl('${appRoot}/issue/edit/${issue.id}.html')">${issue.name}</a>
            <c:if test="${not issue.status.name.equals('Closed')}">
                <!-- Create Sub-Issue -->
                 &nbsp;&nbsp;&nbsp; <a class="link" onclick="FrameUtils.showUrl('${appRoot}/issue/create/${issue.project.id}/${issue.type.id}/${issue.id}');">(+)</a>
            </c:if>
        </div>

        <div class="move">
            <c:if test="${count.index != 0}">
                <a href="#" onclick="IssueUtils.moveIssue(${issue.id},'UP')">&uarr;</a>
            </c:if>
        </div>

        <div class="move">${issue.position}</div>

        <div class="move">
            <c:if test="${count.count < items.size()}">
                <a href="#" onclick="IssueUtils.moveIssue(${issue.id},'DOWN')">&darr;</a>
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
