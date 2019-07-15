<%@ page import="servlets.RoutingConstants" %>
<%@ page import="models.User" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/21/19
  Time: 1:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%--HEAD--%>
<jsp:include page="/pages/partials/head.jsp">
    <jsp:param name="title" value="User Edit"/>
</jsp:include>

<body>
<%--Navbar--%>
<jsp:include page="/pages/partials/navbar.jsp"/>
<%--Page Content--%>
<div>
    <div id="errors" style="color:red;"></div>
    <% User user = (User) request.getAttribute("user"); %>
    <form>
        <br>
        <label for="password"> Password: </label>
        <input type="password" id="password" name="password">
        <br>
        <label for="first_name"> First Name: </label>
        <input type="text" id="first_name" name="first_name" value="<%=user.getFirstName()%>">
        <br>
        <label for="last_name"> Last Name: </label>
        <input type="text" id="last_name" name="last_name" value="<%=user.getLastName()%>">
        <br>
        <label for="email"> E-Mail: </label>
        <input type="email" id="email" name="email" value="<%=user.getEmail()%>">
        <br>
        <label for="phone_number"> Phone Number: </label>
        <input type="number" id="phone_number" name="phone_number" value="<%=user.getPhoneNumber()%>">
        <br>
        <label for="image">Upload Image:</label>
        <input type="file" name="image" id="image">
        <br>
        <input type="button" class="btn-primary" value="Submit Changes" onclick="submitChanges()">
    </form>
</div>
<%--Footer--%>
<jsp:include page="/pages/partials/footer.jsp"/>
</body>
<script src="${pageContext.request.contextPath}/assets/js/Http.service.js"></script>
<script>
    let base64_image = "";
    //Add listener to image change
    document.getElementById("image").addEventListener("change", getEncodedImage);

    /**
     * After user uploaded image, converts it into base64
     * and stores it in the variable base64_image
     * */
    function getEncodedImage() {
        //Check file present
        if(!this.files || !this.files[0]) return;
        console.log("bla");
        let fileReader = new FileReader();
        fileReader.onload = e => base64_image = e.target.result;
        fileReader.readAsDataURL(this.files[0]);
    }

    function getUserParameters(){
        const user = {};
        user.password = document.getElementById("password").value;
        user.first_name = document.getElementById("first_name").value;
        user.last_name = document.getElementById("last_name").value;
        user.email = document.getElementById("email").value;
        user.phone_number = document.getElementById("phone_number").value;
        return user;
    }

    submitChanges = async () => {
        let errDiv = document.getElementById("errors");
        errDiv.innerHTML = ""; //Remove all prev. errors
        let user = getUserParameters();
        try{
            await http.PUT("<%=RoutingConstants.USER_EDIT%>", user);
            let data = await httpJsonEncoded.POST("<%=RoutingConstants.USER_IMAGES%>", {"base64_image":base64_image});
            console.log(data);
            window.alert("User profile updated!");
        }catch (reason) {
            console.error(reason);
            let errors = JSON.parse(reason.errors);
            if(errors == null) return;
            errDiv.innerHTML = ""; //Remove all prev. errors
            errors.forEach(err => {
                errDiv.innerHTML += '<p>'+err+'</p>';
            })
        }
    }
</script>
</html>