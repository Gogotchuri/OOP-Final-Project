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
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="models.ProcessStatus" %>
<%@ page import="servlets.RoutingConstants" %>
<% int maxNumberOfDeals = 5;%>
<% User user = (User) request.getAttribute("user");%>
<html>
    <%--HEAD--%>
    <jsp:include page="/pages/partials/head.jsp">
        <jsp:param name="title" value="Profile N ${id}"/>
    </jsp:include>

<body>
    <%--Navbar--%>
    <jsp:include page="/pages/partials/navbar.jsp"/>
    <%--Page Content--%>
    <div class="user-details-wrapper">
        <div class="details">
            <% Image img = user.getProfilePicture(); %>
            <% String imgUrl = "";
            if(img == null){
                imgUrl = "https://zenbooks.ca/wp-content/uploads/2017/09/placeholder-female-square.png";
            }else{
                imgUrl = img.getUrl();
            } %>
            <% String name = user.getFirstName(); %>
            <% String lastName = user.getLastName(); %>
            <% String userName = user.getUsername(); %>
            <% String email = user.getEmail(); %>
            <% String phoneNumber = user.getPhoneNumber(); %>
            <div class="image-div">
                <img src = "<%= imgUrl%>" alt = "No Profile Picture" style="width: 1000px">
            </div>
            <div>
                <% User thisUser = (User)session.getAttribute("user"); %>
                <% int thisId = thisUser.getUserID(); %>
                <%if(user.getUserID() == thisId){ %>
                <form method="GET" action="${pageContext.request.contextPath}<%=RoutingConstants.USER_EDIT%>">
                    <button type="submit">Edit Profile</button>
                </form>
                <% } %>
            </div>
            <div class = "user-info">
                <p><%= name %></p>
                <p><%= lastName %></p>
                <p> <%= userName %> </p>
                <!--<p>Rating: </p>
                <p>10 deals completed</p> -->
                <p>Email: <%= email %></p>
                <p>Phone Number: <%= phoneNumber %></p>
            </div>
        </div>
        <div class = "deals-column">
            <% List<Deal> deals = user.getDeals(); %>
            <% int numToShow = deals.size(); %>
            <h3>Deals:</h3>
            <ul>

                <% if(numToShow == 0){ %>
                    <p>this user has no deals</p>
                <% }else{
                    for (int i=0; i<numToShow; i++){ %>
                <% Deal curDeal = deals.get(i); %>
                <% String title = curDeal.getTitle(); %>
                <% String dateCreated = new SimpleDateFormat("yyyy.MM.dd").format(curDeal.getCreateDate());%>
                <% ProcessStatus.Status status = curDeal.getStatus(); %>
                <% String statusName = status.getName(); %>
                <% String className = statusName; %>
                <li><p><%= title %>
                    <label class="<%= className %>">
                        <%= statusName %></label></p>
                    <p class="side-text"> <%= dateCreated %> </p>
                </li>
                <% }
                } %>
            </ul>
                <form method="POST" action="${pageContext.request.contextPath}<%=RoutingConstants.USER_DEALS%>">
                    <button type="submit">See all deals</button>
                </form>

            <%if(user.getUserID() == thisId){ %>
                <form method="GET" action="${pageContext.request.contextPath}<%=RoutingConstants.USER_CYCLES%>">
                    <button type="submit">See suggested cycles</button>
                </form>
            <% } %>
        </div>
    </div>

    <%--Footer--%>
    <jsp:include page="/pages/partials/footer.jsp"/>
</body>
</html>
