<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="custom" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}" />

<form:form id="issueForm" method="POST" commandName="issue" action="${appRoot}/issue/save">
    <table>
        <tbody>
            <tr>
                <td>Id:</td>
                <td><form:input path="id" /></td>
                <td><form:errors path="id" cssStyle="color: red;" /></td>
            </tr>
            <tr>
                <td>Name:</td>
                <td><form:input path="name" size="50" /></td>
                <td><form:errors path="name" cssStyle="color: red;" /></td>
            </tr>
            <tr>
                <td>Description:</td>
                <td><form:textarea path="description" rows="7" cols="50" /></td>
                <td><form:errors path="description" cssStyle="color: red;" /></td>
            </tr>
            <tr>
                <td>Parent:</td>
                <td>
                    <form:select path="parent.id" multiple="false" onchange="IssueUtils.parentChanged();">
                        <custom:recursive-options rootItems="${rootIssues}" propertyOfChildren="childs" itemValue="id"
                                itemLabelExpression="name + ' - (' + (status == null ? 'null' : status.name) + ')'" indentString="---- " />
                    </form:select>
                </td>
                <td><form:errors path="parent.id" cssStyle="color: red;" /></td>
            </tr>
            <tr>
                <td>Project:</td>
                <td><form:select path="project.id" items="${projects}" itemLabel="name" multiple="false" itemValue="id" /></td>
                <td><form:errors path="project.id" cssStyle="color: red;" /></td>
            </tr>
            <tr>
                <td>Type:</td>
                <td>
                    <span id="type.id">
                        <form:radiobuttons path="type.id" items="${issueTypes}" itemLabel="name" itemValue="id" />
                    </span>
                </td>
                <td><form:errors path="type.id" cssStyle="color: red;" /></td>
            </tr>
            <tr>
                <td>Status:</td>
                <td>
                    <span id="status.id">
                        <form:radiobuttons path="status.id" items="${issueStatuses}" itemLabel="name" itemValue="id" />
                    </span>
                </td>
                <td><form:errors path="status.id" cssStyle="color: red;" /></td>
            </tr>
            <tr>
                <td>Priority:</td>
                <td>
                    <span id="priority.id">
                        <form:radiobuttons path="priority.id" items="${issuePriorities}" itemLabel="name" itemValue="id" />
                    </span>
                </td>
                <td><form:errors path="priority.id" cssStyle="color: red;" /></td>
            </tr>
            <tr>
                <td>Position:</td>
                <td><form:input path="position" /></td>
                <td><form:errors path="position" cssStyle="color: red;" /></td>
            </tr>
        </tbody>
    </table>
</form:form>
