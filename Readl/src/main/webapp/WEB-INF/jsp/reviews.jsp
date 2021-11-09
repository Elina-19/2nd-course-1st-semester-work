<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <div class="col-md-8 add-chapter">
        <h4 class="new-book">Рецензии</h4>
        <c:if test="${authenticated == true}">
            <div id="message"></div>
            <div>
                <label for="description" class="form-label">Добавьте рецензию</label>
                <textarea class="form-control" id="description" placeholder="Введите текст" rows="3"></textarea>
            </div>
            <div class="mt-3">
                <input id="button" class="btn" type="submit" value="Отправить">
            </div>
        </c:if>
    </div>
    <div id="empty"></div>
    <div id="reviews"></div>
    <script>
        bookId=${bookId};
        urlGet="<c:url value="/getReviews"/>";
        urlSend="<c:url value="/reviews"/>";
    </script>
    <script type="text/javascript" src="<c:url value="/js/review.js"/>"></script>
</t:layout>