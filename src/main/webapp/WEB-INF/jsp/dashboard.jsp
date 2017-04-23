<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <title>Sunny Tracker</title>

    <link rel="stylesheet" href="/styles/dashboard.css" />

    <script src="/scripts/frame-utils.js"></script>
    <script src="/scripts/issue-utils.js"></script>
    <script src="/scripts/jquery.js"></script>
</head>
<body>

    <jsp:include page="header.jsp" />

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
                <div class="issuetype">${issueTypeStructure.issueType.name}s &nbsp;&nbsp;&nbsp;
                    <a href="#" onclick="IssueUtils.createRootIssue(${structure.project.id},${issueTypeStructure.issueType.id});">(+)</a>
                </div><br />
                <%-- Issues --%>
                <t:tree indent="60" items="${issueTypeStructure.issues}" />
            </c:forEach>
        </c:forEach>
    </div>
</body>

</html>
