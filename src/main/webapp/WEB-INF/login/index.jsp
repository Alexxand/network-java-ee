<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set value="${pageContext.request.contextPath}" var="contextPath"/>

<jsp:useBean id="validation" class="com.github.alexxand.model.validation.FormValidation" scope="request"/>
<jsp:useBean id="data"  class="com.github.alexxand.model.Credentials" scope="request"/>

<html>
<head>
    <link rel="stylesheet" href="${contextPath}/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${contextPath}/webjars/flag-icon-css/2.4.0/css/flag-icon.min.css"/>
    <link rel="stylesheet" href="${contextPath}/css/form-signing.css" />
    <title>Login</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h1 class="text-center login-title">Log in to continue</h1>
            <div class="account-wall">
                <img class="profile-img" src="https://lh5.googleusercontent.com/-b0-k99FZlyE/AAAAAAAAAAI/AAAAAAAAAAA/eu7opA4byxI/photo.jpg?sz=120" alt="">
                <form class="form-signin" action="${contextPath}/login" method="post">
                    <c:if test="${validation.errors.INVALID_CREDENTIALS}">
                        <span class="alert-danger">Invalid email or password</span>
                    </c:if>
                    <input type="text" class="form-control" placeholder="Email" name="email" value="<c:out value='${data.email}'/>">
                    <input type="password" class="form-control" placeholder="Password" name="password" value="<c:out value='${data.password}'/>">
                    <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
                </form>
            </div>
            <a href="${contextPath}/reg" class="text-center new-account">Register</a>
        </div>
    </div>
</div>

<%@include file="../components/selectLocaleBar.jsp" %>

</body>
</html>

