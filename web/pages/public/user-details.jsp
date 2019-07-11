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
<%@ page import="models.Deal" %>
<%@ page import="java.util.List" %>
<%@ page import="static java.lang.Math.min" %>
<% int maxNumberOfDeals = 5;%>
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
            background-color: powderblue;
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

        .button {
            background-color: powderblue;
            border: none;
            color: lightslategray;
            padding: 15px 32px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
        }


    </style>
<body>
    <%--Navbar--%>
    <jsp:include page="/pages/partials/navbar.jsp"/>
    <%--Page Content--%>
    <div class="wrapper">
        <div>
            <% Image img = user.getProfilePicture(); %>
            <% String imgUrl = img.getUrl();%>
            <% String name = user.getFirstName(); %>
            <% String lastName = user.getLastName(); %>
            <% String userName = user.getUsername(); %>
            <% String email = user.getEmail();%>
            <% String phoneNumber = user.getPhoneNumber(); %>
            <div class="image-div">
                <img src = "<%= imgUrl%>" alt = "No Profile Picture">
            </div>
            <div>
                <a href="#" class="button">Link Button</a>
            </div>
            <div class = "user-info">
                <p><%= name%></p>
                <p><%= lastName%></p>
                <p class= "italic"> <%= userName%> </p>
                <p>Rating: </p>
                <p>10 deals completed</p>
                <p>Email: <%= email%></p>
                <p>Phone Number: <%= phoneNumber%></p>
            </div>
        </div>
        <div class = "deals-column">
            <% List<Deal> deals = user.getDeals();%>
            <% int numToShow = min(maxNumberOfDeals, deals.size());%>
            <h3>Deals:</h3>
            <ul>

                <% for (int i=0; i<numToShow; i++){%>
                <% Deal curDeal = deals.get(i);%>
                <li><p>Deal name <label class="statusActive"> active</label></p>
                    <p class="italic">10.08.2018</p>
                </li>
                <%}%>
            </ul>
            <a href="#" class="button">Link Button</a>
        </div>
    </div>

    <%--Footer--%>
    <jsp:include page="/pages/partials/footer.jsp"/>
</body>
</html>
