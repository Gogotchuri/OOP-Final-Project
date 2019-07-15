<%--
  Created by IntelliJ IDEA.
  User: giorgi
  Date: 7/15/19
  Time: 2:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%--HEAD--%>
<jsp:include page="/pages/partials/head.jsp">
    <jsp:param name="Deals" value="Profile"/>
</jsp:include>

<body>
<%--Navbar--%>
<jsp:include page="/pages/partials/navbar.jsp"/>
<%--Page Content--%>
<div>
    HERE goes content!
    USER DEAL
</div>
<%--Footer--%>
<jsp:include page="/pages/partials/footer.jsp"/>
</body>
