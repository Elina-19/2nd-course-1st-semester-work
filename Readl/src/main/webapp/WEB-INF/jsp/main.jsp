<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <c:if test="${message != null}">
        <div>${message}</div>
    </c:if>
    <c:forEach items="${books}" var="book">
        <t:externalBook book="${book}"/>
    </c:forEach>
</t:layout>
