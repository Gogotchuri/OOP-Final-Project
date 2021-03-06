<%@ page import="servlets.RoutingConstants" %><%--
  Created by IntelliJ IDEA.
  User: gogotchuri
  Date: 6/22/19
  Time: 9:36 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<%--HEAD--%>
<jsp:include page="/pages/partials/head.jsp">
    <jsp:param name="New Deal" value="Profile"/>
</jsp:include>

<body>
<%--Navbar--%>
<jsp:include page="/pages/partials/navbar.jsp"/>
<%--Page Content--%>
<div class="dealFormContainer" style="background-image: url('${pageContext.request.contextPath}/images/Gradient.jpg');">
    <div class="itemContainer">
        <div class="newItemContainer">
            <h3>Add Owned item</h3>
            <div class="newItem">
                <div>
                    <p>Item details:</p>
                    <label for="name">Name:</label><br>
                    <input id="item-name" type="text" required><br>
                    <label for="item-description">Description:</label><br>
                    <textarea id="item-description" type="text" style="height: 30%" required></textarea><br>
                    <label for="itemImage">choose Image:</label><br>
                    <input type="file" id="itemImage" accept="image/*"><br>
                </div>
                <div>
                    <p>Categorise item:</p>
                    <label for="item-type">Type:</label>
                    <br>
                    <select id="item-type" onchange="updateModels()" required></select>
                    <br>
                    <label for="item-manufacturer">Manufacturer (choose or add):</label>
                    <br>
                    <select id="item-manufacturer" onchange="updateModels()" required></select>
                    <input type="text" id="item-manufacturer-name">
                    <br>
                    <label for="item-model">Model (choose or create new):</label><br>
                    <select id="item-model" required></select>
                    <input type="text" id="item-model-name">
                    <br><br>
                    <input type="submit" value="Add Item" onclick="addItem()" style="width: 100px; height: 30px; color:green">
                </div>
            </div>
        </div>
        <div class="dealItems">
            <h3>Owned Items:</h3>
            <div class="tableWrapper">
                <table id="deal-items">
                    <tr>
                        <th>ID:</th>
                        <th>Image:</th>
                        <th>Item name:</th>
                        <th>Item description:</th>
                        <th>Actions:</th>
                    </tr>
                </table>
            </div>
        </div>
    </div>

    <div class="lowerContainer">
        <div class="wantedCategories">
            <div>
                <h3>Add Wanted Item:</h3>
                <label for="type">Type</label>
                <br>
                <select id="type" onchange="updateModels(false)" required></select>
                <br>
                <label for="manufacturer">Manufacturer</label><br>
                <select id="manufacturer" onchange="updateModels(false)" required></select>
                <input type="text" id="manufacturer-name">
                <br>
                <label for="model">Model (choose or create new)</label><br>
                <select id="model" required></select>
                <input type="text" id="model-name">
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
                </table>
            </div>

        </div>
        <div class="dealForm">
            <p>Deal Description</p>
            <label for="name">Deal Name:</label>
            <br>
            <input id="name" type="text" required>
            <br>
            <label for="description">Deal description:</label>
            <br>
            <textarea id="description" required></textarea>
            <br>
            <button onclick="saveDeal()">Save Deal</button>
        </div>
    </div>

</div>
</body>
<script src="${pageContext.request.contextPath}/assets/js/helpers.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/Http.service.js"></script>
<script>
    let base64_image = "";
    let wantedIDs = [];
    let ownedIDs = [];
    fetchCategories();

    //Add listener to image change
    document.getElementById("itemImage").addEventListener("change", getEncodedImage);

    /**
     * After user uploaded image, converts it into base64
     * and stores it in the variable base64_image
     * */
    function getEncodedImage() {
        //Check file present
        if(!this.files || !this.files[0]) return;
        let fileReader = new FileReader();
        fileReader.onload = e => base64_image = e.target.result;
        fileReader.readAsDataURL(this.files[0]);
    }


    /**
     * Gets and validates field information
     * Then posts deal to the server
     */
    function saveDeal(){
        let deal = {};
        deal["name"] = document.getElementById("name").value;
        deal["description"] = document.getElementById("description").value;
        if(deal["name"] === "" || deal["description"] === ""){
            window.alert("Please fill name and description fields!");
            return;
        }
        deal["wanted_ids"] = JSON.stringify(wantedIDs);
        deal["owned_ids"] = JSON.stringify(ownedIDs);
        if(wantedIDs.length === 0 || ownedIDs.length === 0){
            window.alert("You need to have at least one wanted and one given item to make a deal!");
            return;
        }

        console.log(deal);
        http.POST("<%=RoutingConstants.USER_DEALS%>", deal)
            .then(data => {
                console.log(JSON.parse(data.deal));
                window.alert(data.message);
                window.location.href = "${pageContext.request.contextPath}<%=RoutingConstants.DEALS%>";
            })
            .catch(reason => {
                console.error(reason);
            })
    }

    /**
     * Deletes category from frontend
     * @param id category id to delete
     * */
    function deleteCategory(id) {
        wantedIDs = wantedIDs.filter(e => e !== id);
        document.getElementById("wanted-item-"+id).remove();
    }

    /**
     * Given a item, displays it as a wanted item
     * @param wanted item js object
     * */
    function addWantedItem(wanted) {
        let itemsTable = document.getElementById("wanted-items");
        let row = itemsTable.insertRow();
        row.id = "wanted-item-"+wanted.id;
        let idCol = row.insertCell(0);
        let typeCol = row.insertCell(1);
        let manufCol = row.insertCell(2);
        let modelCol = row.insertCell(3);
        let actionCol = row.insertCell(4);
        idCol.innerHTML = wanted.id;
        typeCol.innerHTML = JSON.parse(wanted.type).name;
        manufCol.innerHTML = JSON.parse(wanted.manufacturer).name;
        modelCol.innerHTML = JSON.parse(wanted.model).name;
        actionCol.innerHTML = '<button style="color:red" onclick="deleteCategory('+wanted.id+')">DELETE</button>';
    }

    /**
     * Validates field and then posts category to the server
     * */
    function addCategory() {
        let cat = {};
        cat["type_name"] = document.getElementById("type").value;
        cat["manufacturer_name"] = (document.getElementById("manufacturer-name").value === "") ?
            document.getElementById("manufacturer").value : document.getElementById("manufacturer-name").value;
        cat["model_name"] = (document.getElementById("model-name").value === "") ?
            document.getElementById("model").value : document.getElementById("model-name").value;
        http.POST("<%=RoutingConstants.USER_ITEM_CATEGORIES%>", cat)
            .then(data => {
                let category = JSON.parse(data.category);
                addWantedItem(category);
                wantedIDs.push(category.id);
                fetchCategories();
            })
            .catch(reason => {
                console.error(reason);
            })
    }

    /**
     * Given a boolean to determine place of insertion and the model
     * Adds model entries to model select
     * @param inItems is in items or in categories
     * @param models models to display
     * */
    function displayModels(inItems, models) {
        let modelOptions = inItems ? document.getElementById("item-model") : document.getElementById("model");
        modelOptions.innerHTML = "";
        models.forEach(m => modelOptions.innerHTML += '<option value="'+m.name+'">'+m.name+'</option> \n');
    }

    /**
     * Updates models base on selected type and manufacturer
     * */
    function updateModels(inItems=true){
        let typeName = "";
        let manufacturerName = "";
        if(inItems){
            typeName = document.getElementById("item-type").value;
            manufacturerName = document.getElementById("item-manufacturer").value;
        }else{
            typeName = document.getElementById("type").value;
            manufacturerName = document.getElementById("manufacturer").value;
        }
        http.GET("<%=RoutingConstants.ITEM_CATEGORIES%>?type_name="+typeName+"&manufacturer_name="+manufacturerName)
            .then(data => {
                let models = JSON.parse(data.models);
                displayModels(inItems, models);
            })
            .catch(reason => {
                console.error(reason);
            })

    }

    /**
     * Displays type options
     * */
    function displayTypes(types) {
        let inItemLs = document.getElementById("item-type");
        let inCatLs = document.getElementById("type");
        inCatLs.innerHTML = "";
        inItemLs.innerHTML = "";
        types.forEach(cat => {
            inItemLs.innerHTML += '<option value="'+cat.name+'">'+cat.name+'</option> \n';
            inCatLs.innerHTML += '<option value="'+cat.name+'">'+cat.name+'</option> \n';
        });
    }

    /**
     * Displays manufacturer options
     * */
    function displayManufacturers(manufacturers) {
        let inItemLs = document.getElementById("item-manufacturer");
        let inCatLs = document.getElementById("manufacturer");
        inCatLs.innerHTML = "";
        inItemLs.innerHTML = "";
        manufacturers.forEach(manuf => {
            inItemLs.innerHTML += '<option value="'+manuf.name+'">'+manuf.name+'</option> \n';
            inCatLs.innerHTML += '<option value="'+manuf.name+'">'+manuf.name+'</option> \n';
        });
    }


    /**
     * Getting types and manufacturers from the database
     * */
    function fetchCategories(){
        http.GET("<%=RoutingConstants.ITEM_CATEGORIES%>")
            .then(data => {
                let manufacturers = JSON.parse(data["manufacturers"]);
                let types = JSON.parse(data["types"]);
                displayManufacturers(manufacturers);
                displayTypes(types);
                updateModels(true);
                updateModels(false);
            })
            .catch(reason => {
                if(reason.error != null)
                    console.error(reason.error);
                if(reason.errors != null)
                    console.error(JSON.parse(reason.errors));
            })
    }

    /**
     * Deletes item from the frontend
     * */
    function deleteItem(id){
        http.DELETE("<%=RoutingConstants.USER_SINGLE_ITEM%>?id="+id)
            .then(data => console.log(data))
            .catch(reason => console.error(reason));
        ownedIDs = ownedIDs.filter(e => e !== id);
        document.getElementById("deal-item-"+id).remove();
    }

    function getItemData(){
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
            return null;
        }
        if(item["description"] === "") {
            window.alert("Please enter the description first!");
            return null;
        }
        return item;
    }

    function displayItem (item) {
        let itemsTable = document.getElementById("deal-items");
        let row = itemsTable.insertRow();
        row.id = "deal-item-"+item.id;
        let idCol = row.insertCell(0);
        let imageCol = row.insertCell(1);
        let nameCol = row.insertCell(2);
        let descCol = row.insertCell(3);
        let actionCol = row.insertCell(4);
        idCol.innerHTML = item.id;
        imageCol.innerHTML = '<img src="${pageContext.request.contextPath}' +item.image_url + '" style="width:11vw;">';
        nameCol.innerHTML = item.name;
        descCol.innerHTML = item.description;
        actionCol.innerHTML = '<button style="color:red" onclick="deleteItem('+item.id+')">DELETE</button>';
    }

    addItem = async () => {
        let item = getItemData();
        if(item == null) return;
        if(base64_image === ""){
            window.alert("Please select an image first!");
            return;
        }
        try{
            let itemReq = await http.POST("<%=RoutingConstants.USER_ITEMS%>", item);
            let itemObj = JSON.parse(itemReq.item);
            let imageReq = await httpJsonEncoded.POST("<%=RoutingConstants.USER_ITEM_IMAGES%>?item_id="+itemObj.id, {"base64_image":base64_image});
            itemObj.image_url = imageReq.image_url;
            displayItem(itemObj);
            ownedIDs.push(itemObj.id);
            fetchCategories();//Refresh categories

        }catch (reason) {
            console.error(reason);
        }
    }
</script>
</html>