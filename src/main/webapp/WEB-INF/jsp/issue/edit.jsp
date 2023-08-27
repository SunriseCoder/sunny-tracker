<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="custom" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Issue</title>

    <script src="${appRoot}/scripts/jquery.js"></script>
    <script src="${appRoot}/scripts/issue-utils.js"></script>

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
<body onload="IssueUtils.parentChanged();">

    <%-- Validation errors --%>
    <c:if test="${not empty errors}">
        <form:form commandName="issue">
            <form:errors path="*" cssClass="error"></form:errors>
        </form:form>
    </c:if>

    <%-- Saving error --%>
    <c:if test="${not empty error}">
        <span class="error">${error}</span>
    </c:if>

    <%-- Success message --%>
    <c:if test="${not empty message}">
        <span class="success">${message}</span>
        <script>IssueUtils.refreshDashboard();</script>
    </c:if>

    <jsp:include page="../includes/issue-form.jsp" />
    <input type="button" value="Save" onclick="IssueUtils.submitForm('issueForm');" />
    <input type="button" value="Delete" onclick="IssueUtils.deleteIssueWithConfirmation('${appRoot}/rest/issue/delete', ${issue.id});" />
</body>
</html>
