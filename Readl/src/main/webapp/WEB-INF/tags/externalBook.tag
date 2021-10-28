<%@tag description="external book" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="book" type="ru.itis.zagidullina.readl.models.Book" %>

<div class="container-fluid wrapper">
    <div class="external-book">
        <img class="book-img" src="https://cdn.eksmo.ru/v2/ASE000000000834477/COVER/cover1.jpg"/>
        <div class="book-content">
            <form action="https://www.w3docs.com/">
                <button class="name-of-book" type="submit">${book.name}</button>
            </form>
            <h2 class="book-describe">${book.description}</h2>
        </div>
    </div>
</div>