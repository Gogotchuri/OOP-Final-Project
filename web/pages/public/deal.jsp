<%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/14/19
  Time: 2:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <%--HEAD--%>
    <jsp:include page="/pages/partials/head.jsp">
        <jsp:param name="title" value="Deal number ${id}"/>
    </jsp:include>

<body>
    <%--Navbar--%>
    <jsp:include page="/pages/partials/navbar.jsp"/>
    <%--Page Content--%>
    <div>
        HERE goes dela number ${id}!
    </div>
    <%--Footer--%>
    <jsp:include page="/pages/partials/footer.jsp"/>
</body>
</html>
