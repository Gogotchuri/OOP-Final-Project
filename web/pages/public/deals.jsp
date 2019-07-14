<%@ page import="java.util.List" %>
<%@ page import="models.Deal" %><%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/14/19
  Time: 2:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  List<Deal> deals = (List<Deal>) request.getAttribute("deals");
    String next_page_url = (String) request.getAttribute("next_page_url");
    String prev_page_url = (String) request.getAttribute("prev_page_url");
    int curr_page = (int) request.getAttribute("curr_page_num");
%>
<html>
<%--HEAD--%>
    <jsp:include page="/pages/partials/head.jsp">
        <jsp:param name="title" value="Deals"/>
    </jsp:include>

<body>
    <%--Navbar--%>
    <jsp:include page="/pages/partials/navbar.jsp"/>
    <%--Page Content--%>
    <div class="deals">
        <%for(Deal deal: deals){%>
            <div class="card">
                <img class="card-img" src="https://images.pexels.com/photos/414612/pexels-photo-414612.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500">
                <a class="card-title" href="${pageContext.request.contextPath}/deals/show?id=<%=deal.getDealID()%>"><%=deal.getTitle()%></a>
                <p class="card-text"><%=deal.getDescription()%></p>
            </div>
        <%}%>
    </div>
    <div class="pagination-box">
        <div class="pagination">

            <%if(prev_page_url == null || prev_page_url.isEmpty()){%>
                <a class="unselectable"> ← previous</a>
            <%}else{%>
                <a href="${pageContext.request.contextPath}<%=prev_page_url%>"> ← Previous</a>
            <%}%>

            <%for(int i = 1; i <= (int)request.getAttribute("last_page_num"); i++){
                if(curr_page == i){%>
                    <a class="selected"><%=i%></a>
                <%}else{%>
                    <%--TODO Might need to change, so it takes other criterias with it--%>
                    <a href="${pageContext.request.contextPath}/deals?page=<%=i%>"><%=i%></a>
            <%}}%>

            <%if(next_page_url == null || next_page_url.isEmpty()){%>
                <a class="unselectable"> next →</a>
            <%}else{%>
                <a href="${pageContext.request.contextPath}<%=next_page_url%>"> next →</a>
            <%}%>
        </div>
    </div>
    <%--Footer--%>
     <jsp:include page="/pages/partials/footer.jsp"/>
</body>
</html>
