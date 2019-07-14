<%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/21/19
  Time: 1:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="models.Cycle" %>
<%@ page import="models.Deal" %>
<%@ page import="java.util.List" %>
<%@ page import="servlets.RoutingConstants" %>
<%@ page import="models.User" %>
<%@ page import="models.ItemImage" %>

<%  Cycle cycle = (Cycle) request.getAttribute("cycle");
    List<Deal> deals = cycle.getDeals();
    String userDealsJson = (String)request.getAttribute("user_deal_ids_json");
    String paramUser = RoutingConstants.PUBLIC_PROFILE;
    String paramDeal = RoutingConstants.SINGLE_DEAL;
%>

<html>
  <jsp:include page="/pages/partials/head.jsp">
      <jsp:param name="title" value="Cycle"/>
  </jsp:include>

  <body>
    <jsp:include page="/pages/partials/navbar.jsp"/>
    <div class="cycleContainer">
        <div class="cycles">
            <div class="button">
                <button id="accept-btn" type="submit" onclick="accept()">Accept Cycle</button>
                <button onclick="rejectCycle()">Reject Cycle</button>
            </div>


            <%for(int i = 0; i < deals.size(); i++){
                Deal deal = deals.get(i);
                %>
                <div class="deal">
                    <%  List<ItemImage> images = deal.getOwnedItems().get(0).getImages();
                        String url;
                        if(images == null || images.isEmpty()) url = "https://cdn.theatlantic.com/assets/media/img/mt/2018/11/shutterstock_552503470/lead_720_405.jpg?mod=1541605820";
                        else url = deal.getOwnedItems().get(0).getImages().get(0).getUrl();
                    %>
                    <img src="<%=url%>">
                    <div class="info">
                        <form method="GET" action="${pageContext.request.contextPath}<%=paramDeal%>">
                            <button type="submit" class = "link">Go to <%=deal.getTitle()%> page</button>
                            <input name="id" type="hidden" value="<%=deal.getDealID()%>"/>
                        </form>
                        <p> <%=deal.getOwnedItems().get(0).getDescription()%> </p>
                        <form method="GET" action="${pageContext.request.contextPath}<%=paramUser%>">
                            <button type="submit" class = "link">owner's profile</button>
                            <input name="id" type="hidden" value="<%=deal.getOwnerID()%>"/>
                        </form>
                    </div>
                </div>

                <%if(i < deals.size() -1){%>
                    <div class="dealArrow">
                        <img src="https://www.stickpng.com/assets/images/58f8bcf70ed2bdaf7c128307.png">
                    </div>
            <%}}%>
        </div>
        <div class="dealUpArrow">
            <img src="https://imageog.flaticon.com/icons/png/512/20/20901.png?size=1200x630f&pad=10,10,10,10&ext=png&bg=FFFFFFFF" alt="up arrow">
        </div>
    </div>


    <jsp:include page="/pages/partials/footer.jsp"/>
  </body>
  <script src="${pageContext.request.contextPath}/assets/js/Http.service.js"></script>
  <script>
      const userDeals = JSON.parse('<%=userDealsJson%>');
      const cycleId = "<%=cycle.getCycleID()%>";
      let notAccepted = true;

      function acceptCycle(deal_id){
          const bag = {};
          bag.cycle_id = cycleId;
          bag.deal_id = deal_id;
          http.POST("<%=RoutingConstants.USER_SINGLE_CYCLE%>", bag)
          .then(data => {
            if(notAccepted) {
                notAccepted = false;
                window.alert(data.message);
                document.getElementById("accept-btn").remove();
            }

          }).catch(reason => {
              console.error(reason);
          });
      }

      function rejectCycle(){
          http.DELETE("<%=RoutingConstants.USER_SINGLE_CYCLE%>", {"cycle_id": cycleId })
              .then(() => {
                  window.alert("Cycle Deleted!");
                  window.location.href = "${pageContext.request.contextPath}<%=RoutingConstants.USER_CYCLES%>";
              })
              .catch(reason => console.error(reason));
      }

      function accept(){
        userDeals.forEach(id => acceptCycle(id));
      }

  </script>
</html>
