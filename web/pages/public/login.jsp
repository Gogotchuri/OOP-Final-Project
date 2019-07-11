<%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/19/19
  Time: 3:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<% List<String> errList = (List<String>) request.getAttribute("errors");%>
<html>
    <%--HEAD--%>
    <jsp:include page="/pages/partials/head.jsp">
        <jsp:param name="title" value="Login"/>
    </jsp:include>

<body>
    <%--Navbar--%>
    <jsp:include page="/pages/partials/navbar.jsp"/>
    <%--Page Content--%>
    <div>
        <h2>Login</h2>
        <%if(errList != null){%>
        <div>
            <%for(String err : errList){%>
                <p style="color:red;"><%=err%></p>
            <%}%>
        </div>
        <%}%>
        <form method="POST" action="${pageContext.request.contextPath}/login">
            <br>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
            <br>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            <br>
            <button type="submit">Login</button>
        </form>
    </div>
    <%--Footer--%>
    <jsp:include page="/pages/partials/footer.jsp"/>
</body>
</html>
