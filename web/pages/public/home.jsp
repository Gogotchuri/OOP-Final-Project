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
            </ol>

            <div class="carousel-inner">

                <div class="carousel-item active">
                    <img src="${pageContext.request.contextPath}/images/1.jpg" alt="Image can't be displayed">
                </div>
                <div class="carousel-item">
                    <img src="${pageContext.request.contextPath}/images/2.jpg" alt="Image can't be displayed">
                </div>
                <div class="carousel-item">
                    <img src="${pageContext.request.contextPath}/images/3.jpg" alt="Image can't be displayed">
                </div>

            </div>

        </div>

    </div>

    <%--Footer--%>
    <jsp:include page="/pages/partials/footer.jsp"/>
</body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</html>
