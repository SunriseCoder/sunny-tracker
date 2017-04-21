<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <title>Sunny Tracker</title>

    <script src="/scripts/issue-utils.js"></script>
    <script src="/scripts/jquery.js"></script>

    <style>
        .error {
            color: red;
            font-weight: bold;
        }

        .success {
            color: green;
            font-weight: bold;
        }
    </style>
</head>
<body onload="IssueUtils.refreshDashboard();">

    <%-- Validation errors --%>
    <c:if test="${not empty errors}">
        <form:form commandName="issue">
            <form:errors path="*" cssClass="error"></form:errors>
        </form:form>
    </c:if>

    <%-- Saving error --%>
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <%-- Success message --%>
    <c:if test="${not empty message}">
        <p class="success">${message}</p>
    </c:if>

</body>

</html>
