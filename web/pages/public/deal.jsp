<%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/14/19
  Time: 2:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Deal" %>
<%@ page import="models.Category" %>
<%@ page import="java.util.List" %>
<% Deal d= (Deal)request.getAttribute("deal");%>
<html>
    <%--HEAD--%>
    <jsp:include page="/pages/partials/head.jsp">
        <jsp:param name="title" value="Deal number ${id}"/>
    </jsp:include>

    <style>
        .wrapper{
            display: grid;
            grid-template-columns: 3fr 3fr 4fr;
            grid-gap: 5px;
            grid-auto-rows: minmax(200px, auto);
        }

        .wrapper >div{
            background:  #205369;
            color: #ffffff;
            padding: 1em;
        }

        .box1{
            grid-row: 1/3;
        }

        .box2{
            grid-row: 1/3;
        }


    </style>


    <body>
    <%--Navbar--%>
    <jsp:include page="/pages/partials/navbar.jsp"/>
    <%--Page Content--%>
    <div class="wrapper">

        <div><h3>Edit</h3>
            <ul>
                <li><input type="button" onclick="alert('Hello World!')" value="Click Me!"></li>
            </ul>
        </div>

        <div><h3>Information</h3>
            <ul>
                <li><%=d.getId()%></li>
                <li><%=d.getCreated_at()%></li>
                <li>status</li>
                <li>Cycles
                    <ul>
                        <li>1<li>
                        <li>2<li> </ul>
                </li>
            </ul>
        </div>

        <div class = "box1">
            <h3>What i give</h3>
            <ul>
                <li>Item1</li>
            </ul>
        </div>

        <div class="box2">
            <h3>What i get</h3>
            <ul>
                <li>Item1</li>
            </ul>
        </div>
    </div>



    <%--Footer--%>
    <jsp:include page="/pages/partials/footer.jsp"/>
</body>
</html>
