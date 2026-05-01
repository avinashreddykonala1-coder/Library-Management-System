<%@ taglib uri="http://java.sun.com/jsp/jstl/core"   prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="../common/header.jsp" %>

<h1>${empty book.id ? '➕ Add New Book' : '✏️ Edit Book'}</h1>

<div class="card">
<form:form method="post"
           action="${empty book.id ? '/books/add' : '/books/edit/'.concat(book.id)}"
           modelAttribute="book">

    <div class="form-group">
        <label for="title">Title *</label>
        <form:input path="title" id="title" cssClass="form-control" placeholder="e.g. The Great Gatsby"/>
        <form:errors path="title" cssClass="error-msg"/>
    </div>

    <div class="form-group">
        <label for="isbn">ISBN *</label>
        <form:input path="isbn" id="isbn" cssClass="form-control" placeholder="e.g. 978-0-7432-7356-5"/>
        <form:errors path="isbn" cssClass="error-msg"/>
    </div>

    <div class="form-group">
        <label for="authorId">Author *</label>
        <select name="authorId" id="authorId" class="form-control">
            <option value="">-- Select Author --</option>
            <c:forEach var="author" items="${authors}">
                <option value="${author.id}"
                    ${book.author != null && book.author.id == author.id ? 'selected' : ''}>
                    ${author.name}
                </option>
            </c:forEach>
        </select>
    </div>

    <div class="form-group">
        <label for="genre">Genre</label>
        <form:input path="genre" id="genre" cssClass="form-control" placeholder="e.g. Fiction, Mystery"/>
    </div>

    <div class="form-group">
        <label for="publishedYear">Published Year</label>
        <form:input path="publishedYear" id="publishedYear" type="number"
                    cssClass="form-control" placeholder="e.g. 1999"/>
    </div>

    <div class="form-group">
        <label for="price">Price</label>
        <form:input path="price" id="price" type="number" step="0.01"
                    cssClass="form-control" placeholder="e.g. 12.99"/>
        <form:errors path="price" cssClass="error-msg"/>
    </div>

    <div class="form-actions">
        <button type="submit" class="btn btn-primary">
            ${empty book.id ? 'Add Book' : 'Save Changes'}
        </button>
        <a href="/books" class="btn" style="background:#ecf0f1;color:#2c3e50;">Cancel</a>
    </div>
</form:form>
</div>

<%@ include file="../common/footer.jsp" %>
