<%@tag description="header" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<head>
    <nav class="navbar fixed-top navbar-expand-lg navbar-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Readl</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="search collapse navbar-collapse" id="navbarSupportedContent">
                <div>
                    <form class="d-flex">
                        <input class="form-control mr-2" type="search" placeholder="Search" aria-label="Search">
                        <button class="btn" type="submit">Search</button>
                    </form>
                </div>
                <c:if test="${authenticated eq null}">
                    <div class="d-flex">
                        <button class="btn" type="button">
                            <a href="<c:url value="/signIn"/>" class="logout-button" id="signIn">Войти</a>
                        </button>
                        <button class="btn" type="button">
                            <a href="<c:url value="/signUp"/>" class="logout-button" id="signUp">Регистрация</a>
                        </button>
                        <a>
                            <img class="userIcon" src="<c:url value="/images/user.png"/>"/>
                        </a>
                    </div>
                </c:if>
            </div>
        </div>
    </nav>
</head>