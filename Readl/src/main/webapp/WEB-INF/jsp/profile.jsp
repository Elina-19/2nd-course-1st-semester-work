<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <div class="profile">
    <div class="container rounded bg-white mt-5 mb-5">
        <div class="row">
            <div class="col-md-4 border-right">
                <div class="d-flex flex-column align-items-center text-center p-3 py-5"><img class="rounded-circle mt-5" width="150px" src="https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg">
                    <span class="font-weight-bold">${account.nickname}</span>
                    <span class="text-black-50">${account.email}</span>
                </div>
                <div class="mt-5 text-center">
                    <button class="btn" type="button">
                        <a href="<c:url value="/logout"/>" class="logout-button" id="logout">Выйти</a>
                    </button>
                </div>
            </div>
            <div class="col-md-8 border-right">
                <div class="p-3 py-5">
                    <h4 class="your-profile">Ваш профиль</h4>
                </div>
                <div class="profile-buttons row mt-2">
                    <div class="col-md-4"><label class="labels"></label><button class="btn">Favourite</button></div>
                    <div class="col-md-4"><label class="labels"></label><button class="btn">Followers</button></div>
                    <div class="col-md-4"><label class="labels"></label><button class="btn">Followers</button></div>
                </div>
                <div class="profile-book-buttons row mt-4">
                    <div class="col-md-4">
                        <button class="btn" type="button">
                            <a href="<c:url value="/addBook"/>" id="addBook">Добавить книгу</a>
                        </button>
                    </div>
                    <div class="col-md-4">
                        <button class="btn" type="button">
                            <a href="<c:url value="/myBooks?id=${account.id}"/>" id="myBook">Мои книги</a>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</t:layout>
