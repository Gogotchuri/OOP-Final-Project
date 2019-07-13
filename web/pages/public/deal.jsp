<%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/14/19
  Time: 2:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.*" %>
<%@ page import="managers.CycleManager" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="models.categoryModels.ItemCategory" %>
<%@ page import="models.categoryModels.ItemType" %>
<%@ page import="models.categoryModels.ItemBrand" %>
<%@ page import="models.categoryModels.ItemSerie" %>
<%@ page import="managers.UserManager" %>
<%@ page import="servlets.RoutingConstants" %>
<% Deal deal= (Deal)request.getAttribute("deal");%>
<html>
    <%--HEAD--%>
    <jsp:include page="/pages/partials/head.jsp">
        <jsp:param name="title" value="Deal number ${id}"/>
    </jsp:include>

    <style>
        .wrapper{
            display: grid;
            grid-template-columns: 1fr 1fr 1fr;
            grid-gap: 5px;
        }

        .wrapper > div{
            height: 200%;
        }

        p.italic {
            font-style: italic;
            font-size: 14px;
        }

        .information-column{
            padding: 5px;
            color:lightslategray;
            background-color:powderblue;
        }

        .deal-columns{
            padding: 5px;
            color:lightcyan;
            background-color:#4a778a;
        }

    </style>


    <body>
    <%--Navbar--%>
    <jsp:include page="/pages/partials/navbar.jsp"/>
    <%--Page Content--%>
    <div class="wrapper">

        <%--<% HttpSession session = request.getSession();%>--%>
        <% User thisUser = (User)session.getAttribute("user");%>
        <% int thisId = thisUser.getUserID();%>

         <div class="information-column">
            <% int ownerId = deal.getOwnerID();%>
            <% User user = UserManager.getUserByID(ownerId);%>
            <% if(ownerId == thisId){ %>
             <form method="POST" action="${pageContext.request.contextPath}<%=RoutingConstants.USER_DEAL_CONFIG%>">
                 <button type="submit">Delete deal</button>
             </form>
            <%}%>

            <% String userFullName = " (" + user.getFirstName() + " " + user.getLastName() + ")";%>
            <% String dateCreated = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(deal.getCreateDate());%>
            <% List<Cycle> cycles = CycleManager.getCyclesByDealID(deal.getDealID());%>
            <h3>Information</h3>
                User: <%= user.getUsername()%> <br> <p class="italic"><%= userFullName %></p>
                Deal Created: <%= dateCreated%>
                Deal Status: <%= deal.getStatus().getName() %>
                Cycles:
                    <ul>
                        <% for (int i=0; i<cycles.size(); i++){%>
                        <% Cycle c = cycles.get(i);%>
                        <%-- <% String date = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(c.get);%> date unda amas ciklis --%>
                        <% String statusName = c.getCycleStatus().getName();%>
                        <% String cycleInfo = "Cycle" + i;%>
                        <li><%= cycleInfo%> <br> <p class="italic"><%= statusName %></>p></li>
                        <%}%>
                    </ul>
         </div>

         <div class="deal-columns">
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

         <div class="deal-columns">
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
