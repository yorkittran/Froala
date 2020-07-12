<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page='header.jsp'>
    <jsp:param name="title" value="Favorite"/>
</jsp:include>
        <section class="fdb-block fp-active" data-block-type="features" data-id="1">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-12 col-lg-6 col-xl-5">
                        <h1>Create your fashion</h1>
                        <p class="lead">Express originality and design your own set of clothes. Style is a way to say who you are without having to speak.</p>
                    </div>
                    <div class="col-12 col-md-8 col-lg-6 m-auto mr-lg-0 ml-lg-auto pt-5 pt-lg-0">
                        <p><img alt="image" class="img-fluid fr-fic fr-dii" src="https://cdn.jsdelivr.net/gh/froala/design-blocks@master/dist/imgs//draws/scrum.svg"></p>
                    </div>
                </div>
            </div>
        </section>
        <c:if test="${requestScope.LISTSETOFCLOTHES != null}">
        <section id="result-section" class="fdb-block" data-block-type="features" data-id="3" draggable="true">
            <div id="container-id" class="container">
                <div class="row text-center">
                    <div class="col-12">
                        <h1>Your collection of clothes</h1>
                    </div>
                </div>
                <c:forEach var="set" items="${requestScope.LISTSETOFCLOTHES}">
                <div class="row text-center justify-content-center mt-5">
                    <c:forEach var="item" items="${set}">
                    <div class="col-10 col-sm-3 pt-5 pt-sm-0">
                        <img alt="image" class="img-fluid rounded" src="${item.image}">
                        <a class="nav-link" href="${item.url}">
                            <h3><strong>${item.name} - ${item.colour}</strong></h3>
                        </a>
                    </div>
                    </c:forEach>
                </div>
                </c:forEach>
            </div>
        </section>
        </c:if>
<jsp:include page='footer.jsp'/>