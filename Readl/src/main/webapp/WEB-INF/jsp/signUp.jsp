<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <form method="post">
        <div class="sign-form">
            <c:if test="${error != null}">${error}<br></c:if>
            <div class="mb-3">
                <label for="nickname" class="form-label">Nickname</label>
                <input class="form-control" id="nickname" name="nickname" placeholder="Nickname" <c:if test="${not empty nickname}"> value="<c:out value="${nickname}"/>"</c:if>>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" class="form-control" name="email" id="email" placeholder="Email" <c:if test="${not empty email}"> value="<c:out value="${email}"/>"</c:if>>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" name="password" id="password" placeholder="Password" <c:if test="${not empty email}"> value="<c:out value="${email}"/>"</c:if>>
            </div>
            <br>
            <input type="submit" class="btn" value="Sign Up">
        </div>
    </form>
</t:layout>