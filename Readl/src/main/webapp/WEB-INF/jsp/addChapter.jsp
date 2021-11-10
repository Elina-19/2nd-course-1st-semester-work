<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <form method="post" enctype="multipart/form-data">
    <div class="col-md-8 add-chapter">
        <h4 class="new-book">Добавление главы</h4>
        <c:if test="${error != null}">
            <h5>${error}</h5>
        </c:if>
        <input type="hidden" name="bookId" value="${bookId}">
        <div>
            <label class="labels">Название главы</label>
            <input type="text" class="form-control" name="name" placeholder="Название">
        </div>
        <div class="row mt-4">
            <input type="file" class="btn col-md-4" name="file" placeholder="Загрузить главу">
            <div class="col-md-4">
                <input class="btn" type="submit" value="Добавить главу">
            </div>
        </div>
    </div>
    </form>
</t:layout>
