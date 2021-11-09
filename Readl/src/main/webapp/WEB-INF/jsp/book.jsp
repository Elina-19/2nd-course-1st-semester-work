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
        <h1 class="message">${message}</h1>
    </c:if>
    <c:if test="${message == null}">
    <div class="book">
        <div class="row">
            <div class="col-md-4 border-right">
                <div class="d-flex flex-column align-items-center text-center p-3 py-5">
                    <iframe class="d-flex mt-5 image-book" scrolling="no" src="<c:url value="/downloadImage?id=${book.id}"/>"></iframe>
                    <div class="row">
                        <div class="btn mt-5 text-center col-6" type="button">
                            <a href="<c:url value="/reviews?id=${book.id}"/>">Рецензии</a>
                        </div>
                        <div class="btn mt-5 text-center col-6" type="button">
                            <a href="<c:url value="/bookComments?id=${book.id}"/>">Комментарии</a>
                        </div>
                        <c:if test="${authenticated == true}">
                        <div class="mt-5 text-center col-6">
                            <input id="button" class="btn" type="submit" value="${status}">
                        </div>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="col-md-8 border-right">
                <div class="p-3 py-5">
                    <div class="row">
                        <h5 class="col-6">Автор: ${book.account.nickname}</h5>
                        <h5 id="date" class="col-4">${book.dateOfAdding}</h5>
                    </div>
                    <h4 class="name-book">${book.name}</h4>
                    <h2 class="book-describe">${book.description}</h2>
                    <div class="mt-2">
                        <c:if test="${book.genres != null}">
                            <h5>Жанры: </h5>
                            <c:forEach items="${book.genres}" var="genre">
                                <h6>${genre.name}</h6>
                            </c:forEach>
                        </c:if>
                    </div>
                    <c:if test="${book.account.id == accountId}">
                        <div class="mt-5 text-center">
                            <button class="btn" type="button">
                                <a href="<c:url value="/addChapter?id=${book.id}"/>">Добавить главу</a>
                            </button>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="chapters mt-5">
            <c:if test="${chapters != null}">
                <h1 class="message">Нет глав</h1>
            </c:if>
            <c:if test="${book.chapters != null}">
                <div>
                    <c:forEach items="${book.chapters}" var="chapter">
                        <h1 class="chapter">
                            <a href="<c:url value="/chapter?id=${chapter.id}"/>">${chapter.name}</a>
                        </h1>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </div>
    </c:if>
    <script>
        date = "${book.dateOfAdding}";
        document.getElementById("date").innerHTML = date.substring(0, 16);
        bookId = ${book.id};
        url = "<c:url value="/favouriteHandler"/>";
    </script>
    <script src="<c:url value="/js/addDeleteFavourite.js"/>" charset="utf-8"></script>
</t:layout>
