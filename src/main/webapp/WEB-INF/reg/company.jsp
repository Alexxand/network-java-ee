<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<jsp:useBean id="validation" class="com.github.alexxand.model.validation.FormValidation" scope="request"/>
<jsp:useBean id="data"  class="com.github.alexxand.model.CompanyInf" scope="request"/>

<html>
<head>
    <link rel="stylesheet" href="${contextPath}/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${contextPath}/webjars/flag-icon-css/2.4.0/css/flag-icon.min.css"/>
    <link rel="stylesheet" href="${contextPath}/css/form-signing.css" />
    <title>Registration</title>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h1 class="text-center login-title">Registration Form</h1>
            <div class="account-wall">
                <form class="form-signin" action="${contextPath}/reg/company" method="post">
                    <div
                            <c:if test="${validation.fields.company.invalid}">
                                class="has-error"
                            </c:if>
                    >
                        <input type="text" placeholder="Your organization" class="form-control"
                               name="company" value="<c:out value='${data.company}'/>">
                        <c:if test="${validation.fields.company.emptyField}">
                            <span class="help-block">Organization title should not be empty</span>
                        </c:if>
                        <c:if test="${validation.fields.company.incorrect}">
                            <span class="help-block">Organization title should be blablabla</span>
                        </c:if>
                    </div>
                    <div
                            <c:if test="${validation.fields.position.invalid}">
                                class="has-error"
                            </c:if>
                    >
                        <input type="text" placeholder="Your position" class="form-control"
                               name="position" value="<c:out value='${data.position}'/>">
                        <c:if test="${validation.fields.position.emptyField}">
                            <span class="help-block">Position title should not be empty</span>
                        </c:if>
                        <c:if test="${validation.fields.position.incorrect}">
                            <span class="help-block">Position title should be blablabla</span>
                        </c:if>
                    </div>
                    <button type="submit" class="btn btn-lg btn-primary btn-block">Continue</button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="../components/selectLocaleBar.jsp" %>

</body>
</html>

