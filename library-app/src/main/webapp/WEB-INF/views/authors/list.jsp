<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../common/header.jsp" %>

<h1>👤 Authors</h1>

<div class="topbar">
    <span style="color:var(--muted);font-size:0.9rem">
        Showing <strong>${authors.size()}</strong> author(s)
    </span>
    <a href="/authors/add" class="btn btn-primary">+ Add Author</a>
</div>

<c:choose>
<c:when test="${empty authors}">
    <div class="alert alert-danger">No authors found in the database.</div>
</c:when>
<c:otherwise>
<table>
    <thead>
        <tr>
            <th>#</th>
            <th>Name</th>
            <th>Nationality</th>
            <th>Birth Year</th>
            <th>Biography</th>
            <th>Books</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="author" items="${authors}" varStatus="s">
        <tr>
            <td>${s.index + 1}</td>
            <td><strong>${author.name}</strong></td>
            <td>${author.nationality}</td>
            <td>${author.birthYear}</td>
            <td style="max-width:280px;white-space:normal;font-size:0.85rem;color:var(--muted)">
                <c:choose>
                    <c:when test="${author.biography.length() > 120}">
                        ${author.biography.substring(0, 120)}…
                    </c:when>
                    <c:otherwise>${author.biography}</c:otherwise>
                </c:choose>
            </td>
            <td style="text-align:center">
                <span class="badge badge-genre">${author.books.size()}</span>
            </td>
            <td>
                <div class="btn-group">
                    <a href="/authors/edit/${author.id}" class="btn btn-warning btn-sm">Edit</a>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</c:otherwise>
</c:choose>

<%@ include file="../common/footer.jsp" %>
