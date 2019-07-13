<%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/14/19
  Time: 2:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <%--HEAD--%>
    <jsp:include page="/pages/partials/head.jsp">
        <jsp:param name="title" value="HOME"/>
    </jsp:include>

<body>
    <%--Navbar--%>
    <jsp:include page="/pages/partials/navbar.jsp"/>
    <%--Page Content--%>

    <div class="slideshow">

        <div id="myCarousel" class="carousel slide" data-ride="carousel">

            <ol class="carousel-indicators">
                <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                <li data-target="#myCarousel" data-slide-to="1"></li>
                <li data-target="#myCarousel" data-slide-to="2"></li>
                <li data-target="#myCarousel" data-slide-to="3"></li>
            </ol>

            <div class="carousel-inner">

                <div class="carousel-item active">
                    <img src="../../images/a.jpg" alt="a">
                </div>
                <div class="carousel-item">
                    <img src="../../images/b.jpg" alt="b">
                </div>
                <div class="carousel-item">
                    <img src="../../images/c.jpg" alt="c">
                </div>
                <div class="carousel-item">
                    <img src="../../images/d.jpg" alt="d">
                </div>

            </div>

        </div>

    </div>

    <%--Footer--%>
    <jsp:include page="/pages/partials/footer.jsp"/>
</body>
</html>
