<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page='header.jsp'>
    <jsp:param name="recommend" value="true"/>
</jsp:include>
        <section class="fdb-block" data-block-type="contents" data-id="1" draggable="true">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-12 col-md-7 col-lg-5 ml-md-auto">
                        <h1>Personal Styling</h1>
                        <p class="lead">Receive styled outfits or a selection of clothes from some quiz below. Get dressed easier, each and every day.</p>
                    </div>
                    <div class="col-10 col-sm-6 col-md-5 col-lg-4 m-auto pt-5 pt-md-0">
                        <img alt="image" class="img-fluid rounded-0" src="https://cdn.jsdelivr.net/gh/froala/design-blocks@master/dist/imgs//draws/customer-survey.svg">
                    </div>
                </div>
            </div>
        </section>
        <section id="question-section" class="fdb-block bg-gray" data-block-type="forms" data-id="2" draggable="true">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-12 col-md-10 col-lg-8 text-center">
                        <img alt="image" height="40" src="https://cdn.jsdelivr.net/gh/froala/design-blocks@master/dist/imgs//icons/layers.svg">
                        <div id="carousel" class="carousel slide" data-interval="false">
                            <div class="carousel-inner">
                                <c:forEach var="question" items="${requestScope.QUESTIONS}" varStatus="count">
                                    <c:if test="${count.count == 1}">
                                    <div class="carousel-item active">
                                    </c:if>
                                    <c:if test="${count.count != 1}">
                                    <div class="carousel-item">
                                    </c:if>
                                        <h2 class="mt-4 text-left">${question.name}</h2>
                                        <c:forEach var="answer" items="${requestScope.ANSWERS}">
                                            <c:if test = "${answer.questionId.id == question.id}">
                                                <div class="form-check text-left">
                                                    <input class="form-check-input" type="radio" id="question${question.id}" name="question${question.id}" value="answer${answer.id}">
                                                    <label class="form-check-label">${answer.name}</label>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <button type="button" class="btn btn-lg btn-outline-primary mt-5" onclick="getRecommend();">Find your set</button>
                    </div>
                    <a class="carousel-control-prev" href="#carousel" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carousel" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
        </section>
<jsp:include page='footer.jsp'/>