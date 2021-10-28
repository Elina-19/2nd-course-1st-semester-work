<%@tag description="comment" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="commentObject" type="java.lang.String" %>
<div class="comment">
    <div class="comment-header">
    </div>
    <div class="comment-content">
        ${commentObject}
    </div>
    <div class="comment-footer">
        <div class="rate">
            <div class="response-btn underline-on-hover"
                 data-id="${commentObject.length()}">
                Ответить
            </div>
        </div>
    </div>
</div>