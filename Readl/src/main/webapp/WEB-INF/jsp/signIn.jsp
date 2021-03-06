<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <form method="post">
        <div class="sign-form">
            <c:if test="${error != null}">${error}<br></c:if>
            <div class="mb-3">
                <label for="email" class="form-label">Email:</label>
                <input id="email" class="form-control" type="email" name="email" placeholder="Email" <c:if test="${not empty email}"> value="<c:out value="${email}"/>"</c:if>>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password:</label>
                <input id="password" class="form-control" type="password" name="password" placeholder="Password" <c:if test="${not empty password}"> value="<c:out value="${password}"/>"</c:if>>
            </div>
            <div>
                <a href="<c:url value="/signInVk"/>">Войти через vk</a>
            </div>
            <br>
            <input class="btn" type="submit" value="Войти">
        </div>
    </form>
</t:layout>