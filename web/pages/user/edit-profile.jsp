<%@ page import="servlets.RoutingConstants" %>
<%@ page import="models.User" %>
<%@ page import="java.util.List" %><%--
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
    <jsp:param name="title" value="User Edit"/>
</jsp:include>

<body>
<%--Navbar--%>
<jsp:include page="/pages/partials/navbar.jsp"/>
<%--Page Content--%>
<div>
    <% List<String> errors = (List<String>) request.getAttribute("errors");
       if (errors != null) {
           for (String error : errors) { %>
               <br> <%=error%> <br>
    <%     }
       } %>
    <% User user = (User) request.getAttribute("user"); %>
    <form method="POST" action="${pageContext.request.contextPath}<%=RoutingConstants.USER_EDIT%>">
        <br>
        <label for="password"> Password: </label>
        <input type="password" id="password" name="password">
        <br>
        <label for="first_name"> First Name: </label>
        <input type="text" id="first_name" name="first_name" value="<%=user.getFirstName()%>">
        <br>
        <label for="last_name"> Last Name: </label>
        <input type="text" id="last_name" name="last_name" value="<%=user.getLastName()%>">
        <br>
        <label for="email"> E-Mail: </label>
        <input type="email" id="email" name="email" value="<%=user.getEmail()%>">
        <br>
        <label for="phone_number"> Phone Number: </label>
        <input type="number" id="phone_number" name="phone_number" value="<%=user.getPhoneNumber()%>">
        <br>
        <button type="submit"> Submit Changes </button>
    </form>
</div>
<%--Footer--%>
<jsp:include page="/pages/partials/footer.jsp"/>
</body>
</html>