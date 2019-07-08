<%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/19/19
  Time: 4:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.User" %>
<%@ page import="models.Image" %>
<% User user = (User) request.getAttribute("user");%>
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

        .box{
            background:  #205369;
            color: #ffffff;

        }

        img {
            max-width: 100%;
            height: auto;
        }

        p.italic {
            font-style: italic;
            font-size: 14px;
        }

    </style>
<body>
    <%--Navbar--%>
    <jsp:include page="/pages/partials/navbar.jsp"/>
    <%--Page Content--%>
    <div class="wrapper">
        <% Image profileImage = user.getProfilePicture();%>
        <% String imageUrl = profileImage.getUrl();%>
        <% String username = user.getUsername();%>
        <% String userFullName = " (" + user.getFirstName() + " " + user.getLastName() + ")";%>
        <% String email = user.getEmail();%>
        <% String number = user.getPhoneNumber();%>
        <div><img src = "<%= imageUrl%>" alt= "Profile Picture"></div>
        <div class = "box">dzveli dilebi</div>
        <div class = "box">
            User: <%= username%> <br> <p class="italic"><%= userFullName %></p>
            Email: <%= email%>
            Phone Number: <%= number%>
        </div>
        <div class = "box">axlandeli dilebi</div>
    </div>

    <%--Footer--%>
    <jsp:include page="/pages/partials/footer.jsp"/>
</body>
</html>
