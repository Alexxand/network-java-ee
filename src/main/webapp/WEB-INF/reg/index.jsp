<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<jsp:useBean id="validation" class="com.github.alexxand.model.validation.FormValidation" scope="request"/>
<jsp:useBean id="data"  class="com.github.alexxand.model.BasicManagerInf" scope="request"/>
<html>
<head>
    <link rel="stylesheet" href="${contextPath}/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${contextPath}/webjars/flag-icon-css/2.4.0/css/flag-icon.min.css"/>
    <style type="text/css">
        .form-signin {
            max-width: 330px;
            padding: 15px;
            margin: 0 auto;
        }

        .form-signin .form-signin-heading, .form-signin .checkbox {
            margin-bottom: 10px;
        }

        .form-signin .checkbox {
            font-weight: normal;
        }

        .form-signin .form-control {
            position: relative;
            font-size: 16px;
            height: auto;
            padding: 10px;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
        }

        .form-signin .form-control:focus {
            z-index: 2;
        }

        .form-signin input[type="text"] {
            margin-bottom: -1px;
            border-bottom-left-radius: 0;
            border-bottom-right-radius: 0;
        }

        .form-signin input[type="password"] {
            margin-bottom: 10px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
        }

        .account-wall {
            margin-top: 20px;
            padding: 40px 0px 20px 0px;
            background-color: #f7f7f7;
            -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        }

        .login-title {
            color: #555;
            font-size: 18px;
            font-weight: 400;
            display: block;
        }

        .profile-img {
            width: 96px;
            height: 96px;
            margin: 0 auto 10px;
            display: block;
            -moz-border-radius: 50%;
            -webkit-border-radius: 50%;
            border-radius: 50%;
        }

        .need-help {
            margin-top: 10px;
        }

        .new-account {
            display: block;
            margin-top: 10px;
        }
    </style>
    <title>Registration</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h1 class="text-center login-title">Registration Form</h1>
            <div class="account-wall">
                <form class="form-signin" action="${contextPath}/reg" method="post">
                    <%--<c:if test="${validation.errors.DIFF_LANGUAGE}">
                        <span class="alert-danger">Your name and your last name should be written in the same language</span>
                    </c:if>--%>
                    <div
                            <c:if test="${validation.fields.name.invalid}">
                                class="has-error"
                            </c:if>
                    >
                        <input type="text" placeholder="First Name" class="form-control"
                                name="name" value="<c:out value='${data.name}'/>">
                        <c:if test="${validation.fields.name.emptyField}">
                            <span class="help-block">Name should not be empty</span>
                        </c:if>
                        <c:if test="${validation.fields.name.incorrect}">
                            <span class="help-block">Name should be blablabla</span>
                        </c:if>
                    </div>
                    <div
                            <c:if test="${validation.fields.lastName.invalid}">
                                class="has-error"
                            </c:if>
                    >
                        <input type="text" placeholder="Last Name" class="form-control"
                               name="lastName" value="<c:out value='${data.lastName}'/>">
                        <c:if test="${validation.fields.lastName.emptyField}">
                            <span class="help-block">Last name should not be empty</span>
                        </c:if>
                        <c:if test="${validation.fields.lastName.incorrect}">
                            <span class="help-block">Last name should be blablabla</span>
                        </c:if>
                    </div>
                    <span class="help-block"></span>
                    <div
                            <c:if test="${validation.fields.email.invalid || validation.errors.EMAIL_EXISTS}">
                                class="has-error"
                            </c:if>
                    >
                        <input type="text" placeholder="Email" class="form-control"
                               name="email" value="<c:out value='${data.email}'/>">
                        <c:if test="${validation.fields.email.emptyField}">
                            <span class="help-block">email should not be empty</span>
                        </c:if>
                        <c:if test="${validation.fields.email.incorrect}">
                            <span class="help-block">email should be blablabla</span>
                        </c:if>
                        <c:if test="${validation.errors.EMAIL_EXISTS}">
                            <span class="help-block">This e'mail has already registered</span>
                        </c:if>
                    </div>
                    <div
                            <c:if test="${validation.fields.password.invalid}">
                                class="has-error"
                            </c:if>
                    >
                        <input type="password" placeholder="Password" class="form-control"
                               name="password" value="<c:out value='${data.password}'/>">
                        <c:if test="${validation.fields.password.emptyField}">
                            <span class="help-block">Password should not be empty</span>
                        </c:if>
                        <c:if test="${validation.fields.password.incorrect}">
                            <span class="help-block">Password should be blablabla</span>
                        </c:if>
                    </div>
                    <button type="submit" class="btn btn-lg btn-primary btn-block">Continue</button>
                </form>
            </div>
            <a href="${contextPath}/login" class="text-center new-account">Login</a>
        </div>
    </div>
</div>

<%@include file="../components/selectLocaleBar.jsp" %>

</body>
</html>
