<%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/14/19
  Time: 2:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="managers.UserManager" %>
<%@ page import="models.*" %>
<%@ page import="managers.CycleManager" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.mysql.cj.x.protobuf.MysqlxDatatypes" %>
<%@ page import="models.categoryModels.ItemCategory" %>
<%@ page import="models.categoryModels.ItemType" %>
<%@ page import="models.categoryModels.ItemBrand" %>
<%@ page import="models.categoryModels.ItemSerie" %>
<% Deal deal= (Deal)request.getAttribute("deal");%>
<html>
    <%--HEAD--%>
    <jsp:include page="/pages/partials/head.jsp">
        <jsp:param name="title" value="Deal number ${id}"/>
    </jsp:include>
s
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

        <%--<% HttpSession session = request.getSession();%>--%>
        <% User thisUser = (User)session.getAttribute("user");%>
        <% int thisId = thisUser.getUserID()%>

        <div>
            <% User user = deal.getOwner();%>
            <% if(user.getUserID() == thisId){ %>
                <input type="button" onclick="user.DealConfigServlet.doDelete()" value="Delete Deal">
            <%}%>

            <% String userFullName = " (" + user.getFirstName() + " " + user.getLastName() + ")";%>
            <% String dateCreated = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(deal.getCreateDate());%>
            <% List<Cycle> cycles = CycleManager.getCyclesByDealID(deal.getDealID());%>
            <h3>Information</h3>
            <ul>
                <li><%= user.getUsername()%> <br> <%= userFullName %></li>
                <li><%= dateCreated%></li>
                <li><%= deal.getStatus().getName() %></li>
                <li>Cycles
                    <ul>
                        <% for (int i=0; i<cycles.size(); i++){%>
                        <% Cycle c = cycles.get(i);%>
                        <%-- <% String date = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(c.get);%> date unda amas ciklis --%>
                        <% String statusName = c.getCycleStatus().getName();%>
                        <% String cycleInfo = "Cycle" + i;%>
                        <li><%= cycleInfo%> <br> <p class="italic"><%= statusName %></>p></li>
                        <%}%>
                    </ul>
                </li>
            </ul>
        </div>

        <div class = "box1">
            <% List<Item> OwnedItems = deal.getOwnedItems();%>
            <h3>What i give</h3>
            <ul>
                <% for(Item i : OwnedItems){%>
                <% ItemCategory cat = i.getCategory();%>
                <% ItemType type = cat.getType();%>
                <% ItemBrand brand = cat.getBrand();%>
                <% ItemSerie serie = cat.getSerie();%>
                <% String catInfo = type + "-" + brand + "-" + serie;%>
                    <%-- <% String date = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(i.getCreatedAt());%>
                    aitemis tarigia sawiro --%>

                    <li><%= i.getName() %> <br> <%= catInfo%> <%-- <br> <p class="italic"><%= date%></p>> --%></li>
                    <% } %>
                </ul>
            </div>

            <div class="box2">
                <h3>What i get</h3>
                <% List<ItemCategory> WantedItemCategories = deal.getWantedCategories(); %>
                <ul>
                    <% for(ItemCategory cat : WantedItemCategories){ %>
                    <% ItemType type = cat.getType();%>
                    <% ItemBrand brand = cat.getBrand();%>
                    <% ItemSerie serie = cat.getSerie();%>
                    <% String catInfo = type + "-" + brand + "-" + serie;%>
                    <li><%= catInfo%></li>
                    <% } %>
                </ul>
            </div>
        </div>



        <%--Footer--%>
    <jsp:include page="/pages/partials/footer.jsp"/>
</body>
</html>
