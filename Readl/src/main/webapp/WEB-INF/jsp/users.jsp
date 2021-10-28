<%--
  Created by IntelliJ IDEA.
  User: Элина
  Date: 22.10.2021
  Time: 20:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <div>
        <table border="1">
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.getEmail()}</td>
                </tr>
            </c:forEach>
        </table>
        <t:comment commentObject="${comment}"/>
        <t:externalBook book="${book}"/>
        </body>
    </div>
</t:layout>
