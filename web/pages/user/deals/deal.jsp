<%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/22/19
  Time: 9:34 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%--HEAD--%>
<jsp:include page="/pages/partials/head.jsp">
    <jsp:param name="Deal" value="Profile"/>
</jsp:include>

<body>
<%--Navbar--%>
<jsp:include page="/pages/partials/navbar.jsp"/>
<%--Page Content--%>
<div>
    HERE goes content!
    A single Deal
</div>
<%--Footer--%>
<jsp:include page="/pages/partials/footer.jsp"/>
</body>