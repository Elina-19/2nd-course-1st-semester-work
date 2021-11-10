<%@tag description="external book" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="book" type="ru.itis.zagidullina.readl.models.Book" %>

<div class="container-fluid wrapper">
    <div class="external-book">
        <iframe class="d-flex mt-5 image-book" scrolling="no" src="<c:url value="/downloadImage?id=${book.id}"/>"></iframe>
        <div class="book-content">
            <button class="btn" type="button">
                <a href="<c:url value="/book?id=${book.id}"/>">${book.name}</a>
            </button>
            <div class="book-describe">
                <h4>${book.description}</h4>
            </div>
        </div>
    </div>
</div>