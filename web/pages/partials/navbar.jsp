<%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/19/19
  Time: 2:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.User" %>
<html>
    <% User user = (User) session.getAttribute("user"); %>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <a href="${pageContext.request.contextPath}/" class="navbar-brand">TODO</a>
        <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarCollapse">
            <div class="navbar-nav">
                <a href="${pageContext.request.contextPath}/" class="nav-item nav-link active">Home</a>
                <a href="${pageContext.request.contextPath}/deals" class="nav-item nav-link">Deals</a>
                <%if(user != null){%>
                    <a href="${pageContext.request.contextPath}/user/configuration" class="nav-item nav-link">Profile</a>
                    <a href="${pageContext.request.contextPath}/logout" class="nav-item nav-link">Logout</a>
                <%}else{%>
                    <a href="${pageContext.request.contextPath}/login" class="nav-item nav-link">Login</a>
                    <a href="${pageContext.request.contextPath}/register" class="nav-item nav-link">Register</a>
                <%}%>
            </div>
            <form class="form-inline ml-auto">
                <input type="text" class="form-control" placeholder="Search">
                <button type="submit" class="btn btn-outline-secondary">Search</button>
            </form>
        </div>
    </nav>
</html>
