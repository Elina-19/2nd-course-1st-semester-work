<%@tag description="review" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="nickname" type="java.lang.String" %>
<%@attribute name="content" type="java.lang.String" %>

<div class="review">
    <div class="row border-bottom">
        <div class="col-9">${nickname}</div>
<%--        <div class="col-3">${review.date}</div>--%>
    </div>
    <div>${content}</div>
</div>
