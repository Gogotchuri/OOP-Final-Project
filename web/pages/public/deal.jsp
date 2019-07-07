<%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/14/19
  Time: 2:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Deal" %>
<%@ page import="models.User" %>
<%@ page import="models.Item" %>
<%@ page import="java.lang.Integer" %>
<%@ page import="models.Category" %>
<%@ page import="java.util.List" %>
<% Deal deal= (Deal)request.getAttribute("deal");%>
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

        <% HttpSession session = request.getSession();%>
        <% User thisUser = (User)session.getAttribute("user");%>
        <% int thisId = thisUser.getId();%>

        <% if(deal.getUser_id() == thisId){ %>
        <div>
            <ul>
                <li><input type="button" onclick="user.dealConfigServlet.doGet()" value="Edit"></li>
                <li><input type="button" onclick="user.DealConfigServlet.doDelete()" value="Delete"></li>
            </ul>
        </div>
        <%}%>

        <div><h3>Information</h3>
            <ul>
                <li><%=deal.getId()%></li>
                <li><%=deal.getCreated_at()%></li>
                <li>status</li>
                <li>Cycles
                    <ul>
                        <li>1<li>
                        <li>2<li> </ul>
                </li>
            </ul>
        </div>

        <div class = "box1">
            <% List<Item> OwnedItems = deal.getOwnedItems()%>
            <h3>What i give</h3>
            <ul>
                <% for(Item i : OwnedItems){ %>
                <li><%= i.getName()%></li>
                <% } %>
            </ul>
        </div>

        <div class="box2">
            <h3>What i get</h3>
            <% List<Category> WantedItemCategories = deal.getWantedItemCategories(); %>
            <ul>
                <% for(Category c : WantedItemCategories){ %>
                <li><%= c.getName()%></li>
                <% } %>
            </ul>
        </div>
    </div>



    <%--Footer--%>
    <jsp:include page="/pages/partials/footer.jsp"/>
</body>
</html>
