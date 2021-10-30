<%@tag description="external book" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="book" type="ru.itis.zagidullina.readl.models.Book" %>

<div class="container-fluid wrapper">
    <div class="external-book">
        <img class="book-img" src="<c:url value="C:/files/${book.pathToDirectoryWithContent}/${book.imagePath}"/>"/>
        <div class="book-content">
            <button class="btn" type="button">
                <a href="<c:url value="/book?id=${book.id}"/>">${book.name}</a>
            </button>
            <h2 class="book-describe">${book.description}</h2>
        </div>
    </div>
</div>