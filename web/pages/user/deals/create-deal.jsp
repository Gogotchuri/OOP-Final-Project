<%@ page import="servlets.RoutingConstants" %><%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/22/19
  Time: 9:36 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%--HEAD--%>
<jsp:include page="/pages/partials/head.jsp">
    <jsp:param name="New Deal" value="Profile"/>
</jsp:include>

<body>
<%--Navbar--%>
<jsp:include page="/pages/partials/navbar.jsp"/>
<%--Page Content--%>
<div class="dealFormContainer">
    <div class="itemContainer">
        <div class="newItemContainer">
            <h3>Add Owned item</h3>
            <div class="newItem">
                <div>
                    <p>Item details:</p>
                    <form>
                        <label for="name">Name:</label><br>
                        <input id="item-name" type="text" required><br>
                        <label for="item-description">Description:</label><br>
                        <textarea id="item-description" type="text" style="height: 30%" required></textarea><br>
                        <label for="itemImage">choose Image:</label><br>
                        <input type="file" id="itemImage" accept="image/*"><br>
                    </form>
                </div>
                <div>
                    <p>Categorise item:</p>
                    <label for="item-type">Type:</label><br>
                    <select id="item-type" required>
                        <option value="type1">Type1</option>
                        <option value="type2">Type2</option>
                        <option value="type3">Type3</option>
                    </select><br>
                    <label for="item-manufacturer">Manufacturer (choose or add):</label><br>
                    <select id="item-manufacturer" required>
                        <option value="maunf1">manufacturer1</option>
                        <option value="manuf2">manufacturer2</option>
                        <option value="manuf3">manufacturer3</option>
                    </select>
                    <input type="text" id="item-manufacturer-name">
                    <br>
                    <label for="item-model">Model (choose or create new):</label><br>
                    <select id="item-model" required>
                        <option value="model1">model1</option>
                        <option value="model2">model2</option>
                        <option value="model3">model3</option>
                    </select>
                    <input type="text" id="item-model-name">
                    <br><br>
                    <input type="submit" value="Add Item" onclick="addItem()" style="width: 100px; height: 30px; color:green">
                </div>
            </div>
        </div>
        <div class="dealItems">
            <h3>Owned Items:</h3>
            <div class="tableWrapper">
                <table>
                    <tr>
                        <th>ID:</th>
                        <th>Image:</th>
                        <th>Item name:</th>
                        <th>Item description:</th>
                        <th>Actions:</th>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td>
                            <img src="http://bandtanimalkingdom.weebly.com/uploads/3/9/9/4/39943199/s320981046200626688_p7_i1_w500.jpeg" style="width:11vw">
                        </td>
                        <td>Item 1</td>
                        <td>Lorem ipsum dolor sit amet consectetur adipisicing elit. Unde reiciendis ipsa ipsam quisquam minima maiores necessitatibus</td>
                        <td><button style="color:red">DELETE</button></td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>
                            <img src="http://bandtanimalkingdom.weebly.com/uploads/3/9/9/4/39943199/s320981046200626688_p7_i1_w500.jpeg" style="width:11vw">
                        </td>
                        <td>Item 2</td>
                        <td>Lorem ipsum dolor sit amet consectetur adipisicing elit. Unde reiciendis ipsa ipsam quisquam minima maiores necessitatibus</td>
                        <td><button style="color:red">DELETE</button></td>

                    </tr>
                    <tr>
                        <td>3</td>
                        <td>
                            <img src="http://bandtanimalkingdom.weebly.com/uploads/3/9/9/4/39943199/s320981046200626688_p7_i1_w500.jpeg" style="width:11vw">
                        </td>
                        <td>Item 3</td>
                        <td>Lorem ipsum dolor sit amet consectetur adipisicing elit. Unde reiciendis ipsa ipsam quisquam minima maiores necessitatibus</td>
                        <td><button style="color:red">DELETE</button></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>

    <div class="lowerContainer">
        <div class="wantedCategories">
            <div>
                <h3>Add Wanted Item:</h3>
                <label for="type">Type</label><br>
                <select id="type">
                    <option>Type1</option>
                    <option>Type2</option>
                    <option>Type3</option>
                </select><br>
                <label for="manufacturer">Manufacturer</label><br>
                <select id="manufacturer">
                    <option>manufacturer1</option>
                    <option>manufacturer2</option>
                    <option>manufacturer3</option>
                </select><br>
                <label for="model">Model (choose or create new)</label><br>
                <select id="model">
                    <option>model1</option>
                    <option>model2</option>
                    <option>model3</option>
                </select>
                <input type="text" id="category-name">
                <br><br>
                <input type="button" value="Add Wanted" onclick="addCategory()" style="width: 100px; height: 30px; color:green">
            </div>
        </div>
        <div class="categoryTableContainer">
            <p>Wanted Items:</p>
            <div class="tableWrapper">
                <table id="wanted-items">
                    <tr>
                        <th>ID</th>
                        <th>Type</th>
                        <th>Manufacturer</th>
                        <th>Model</th>
                        <th>Actions:</th>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td>Type1</td>
                        <td>Manuf1</td>
                        <td>Model1</td>
                        <td><button>Delete</button></td>
                    </tr>
                </table>
            </div>

        </div>
        <div class="dealForm">
            <p>Deal Description</p>
            <form>
                <label for="name">Deal Name:</label>
                <br>
                <input id="name" type="text">
                <br>
                <label for="description">Deal description:</label>
                <br>
                <textarea id="description"></textarea>
                <br>
                <button>Save Deal</button>
            </form>
        </div>
    </div>

</div>
</body>
<script src="${pageContext.request.contextPath}/assets/js/helpers.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/Http.service.js"></script>
<script>
    let addItem = () => {
        let item = {};
        item["name"] = document.getElementById("item-name").value;
        item["description"] = document.getElementById("item-description").value;
        item["type_name"] = document.getElementById("item-type").value;
        item["manufacturer_name"] = (document.getElementById("item-manufacturer-name").value === "") ?
            document.getElementById("item-manufacturer").value : document.getElementById("item-manufacturer-name").value;
        item["model_name"] = (document.getElementById("item-model-name").value === "") ?
            document.getElementById("item-model").value : document.getElementById("item-model-name").value;

        if(item["name"] === "") {
            window.alert("Please enter the name first!");
            return;
        }
        if(item["description"] === "") {
            window.alert("Please enter the description first!");
            return;
        }

        http.POST("<%=RoutingConstants.USER_ITEMS%>", item)
            .then(data => console.log(data))
            .catch(reason => {
                if(reason.error != null)
                    console.error(reason.error);
                if(reason.errors != null)
                    console.error(JSON.parse(reason.errors));
            });
    }
</script>
</html>