<%--
  Created by IntelliJ IDEA.
  User: Элина
  Date: 01.11.2021
  Time: 8:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <c:forEach items="${books}" var="book">
        <t:externalBook book="${book}"/>
    </c:forEach>
</t:layout>
