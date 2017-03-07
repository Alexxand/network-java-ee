<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set value="${pageContext.request.contextPath}" var="contextPath"/>

<jsp:useBean id="validation" class="com.github.alexxand.model.validation.FormValidation" scope="request" />

<html>
<head>
    <link rel="stylesheet" href="${contextPath}/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${contextPath}/webjars/flag-icon-css/2.4.0/css/flag-icon.min.css"/>
    <link rel="stylesheet" href="${contextPath}/css/form-signing.css"/>
    <link rel="stylesheet" href="${contextPath}/css/btn-file.css"/>
    <title>Registration</title>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h1 class="text-center login-title">Add photo</h1>
            <div class="account-wall">
                <form class="form-signin" action="${contextPath}/reg/photo" method="post" enctype="multipart/form-data">
                    <c:if test="${validation.errors.INCORRECT_TYPE}">
                        <span class="alert-danger">Photo should be png or jpeg image.</span>
                    </c:if>

                    <c:if test="${validation.errors.FILE_NOT_FOUND}">
                        <span class="alert-danger">You should upload a photo or press a skip button</span>
                    </c:if>

                    <c:if test="${validation.errors.INCORRECT_SIZE}">
                        <span class="alert-danger">Your photo is too large.</span>
                    </c:if>

                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-btn">
                                <span class="btn btn-default btn-file form-control">
                                    Browse… <input type="file" id="imgInp" name="photo">
                                </span>
                            </span>
                        </div>
                        <span class="help-block">You can see the file you upload in the tooltip</span>
                        <span class="help-block"></span>
                        <button type="submit" class="btn btn-lg btn-primary btn-block">Add photo</button>
                    </div>
                </form>
            </div>
            <form action="${contextPath}/reg/photo" method="post">
                <button type="submit" class="center-block btn-link new-account" name="skip-photo" value="true">Skip</button>
            </form>
        </div>
    </div>
</div>

<%@include file="../components/selectLocaleBar.jsp" %>

</body>
</html>
