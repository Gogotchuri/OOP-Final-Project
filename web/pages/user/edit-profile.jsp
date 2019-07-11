<%@ page import="servlets.RoutingConstants" %><%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/21/19
  Time: 1:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%--HEAD--%>
<jsp:include page="/pages/partials/head.jsp">
    <jsp:param name="title" value="Profile"/>
</jsp:include>

<body>
<%--Navbar--%>
<jsp:include page="/pages/partials/navbar.jsp"/>
<%--Page Content--%>
<div>
    <form method = "POST" action = "${pageContext.request.contextPath}<%=RoutingConstants.USER_EDIT%>">
        Fields For Updating User Information
    </form>
</div>
<%--Footer--%>
<jsp:include page="/pages/partials/footer.jsp"/>
</body>
</html>
