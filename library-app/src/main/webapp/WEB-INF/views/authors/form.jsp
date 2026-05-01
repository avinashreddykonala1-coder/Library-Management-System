<%@ taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="../common/header.jsp" %>

<h1>${empty author.id ? '➕ Add New Author' : '✏️ Edit Author'}</h1>

<div class="card">
<form:form method="post"
           action="${empty author.id ? '/authors/add' : '/authors/edit/'.concat(author.id)}"
           modelAttribute="author">

    <div class="form-group">
        <label for="name">Full Name *</label>
        <form:input path="name" id="name" cssClass="form-control" placeholder="e.g. Jane Austen"/>
        <form:errors path="name" cssClass="error-msg"/>
    </div>

    <div class="form-group">
        <label for="nationality">Nationality</label>
        <form:input path="nationality" id="nationality" cssClass="form-control" placeholder="e.g. British"/>
    </div>

    <div class="form-group">
        <label for="birthYear">Birth Year</label>
        <form:input path="birthYear" id="birthYear" type="number"
                    cssClass="form-control" placeholder="e.g. 1775"/>
    </div>

    <div class="form-group">
        <label for="biography">Biography</label>
        <form:textarea path="biography" id="biography" cssClass="form-control"
                       rows="4" placeholder="A short biography…"/>
    </div>

    <div class="form-actions">
        <button type="submit" class="btn btn-primary">
            ${empty author.id ? 'Add Author' : 'Save Changes'}
        </button>
        <a href="/authors" class="btn" style="background:#ecf0f1;color:#2c3e50;">Cancel</a>
    </div>
</form:form>
</div>

<%@ include file="../common/footer.jsp" %>
