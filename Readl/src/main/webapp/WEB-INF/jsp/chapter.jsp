<%--
  Created by IntelliJ IDEA.
  User: Элина
  Date: 02.11.2021
  Time: 21:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <c:if test="${message != null}">
        <h1 class="message">${message}</h1>
    </c:if>
    <c:if test="${message == null}">
        <div>
            ${chapter.content}
        </div>
    </c:if>
</t:layout>