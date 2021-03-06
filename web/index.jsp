<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page='header.jsp'>
    <jsp:param name="title" value="Home"/>
</jsp:include>
        <section class="fdb-block" data-block-type="contents" data-id="2" draggable="true">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-10 col-sm-6 col-md-5 col-lg-4 m-auto pb-5 pb-md-0">
                        <img alt="image" class="img-fluid rounded-0" src="https://cdn.jsdelivr.net/gh/froala/design-blocks@master/dist/imgs//draws/iphone-hand.svg">
                    </div>
                    <div class="col-12 ml-md-auto col-md-7 col-lg-6 pb-5 pb-md-0">
                        <img alt="image" class="fdb-icon" src="https://cdn.jsdelivr.net/gh/froala/design-blocks@master/dist/imgs//icons/gift.svg">
                        <h1>Style, your way</h1>
                        <p class="lead">Learn how to appreciate what you have, do more with less, and clear out the clutter in your life. Get dressed easier, each and every day.</p>
                        <p class="mt-4">
                            <a class="btn btn-secondary" href="InitRecommendController">Go find your dress</a>
                        </p>
                    </div>
                </div>
            </div>
        </section>
        <c:if test="${requestScope.SETOFCLOTHES != null}">
        <section class="fdb-block" data-block-type="features" data-id="3" draggable="true">
            <div class="container">
                <div class="row text-center">
                    <div class="col-12">
                        <h1>Most recommended items lately</h1>
                    </div>
                </div>
                <div class="row text-center justify-content-center mt-5">
                    <c:forEach var="item" items="${requestScope.SETOFCLOTHES}">
                        <div class="col-10 col-sm-3">
                            <img alt="image" class="img-fluid rounded" src="${item.image}">
                            <a class="nav-link" href="${item.url}"><h3><strong>${item.name} - ${item.colour}</strong></h3></a>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </section>
        </c:if>
<jsp:include page='footer.jsp'/>