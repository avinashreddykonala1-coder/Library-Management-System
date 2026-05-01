<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ include file="../common/header.jsp" %>

<h1>📖 Books</h1>

<div class="topbar">
    <span style="color:var(--muted);font-size:0.9rem">
        Showing <strong>${bookDetails.size()}</strong> book(s) — inner join with Authors
    </span>
    <a href="/books/add" class="btn btn-primary">+ Add Book</a>
</div>

<c:choose>
<c:when test="${empty bookDetails}">
    <div class="alert alert-danger">No books found in the database.</div>
</c:when>
<c:otherwise>
<table>
    <thead>
        <tr>
            <th>#</th>
            <th>Title</th>
            <th>ISBN</th>
            <th>Genre</th>
            <th>Year</th>
            <th>Price (₹)</th>
            <th>Author</th>
            <th>Nationality</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="bd" items="${bookDetails}" varStatus="s">
        <tr>
            <td>${s.index + 1}</td>
            <td><strong>${bd.bookTitle}</strong></td>
            <td style="font-family:monospace;color:var(--muted)">${bd.isbn}</td>
            <td><span class="badge badge-genre">${bd.genre}</span></td>
            <td>${bd.publishedYear}</td>
            <td>
                <c:if test="${not empty bd.price}">
                    <fmt:formatNumber value="${bd.price}" type="number" minFractionDigits="2" maxFractionDigits="2"/>
                </c:if>
            </td>
            <td>${bd.authorName}</td>
            <td>${bd.nationality}</td>
            <td>
                <div class="btn-group">
                    <a href="/books/edit/${bd.bookId}" class="btn btn-warning btn-sm">Edit</a>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</c:otherwise>
</c:choose>

<%@ include file="../common/footer.jsp" %>
