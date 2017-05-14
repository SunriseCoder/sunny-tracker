<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="${appRoot}/styles/dashboard.css" />

    <script src="${appRoot}/scripts/frame-utils.js"></script>
    <script src="${appRoot}/scripts/issue-utils.js"></script>
    <script src="${appRoot}/scripts/jquery.js"></script>

    <script>
        IssueUtils.setIssueMoveUrl("${appRoot}/rest/issue/move");
    </script>
</head>
<body>

    <spring:eval var="env" expression="@environment.getProperty('environment')" />

    <h1>Welcome to Sunny Tracker
        <c:if test="${not empty env}">
            (${env})
        </c:if>
    </h1>

    <div class="table">
        <%-- Projects --%>
        <c:forEach var="structure" items="${structures}">
            <div class="project">${structure.project.name}</div><br />
            <%-- Issue Types --%>
            <c:forEach var="issueTypeStructure" items="${structure.issueTypeStructures}">
                <div class="issuetype">${issueTypeStructure.issueType.name} &nbsp;&nbsp;&nbsp;
                    <!-- Create Root Issue -->
                    <a class="link" onclick="FrameUtils.showUrl('${appRoot}/issue/create/${structure.project.id}/${issueTypeStructure.issueType.id}');">(+)</a>
                </div>
                <br />
                <%-- Issues --%>
                <t:tree indent="60" items="${issueTypeStructure.issues}" />
            </c:forEach>
        </c:forEach>
    </div>
</body>

</html>
