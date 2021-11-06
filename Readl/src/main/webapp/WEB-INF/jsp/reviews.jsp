<%--
  Created by IntelliJ IDEA.
  User: Элина
  Date: 04.11.2021
  Time: 9:15
  To change this template use File | Settings | File Templates.
--%>
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
<%--    <body onload="getComments()">--%>
<%--    <script>--%>
<%--        bookId = ${bookId};--%>
<%--        count = 0;--%>
<%--        function getComments() {--%>
<%--            var xmlhttp2 = new XMLHttpRequest();--%>
<%--            xmlhttp2.open('post', '<c:url value="/getReviews"/>', true);--%>
<%--            xmlhttp2.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');--%>
<%--            xmlhttp2.send('count=' + count + '&bookId=' + bookId);--%>
<%--            xmlhttp2.onreadystatechange = function() {--%>
<%--                if(xmlhttp2.readyState == 4) {--%>
<%--                    if(xmlhttp2.status == 200) {--%>
<%--                        var data = xmlhttp2.responseText;--%>
<%--                        if(data != 'empty') {--%>
<%--                            data = JSON.parse(data);--%>
<%--                            var max = 0;--%>
<%--                            for(var i = 0; i < data.length; i++) {--%>
<%--                                var parent = document.getElementsByTagName('body')[0];--%>
<%--                                var elem = document.createElement('div');--%>
<%--                                elem.className = 'comments';--%>
<%--                                parent = parent.appendChild(elem);--%>
<%--                                elem = document.createElement('span');--%>
<%--                                parent.appendChild(elem);--%>
<%--                                var text = data[i].authorId;--%>
<%--                                var textNode = document.createTextNode(text);--%>
<%--                                elem.appendChild(textNode);--%>
<%--                                elem = document.createElement('hr');--%>
<%--                                parent.appendChild(elem);--%>
<%--                                elem = document.createElement('div');--%>
<%--                                elem.className = 'comment';--%>
<%--                                parent.appendChild(elem);--%>
<%--                                text = data[i].content;--%>
<%--                                textNode = document.createTextNode(text);--%>
<%--                                elem.appendChild(textNode);--%>
<%--                                max = data[i].id;--%>
<%--                            }--%>
<%--                            count = max;--%>
<%--                        }--%>
<%--                    }--%>
<%--                }--%>
<%--            };--%>
<%--            setTimeout(function() {--%>
<%--                getComments();--%>
<%--            }, 7000);--%>
<%--        }--%>
<%--    </script>--%>
<%--    </body>--%>
<%--    <script src="<c:url value="/js/review.js"/>">--%>
<%--        var button = document.getElementById('button');--%>
<%--        var xmlhttp = new XMLHttpRequest();--%>
<%--        button.addEventListener('click', function(e) {--%>
<%--            e.preventDefault();--%>
<%--            var description = document.getElementById('description').value.replace(/<[^>]+>/g,'');--%>
<%--            if(description === '') {--%>
<%--                document.getElementById('message').value = 'Заполните поле';--%>
<%--                return false;--%>
<%--            }--%>
<%--            xmlhttp.open('post', '<c:url value="/reviews"/>', true);--%>
<%--            xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');--%>
<%--            xmlhttp.send("content=" + encodeURIComponent(description) + "&bookId=" + ${bookId});--%>
<%--        });--%>
<%--    </script>--%>
