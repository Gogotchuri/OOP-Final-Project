<%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/21/19
  Time: 11:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="models.User" %>
<html>
<% User user = (User) session.getAttribute("user"); %>
    <%--HEAD--%>
    <jsp:include page="/pages/partials/head.jsp">
        <jsp:param name="title" value="chats"/>
    </jsp:include>

<body>
    <%--Navbar--%>
    <jsp:include page="/pages/partials/navbar.jsp"/>
    <%--Page Content--%>
    <div class="chats-container">
        <div class="chats">
            <div class="col-header">
                <h5>Chats</h5>
            </div>
            <div class="chats-list">
                <!-- Repetable -->
                <div class="chat-thumbnail">
                    <img src="http://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908">
                    <div class="thumbnail-body">
                        <h5>სამსუნგის ჩათი</h5>
                        <p>ვასო, სოსო, ტასო</p>
                    </div>
                </div>
                <div class="chat-thumbnail">
                    <img src="http://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908">
                    <div class="thumbnail-body">
                        <h5>სამსუნგის ჩათი</h5>
                        <p>ვასო, სოსო, ტასო</p>
                    </div>
                </div>
                <div class="chat-thumbnail">
                    <img src="http://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908">
                    <div class="thumbnail-body">
                        <h5>სამსუნგის ჩათი</h5>
                        <p>ვასო, სოსო, ტასო</p>
                    </div>
                </div>
                <!-- Repetable -->
                <div class="chat-thumbnail">
                    <img src="http://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908">
                    <div class="thumbnail-body">
                        <h5>სამსუნგის ჩათი</h5>
                        <p>ვასო, სოსო, ტასო</p>
                    </div>
                </div>
                <div class="chat-thumbnail">
                    <img src="http://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908">
                    <div class="thumbnail-body">
                        <h5>სამსუნგის ჩათი</h5>
                        <p>ვასო, სოსო, ტასო</p>
                    </div>
                </div>
                <div class="chat-thumbnail">
                    <img src="http://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908">
                    <div class="thumbnail-body">
                        <h5>სამსუნგის ჩათი</h5>
                        <p>ვასო, სოსო, ტასო</p>
                    </div>
                </div>
                <!-- Repetable -->
                <div class="chat-thumbnail">
                    <img src="http://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908">
                    <div class="thumbnail-body">
                        <h5>სამსუნგის ჩათი</h5>
                        <p>ვასო, სოსო, ტასო</p>
                    </div>
                </div>
                <div class="chat-thumbnail">
                    <img src="http://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908">
                    <div class="thumbnail-body">
                        <h5>სამსუნგის ჩათი</h5>
                        <p>ვასო, სოსო, ტასო</p>
                    </div>
                </div>
                <div class="chat-thumbnail">
                    <img src="http://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908">
                    <div class="thumbnail-body">
                        <h5>სამსუნგის ჩათი</h5>
                        <p>ვასო, სოსო, ტასო</p>
                    </div>
                </div>
                <!-- Repetable -->
                <div class="chat-thumbnail">
                    <img src="http://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908">
                    <div class="thumbnail-body">
                        <h5>სამსუნგის ჩათი</h5>
                        <p>ვასო, სოსო, ტასო</p>
                    </div>
                </div>
                <div class="chat-thumbnail">
                    <img src="http://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908">
                    <div class="thumbnail-body">
                        <h5>სამსუნგის ჩათი</h5>
                        <p>ვასო, სოსო, ტასო</p>
                    </div>
                </div>
                <div class="chat-thumbnail">
                    <img src="http://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908">
                    <div class="thumbnail-body">
                        <h5>სამსუნგის ჩათი</h5>
                        <p>ვასო, სოსო, ტასო</p>
                    </div>
                </div>

            </div>
        </div>
        <div class="conversation">
            <div class="col-header">
                <h5>Conversation</h5>
            </div>
            <div id="messages" class="messages" >
                <div class="message">
                    <img src="https://vignette.wikia.nocookie.net/pokemon/images/3/3e/039Jigglypuff.png/revision/latest?cb=20140328193313">
                    <div class="message-bubble">პიკა პიკა პიკა, პიკა პიკა?</div>
                </div>
                <div class="own-message">
                    <img src="http://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908">
                    <div class="message-bubble">პიკა პიკა, პიკა?</div>
                </div>
                <div class="message">
                    <img src="https://vignette.wikia.nocookie.net/pokemon/images/3/3e/039Jigglypuff.png/revision/latest?cb=20140328193313">
                    <div class="message-bubble">პიკა, პიკა?</div>
                </div>
                <div class="own-message">
                    <img src="http://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908">
                    <div class="message-bubble">პიკა, პიკა პიკა?</div>
                </div>
                <div class="message">
                    <img src="https://vignette.wikia.nocookie.net/pokemon/images/3/3e/039Jigglypuff.png/revision/latest?cb=20140328193313">
                    <div class="message-bubble">
                        <span class="message-author">ილია</span>
                        <span class="message-text">ვიცი რომ ამ პოკემონს სხვა ხმა აქვს ხო...</span>
                        <span class="message-date"> 10/10/1000 10:10</span>
                    </div>
                </div>
                <div class="own-message">
                    <img src="http://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908">
                    <div class="message-bubble">
                        <span class="message-author">ილია</span>
                        <span class="message-text">ვიცი რომ ამ პოკემონს სხვა ხმა აქვს ხო...</span>
                        <span class="message-date"> 10/10/1000 10:10</span>
                    </div>
                </div>
            </div>
            <div class="message-send">
                <textarea id="msg-text" rows="5"></textarea>
                <input class="btn-primary send-btn" type="button" value="send" onclick="sendMessage()">
            </div>
        </div>
        <div class="parameters">
            <div class="col-header">
                <h5>Parameters</h5>
            </div>
            <div>

            </div>
        </div>
    </div>
</body>
    <script src="${pageContext.request.contextPath}/assets/js/chat.js"></script>
<script>
    const chat_id = 1;
    const localUserId = "<%=user.getId()%>";
    const localUserName = "<%=user.getUsername()%>";
    const token = "<%=user.getUsername()%>"; //TODO change to valid token
    const endpoint_addr = "ws://localhost:8080/OOP_Final_Project/user/chats/" + chat_id + "/" + token;

    //Callback to handle receiving messages from server
    let handleMessage = msg => {
        console.log(msg);
        let parsedMessage = JSON.parse(msg.data);
        let messageClass = (parsedMessage.user_id === localUserId) ? "own-message" : "message";
        let messages = document.getElementById("messages");
        let newMessageElement = '<div class="' + messageClass + '"> \n' +
            '<img src="http://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908"> \n'+
            '<div class="message-bubble"> \n' +
                '<span class="message-author">' + parsedMessage.username+'</span>' +
                '<span class="message-text">' + parsedMessage.body + '</span>' +
                '<span class="message-date">' + parsedMessage.date.substr(0, 19) + '</span>' +
            '</div> \n' + '</div>';
        let currMessages = messages.innerHTML;
        messages.innerHTML = currMessages + newMessageElement;
        messages.scrollTop = messages.scrollHeight;
    };

    const chatSocket = new ChatSocket(endpoint_addr, handleMessage);

    //Callback to act when send button is pressed
    let sendMessage = () => {
        let text = document.getElementById("msg-text").value;
        if(text === "") return;
        chatSocket.send(text);
    };
</script>
</html>
