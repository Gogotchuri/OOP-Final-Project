<%@ page import="java.util.*" %>
<%@ page import="models.Cycle" %>
<%@ page import="managers.DealsManager" %>
<%@ page import="models.Deal" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="servlets.RoutingConstants" %><%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/21/19
  Time: 1:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<%--HEAD--%>
<jsp:include page="/pages/partials/head.jsp">
    <jsp:param name="title" value="Cycles"/>
</jsp:include>

<body>
<%--Navbar--%>
<jsp:include page="/pages/partials/navbar.jsp"/>
<%--Page Content--%>
    <% List<Cycle> cycles = (List<Cycle>)request.getAttribute("cycles");%>
    <% int userID = (int)request.getAttribute("userID");
    String param = RoutingConstants.USER_SINGLE_CYCLE;%>

    <div class="cycles-wrapper">
        <%if(cycles.size() == 0){%>
            <div class="cycle-div">
                <p>No suggested cycles</p>
            </div>
        <%}%>
        <% for(Cycle c : cycles){%>
            <div class="cycle-div">
                <% int cycleID = c.getCycleID();%>
                <% List<Deal> deals = c.getUserDeals(userID);%>
                <% for(Deal d : deals){%>
                    <h3><%= d.getTitle()%></h3>
                <%}%>
                <%List<Deal> allDeals = c.getDeals();%>
                <p><%= allDeals.size()%> participants in deal</p>
                <% String statusName = c.getCycleStatus().getName();%>
                <p><%= statusName%></p>
                <%--<% String cycleCreated = new SimpleDateFormat("yyyy.MM.dd").format(c.);%>
                <p>date_created</p>--%>

                <form method="GET" action="${pageContext.request.contextPath}<%=param%>">
                    <button type="submit">Go to cycle page</button>
                    <input name="cycle_id" type="hidden" value="<%=cycleID%>"/>
                    <input name="deal_id" type="hidden" value="<%=cycleID%>"/>
                </form>
            </div>
        <%}%>
    </div>
<%--Footer--%>
<jsp:include page="/pages/partials/footer.jsp"/>
</body>
</html>
