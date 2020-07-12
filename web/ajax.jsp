<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    var xmlHttp;
    var xmlDOM;
    var data;
    var currentProductId = [];
    var parser = new DOMParser();
    function getXmlHttpObject() {
        var xmlHttp = null;
        try {
            xmlHttp = new XMLHttpRequest();
        } catch (e) {
            try {
                xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
            } catch (e) {
                xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
        }
        return xmlHttp;
    }
    function getRecommend() {
        xmlHttp = getXmlHttpObject();
        if (xmlHttp === null) {
            alert("Your browser is suck");
            return;
        }
        
        var url = "RecommendController?";
        var answer;
        <c:forEach var="question" items="${requestScope.QUESTIONS}">  
            answer = document.querySelector('input[name="question' + '${question.id}' + '"]:checked').value;
            url += "question" + '${question.id}' + "=" + answer + "&";
        </c:forEach>
        url = url.slice(0, -1);
        xmlHttp.onreadystatechange = handleStateChange;
        xmlHttp.open("GET", url, true);
        xmlHttp.send(url);
        console.log("Finding ...");
    }
    function addFavorite() {
        xmlHttp = getXmlHttpObject();
        if (xmlHttp === null) {
            alert("Your browser is suck");
            return;
        }
        var url = "AddFavoriteController";
        if (currentProductId.length === 2) {
            url += "?topId=" + currentProductId[0] + "&bottomId=" + currentProductId[1];
        } else {
            url += "?layerId=" + currentProductId[0] + "&topId=" + currentProductId[1] + "&bottomId=" + currentProductId[2];
        }
        xmlHttp.onreadystatechange = handleStateChange;
        xmlHttp.open("GET", url, true);
        xmlHttp.send(url);
        console.log("Adding ...");
        console.log(url);
    }
    function handleStateChange() {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            data = xmlHttp.responseText;
            if (data !== "") {
                // AddFavoriteController
                if (data === "Added to favorite") {
                    drawAlertSuccess();
                    console.log("Successfully!")
                } 
                // RecommendController
                else {
                    var parser = new DOMParser();
                    xmlDOM = parser.parseFromString(data, "application/xml");
                    // Check if result section is exist
                    var element =  document.getElementById('result-section');
                    if (typeof(element) !== undefined && element !== null) {
                        // Existed -> only apply data to div
                        applyResultByXML();
                    } else {
                        // Not existed -> draw section first then apply data to div
                        drawResultSection();
                        applyResultByXML();
                    }
                    console.log("Got it!")
                }
            } else {
                alert("Server cung deo co dau");
            }
        }
    }
    function drawAlertSuccess() {
        var container = document.getElementById('container-id');
        var alert = 
            '<div class="alert alert-success alert-dismissible fade show" role="alert">' +
                '<strong>Successfully!</strong> The below set of clothes is added successfully to your favorite.' +
                '<button type="button" class="close" data-dismiss="alert" aria-label="Close">' +
                    '<span aria-hidden="true">&times;</span>' +
                '</button>' +
            '</div>';
        container.insertAdjacentHTML('afterbegin', alert);
    }
    function drawResultSection() {
        var questionSection = document.getElementById('question-section');
        var resultSection = 
            '<section id="result-section" class="fdb-block" data-block-type="features" data-id="3" draggable="true">' +
                '<div id="container-id" class="container">' +
                    '<div class="row text-center">' +
                        '<div class="col-12">' +
                            '<h1>We found a set of clothes might suits for you</h1>' +
                        '</div>' +
                    '</div>' +
                    '<div id="result-parent-div" class="row text-center justify-content-center mt-5">' +
                    '</div>' +
                    '<div class="row text-center">' +
                        '<div class="col-12">' +
                            '<button type="button" class="btn btn-lg btn-secondary mt-5 mr-5" onclick="getRecommend();">Another set</button>' +
                            '<button type="button" class="btn btn-lg btn-success mt-5" onclick="addFavorite();">Add to your favorite</button>' +
                        '</div>' +
                    '</div>' +
                '</div>' +
            '</section>';
        questionSection.insertAdjacentHTML('afterend', resultSection);
    }
    function applyResultByXML() {
        // Remove all existed result div
        var element = document.getElementById('result-div');
        while (typeof(element) !== undefined && element !== null) {
            element.parentNode.removeChild(element);
            element = document.getElementById('result-div');
        }
        currentProductId = [];
        // Loop through XML DOM
        var resultParentDiv = document.getElementById('result-parent-div');
        for (var i = 0; i < xmlDOM.getElementsByTagName("product").length; i++) {
            var id = xmlDOM.getElementsByTagName("id")[i*2+1].childNodes[0].nodeValue;
            var name = xmlDOM.getElementsByTagName("name")[i*2+1].childNodes[0].nodeValue;
            var colour = xmlDOM.getElementsByTagName("colour")[i].childNodes[0].nodeValue;
            var url = xmlDOM.getElementsByTagName("url")[i].childNodes[0].nodeValue;
            var image = xmlDOM.getElementsByTagName("image")[i].childNodes[0].nodeValue;

            // Add id to array if user want to add to favorite
            currentProductId.push(id);
            
            // Draw result div
            var resultDiv = 
                '<div id="result-div" class="col-10 col-sm-3 pt-5 pt-sm-0">' +
                    '<img alt="image" class="img-fluid rounded" src="'+ image + '">' +
                    '<a class="nav-link" href="' + url + '"><h3><strong>' + name + ' - ' + colour + '</strong></h3></a>' +
                '</div>';
            resultParentDiv.insertAdjacentHTML('beforeend', resultDiv);
        }
    }
</script>