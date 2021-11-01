<%--
  Created by IntelliJ IDEA.
  User: Элина
  Date: 01.11.2021
  Time: 9:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <c:if test="${message != null}">
        <h1>${message}</h1>
    </c:if>
    <div class="book">
        <div class="row">
            <div class="col-md-4 border-right">
                <div class="d-flex flex-column align-items-center text-center p-3 py-5"><img class="rounded-circle mt-5" width="150px" src="https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg"><span> </span></div>
            </div>
            <div class="col-md-8 border-right">
                <div class="p-3 py-5">
                    <div class="row">
                        <h5 class="col-6">${book.account.id}</h5>
                        <h5 class="col-6">${book.dateOfAdding}</h5>
                    </div>
                    <h4 class="name-book">${book.name}</h4>
                    <h2 class="book-describe">${book.description}</h2>
                    <div class="mt-2">
                        <c:if test="${book.genres.size != 0}">
                            <h5>Жанры: </h5>
                            <c:forEach items="${book.genres}" var="genre">
                                <h6>${genre.name}</h6>
                            </c:forEach>
                        </c:if>
                    </div>
                    <div class="book-buttons row mt-5">
                        <div class="col-md-4">
                            <button class="btn" type="button">
                                <a href="<c:url value="/reviews?id=${book.id}"/>">Рецензии</a>
                            </button>
                        </div>
                        <div class="col-md-4">
                            <button class="btn" type="button">
                                <a href="<c:url value="/bookComments?id=${book.id}"/>">Комментарии</a>
                            </button>
                        </div>
                    </div>
                    <c:if test="${book.account.id == accountId}">
                        <div class="mt-5 text-center">
                            <input class="btn" type="submit" value="Добавить главу">
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <div>
            <c:if test="${book.chapters.size == 0}">
                <h1>Нет глав</h1>
            </c:if>
            <div class="chapters">
                <c:forEach items="${book.chapters}" var="chapter">
                    <h1 class="chapter">${chapter.name}</h1>
                </c:forEach>
            </div>
        </div>
    </div>
</t:layout>
