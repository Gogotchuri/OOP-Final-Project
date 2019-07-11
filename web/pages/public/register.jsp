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
        <jsp:param name="title" value="Register"/>
    </jsp:include>

<body>
    <%--Navbar--%>
    <jsp:include page="/pages/partials/navbar.jsp"/>
    <%--Page Content--%>
    <div>
        <h2>Register</h2>
        <%if(errList != null){%>
            <div>
                <%for(String err : errList){%>
                    <p style="color:red;"><%=err%></p>
                <%}%>
            </div>
        <%}%>
        <form method="POST" action="${pageContext.request.contextPath}/register">
            <label for="email">Email:</label>
            <input type="email" name="email" id="email" placeholder="Enter email address" required>
            <br>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" placeholder="Enter username" required>
            <br>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" placeholder="Password" required>
            <br>
            <label for="first_name">First name:</label>
            <input type="text" id="first_name" name="first_name" placeholder="(Optional field)">
            <br>
            <label for="last_name">Last name:</label>
            <input type="text" id="last_name" name="last_name" placeholder="(Optional field)">
            <br>

            <label for="phone_number">Phone number:</label>
            <input type="number" id="phone_number" name="phone_number" placeholder="i.e 995597777325">
            <br>
            <button type="submit">Register</button>
        </form>
    </div>
    <%--Footer--%>
    <jsp:include page="/pages/partials/footer.jsp"/>
</body>
</html>

