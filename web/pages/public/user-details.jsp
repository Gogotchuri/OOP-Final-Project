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
            grid-template-columns: 1fr 1fr;
            grid-gap: 10px;
        }

        .wrapper > div{
            grid-gap: 10px;
        }

        img {
            max-width: 100px;
            max-height: 100px;
        }

        p.italic {
            font-style: italic;
            font-size: 0.5em;
        }

        .user-info{
            padding: 5px;
            color:lightslategray;
            background-color:powderblue;
        }

        .image-div{
            min-height: 50%;
        }

        .deals-column{
            padding: 5px;
            color:lightcyan;
            background-color:#4a778a;
        }

        label.statusCompleted{
            color: lightgreen;
            size: 5px;
        }

        label.statusActive{
            color:rgb(228, 153, 153);
            size: 5px;
        }


    </style>
<body>
    <%--Navbar--%>
    <jsp:include page="/pages/partials/navbar.jsp"/>
    <%--Page Content--%>
    <div class="wrapper">
        <div>
            <div class="image-div">
                <img src = "imageUrl" alt = "Profile Picture">
            </div>
            <div>
                <button></button>
            </div>
            <div class = "user-info">
                <p>Name: </p>
                <p class= "italic">Username: </p>
                <p>Rating: </p>
                <p>10 deals completed</p>
                <p>Email:</p>
                <p>Phone Number:</p>
            </div>
        </div>
        <div class = "deals-column">
            <h3>Deals:</h3>
            <ul>
                <li><p>Deal name <label class="statusActive"> active</label></p>
                    <p class="italic">10.08.2018</p>
                </li>
                <li><p>Deal name <label class="statusCompleted"> completed</label></p>
                    <p class="italic">10.08.2018</p>
                </li>
                <li><p>Deal name</p>
                    <p class="italic">10.08.2018</p>
                </li>
                <li><p>Deal name</p>
                    <p class="italic">10.08.2018</p>
                </li>

            </ul>
        </div>
    </div>

    <%--Footer--%>
    <jsp:include page="/pages/partials/footer.jsp"/>
</body>
</html>
