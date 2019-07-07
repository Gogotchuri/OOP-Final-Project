<%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/19/19
  Time: 4:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Deal" %>
<%@ page import="models.User" %>
<html>
    <%--HEAD--%>
    <jsp:include page="/pages/partials/head.jsp">
        <jsp:param name="title" value="Profile N ${id}"/>
    </jsp:include>

    <style>
        .wrapper{
            display: grid;
            grid-template-columns: 1fr 2fr;
            grid-gap: 5px;
            grid-auto-rows: minmax(200px, auto);
        }

        .wrapper >div{
            background:  #205369;
            color: #ffffff;
            padding: 1em;
        }
    </style>
<body>
    <%--Navbar--%>
    <jsp:include page="/pages/partials/navbar.jsp"/>
    <%--Page Content--%>
    <div class="wrapper">
        <div>surati</div>
        <div>dzveli dilebi</div>
        <div>info</div>

        <div >axlandeli dilebi</div>

    </div>
    <%--Footer--%>
    <jsp:include page="/pages/partials/footer.jsp"/>
</body>
</html>
