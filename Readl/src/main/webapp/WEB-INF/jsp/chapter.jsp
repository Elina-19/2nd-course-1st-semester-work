<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <c:if test="${message != null}">
        <h1 class="message">${message}</h1>
    </c:if>
    <c:if test="${message == null}">
        <div class="chapter-content">
            ${chapter.content}
        </div>
        <div class="row chapter-buttons">
            <button class="btn mt-5">
                <a href="<c:url value="/book?id=${chapter.book.id}"/>">Книга</a>
            </button>
        </div>
    </c:if>

<%--    <div class="col-md-8 add-chapter">--%>
<%--        <h4 class="new-book">Комментарии</h4>--%>
<%--        <c:if test="${authenticated == true}">--%>
<%--            <div id="message"></div>--%>
<%--            <div>--%>
<%--                <label for="description" class="form-label">Добавьте комментарий</label>--%>
<%--                <textarea class="form-control" id="description" placeholder="Введите текст" rows="3"></textarea>--%>
<%--            </div>--%>
<%--            <div class="mt-3">--%>
<%--                <input id="button" class="btn" type="submit" value="Отправить">--%>
<%--            </div>--%>
<%--        </c:if>--%>
<%--    </div>--%>
<%--    <div id="empty"></div>--%>
<%--    <div id="reviews"></div>--%>
<%--    <script>--%>
<%--        bookId=${chapter.id};--%>
<%--        type = "chapter";--%>
<%--        urlGet="<c:url value="/getComments"/>";--%>
<%--    </script>--%>
<%--    <script type="text/javascript" src="<c:url value="/js/comments.js"/>"></script>--%>
</t:layout>