<%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/21/19
  Time: 11:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
    <%--HEAD--%>
    <jsp:include page="/pages/partials/head.jsp">
        <jsp:param name="title" value="chat"/>
    </jsp:include>

<body>
    <%--Navbar--%>
    <jsp:include page="/pages/partials/navbar.jsp"/>
    <%--Page Content--%>
    <div>
        HERE goes content!
    </div>
    <%--Footer--%>
    <jsp:include page="/pages/partials/footer.jsp"/>
</body>
</html>
