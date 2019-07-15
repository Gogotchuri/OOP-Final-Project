<%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/19/19
  Time: 2:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.User" %>
<%@ page import="servlets.RoutingConstants" %>
<html>
    <% User user = (User) session.getAttribute("user"); %>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <a href="${pageContext.request.contextPath}/" class="navbar-brand">BARTER</a>
        <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarCollapse">
            <div class="navbar-nav">
                <a href="${pageContext.request.contextPath}/" class="nav-item nav-link active">Home</a>
                <a href="${pageContext.request.contextPath}<%= RoutingConstants.DEALS%>" class="nav-item nav-link">Deals</a>
                <%if(user != null){%>
                    <a href="${pageContext.request.contextPath}<%= RoutingConstants.USER_DEAL_CREATE%>" class="nav-item nav-link">Create Deal</a>
                    <a href="${pageContext.request.contextPath}<%= RoutingConstants.USER_CHATS%>" class="nav-item nav-link">Chats</a>
                    <a href="${pageContext.request.contextPath}<%= RoutingConstants.PUBLIC_PROFILE%>?id=<%=user.getUserID()%>" class="nav-item nav-link">Profile</a>
                    <a href="${pageContext.request.contextPath}<%= RoutingConstants.LOGOUT%>" class="nav-item nav-link">Logout</a>
                <%}else{%>
                    <a href="${pageContext.request.contextPath}<%= RoutingConstants.LOGIN%>" class="nav-item nav-link">Login</a>
                    <a href="${pageContext.request.contextPath}<%= RoutingConstants.REGISTER%>" class="nav-item nav-link">Register</a>
                <%}%>
            </div>
            <form action="${pageContext.request.contextPath}<%=RoutingConstants.DEALS%>" method="GET" class="form-inline ml-auto">
                <input type="text" class="form-control" name="title" placeholder="Search">
                <button type="submit" class="btn btn-outline-secondary">Search</button>
            </form>
        </div>
    </nav>
</html>
