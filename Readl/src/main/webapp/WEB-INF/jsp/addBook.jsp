<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <form method="post" enctype="multipart/form-data">
        <div class="container form-add-book">
            <div class="row">
                <div class="col-md-4 border-right">
                    <div class="d-flex flex-column align-items-center text-center p-3 py-5"><img class="rounded-circle mt-5" width="150px" src="https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg"><span> </span></div>
                    <div class="mt-5 text-center">
                        <input type="file" class="btn" name="file" placeholder="Загрузить обложку">
                    </div>
                </div>
                <div class="col-md-8 border-right">
                    <div class="p-3 py-5">
                        <h4 class="new-book">Добавление книги</h4>
                        <c:if test="${error != null}">
                            <h5>${error}</h5>
                        </c:if>
                        <div>
                            <label class="labels">Название книги</label>
                            <input class="form-control" name="name" placeholder="Название">
                        </div>
                        <div>
                            <label for="description" class="form-label">Описание книги</label>
                            <textarea class="form-control" name="description" id="description" placeholder="Введите описание" rows="3"></textarea>
                        </div>
                        <br>
                        <div class="col-md-12">
                            <label class="labels">Жанры</label>
                            <ul class="list-group">
                                <li>
                                    <c:forEach items="${genres}" var="genre">
                                        <input type="checkbox" name="genre" value="${genre.id}">
                                        ${genre.name}
                                    </c:forEach>
                                </li>
                            </ul>
                        </div>
                        <div class="mt-5 text-center">
                            <input class="btn" type="submit" value="Добавить">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</t:layout>
