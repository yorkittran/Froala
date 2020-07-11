<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    var xmlHttp;
    var xmlDOM;
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
        console.log("Finding ...")
    }
    function handleStateChange() {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            data = xmlHttp.responseText;
            if (data !== "") {
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
            } else {
                alert("Server cung deo co dau");
            }
        }
    }
    function drawResultSection() {
        var questionSection = document.getElementById('question-section');
        var resultSection = 
            '<section id="result-section" class="fdb-block" data-block-type="features" data-id="3" draggable="true">' +
                '<div class="container">' +
                    '<div class="row text-center">' +
                        '<div class="col-12">' +
                            '<h1>We found a set of clothes might suits for you</h1>' +
                        '</div>' +
                    '</div>' +
                    '<div id="result-parent-div" class="row text-center justify-content-center mt-5">' +
                    '</div>' +
                    '<div class="row text-center">' +
                        '<div class="col-12">' +
                            '<button type="button" class="btn btn-lg btn-outline-secondary mt-5" onclick="getRecommend();">Another set</button>' +
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
        
        // Loop through XML DOM
        var resultParentDiv = document.getElementById('result-parent-div');
        for (var i = xmlDOM.getElementsByTagName("product").length - 1; i >= 0; i--) {
            var name = xmlDOM.getElementsByTagName("name")[i*2+1].childNodes[0].nodeValue;
            var colour = xmlDOM.getElementsByTagName("colour")[i].childNodes[0].nodeValue;
            var url = xmlDOM.getElementsByTagName("url")[i].childNodes[0].nodeValue;
            var image = xmlDOM.getElementsByTagName("image")[i].childNodes[0].nodeValue;

            // Draw result div
            var resultDiv = 
                '<div id="result-div" class="col-10 col-sm-3 pt-5 pt-sm-0">' +
                    '<img alt="image" class="img-fluid rounded" src="'+ image + '">' +
                    '<a class="nav-link" href="' + url + '"><h3><strong>' + name + ' - ' + colour + '</strong></h3></a>' +
                '</div>';
            resultParentDiv.insertAdjacentHTML('afterbegin', resultDiv);
        }
    }
</script>