<%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/21/19
  Time: 11:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="models.Chat" %>
<%@ page import="models.User" %>
<%@ page import="java.util.List" %>
<html>
<%  User user = (User) session.getAttribute("user");
    List<Chat> chats = (List<Chat>) request.getAttribute("chats");
%>
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
                <%for(Chat ch : chats){%>
                    <div id="chat-<%=ch.getChatID()%>" class="chat-thumbnail" onclick="changeChat(<%=ch.getChatID()%>)">
                        <img src="http://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908">
                        <div class="thumbnail-body">
                            <h5>ჩათი ნომერი <%=ch.getChatID()%></h5>
                            <p>
                                <%for(String name : ch.getParticipantNames()){%>
                                    <%=name%>,
                                <%}%>
                            </p>
                        </div>
                    </div>
                <%}%>
            </div>
        </div>
        <div class="conversation">
            <div class="col-header">
                <h5>Conversation</h5>
            </div>
            <div id="messages" class="messages" ></div>
            <div class="message-send" id="message-send" style="display: none">
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
    <script src="${pageContext.request.contextPath}/assets/js/helpers.js"></script>
<script>

    //Variables
    const http = new HTTP("http://localhost:8080/OOP_Final_Project");
    let chatSocket = null;
    const localUserId = "<%=user.getUserID()%>";


    //functions
    let getChatEndpointAddr = chatID => "ws://localhost:8080/OOP_Final_Project/user/chats/" + chatID + "/" + "<%=user.getUsername()%>";

    let handleMessage = parsedMessage => {
        let messageClass = (parsedMessage.user_id == localUserId) ? "own-message" : "message";
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

    //Callback to handle receiving messages from server
    let handleRawMessage = msg => {
        console.log(msg);
        let parsedMessage = JSON.parse(msg.data);
        handleMessage(parsedMessage)
    };


    //Callback to act when send button is pressed
    let sendMessage = () => {
        let text = document.getElementById("msg-text").value;
        if(text === "") return;
        chatSocket.send(text);
    };


    let changeChat = (chat_id) => {
        if(chatSocket != null) chatSocket.close(); //Close connection to old chat
        chatSocket = new ChatSocket(getChatEndpointAddr(chat_id), handleRawMessage);
        document.getElementById("message-send").style.display = ""; //Show input form when user asks for a chat
        http.GET("/user/chats/show?id=" + chat_id)
            .then(data => {
                let messages = JSON.parse(data.messages);
                document.getElementById("messages").innerHTML = "";
                messages.forEach(message => {
                    handleMessage(message);
                })
            })
            .catch(err => console.error(err));
    }
</script>
</html>
