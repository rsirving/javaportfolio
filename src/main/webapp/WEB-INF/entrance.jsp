<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Scriptions Entrance</title>
		<link href="https://fonts.googleapis.com/css?family=Roboto|Poiret+One" rel="stylesheet">
		<link href="/css/style.css" rel="stylesheet" >
		<link href="/css/scriptions.css" rel="stylesheet">
	</head>

	<body>

		<div class="flexBox">
			<div class="entrBlock">
				<h1>Login</h1>
				<c:if test="${logoutMessage != null}">
					<p class="error"><c:out value="${logoutMessage}" /></p>
				</c:if>
				<c:if test="${successMessage != null}">
					<p class="success"><c:out value="${successMessage}" /></p>
				</c:if>
				<c:if test="${errorMessage != null}">
					<p class="error"><c:out value="${errorMessage}" /></p>
				</c:if>
				<form method="POST" action="/scriptions/login">
					<p>
						<label for="username">Email:</label>
						<input type="text" id="username" name="username"/>
					</p>
					<p>
						<label for="password">Password:</label>
						<input type="password" id="password" name="password"/>
					</p>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<input type="submit" value="Login!"/>
				</form>
			</div>
			<div class="entrBlock">
				<h1>Register</h1>
				<form:form method="POST" action="/scriptions/registration" modelAttribute="user">
					<p>
						<form:label path="username">Email:</form:label>
						<form:input type="text" path="username"/>
					</p>
					<form:errors cssClass="error" path="username"/>
					<p>
						<form:label path="firstname">First Name:</form:label>
						<form:input type="text" path="firstname"/>
					</p>
					<form:errors cssClass="error" path="firstname"/>
					<p>
						<form:label path="lastname">Last Name:</form:label>
						<form:input type="text" path="lastname"/>
					</p>
					<form:errors cssClass="error" path="lastname"/>
					<p>
						<form:label path="password">Password:</form:label>
						<form:input type="password" path="password"/>
					</p>
					<form:errors cssClass="error" path="password"/>
					<p>
						<form:label path="passwordConfirmation">Password Confirmation:</form:label>
						<form:input type="password" path="passwordConfirmation"/>
					</p>
					<form:errors cssClass="error" path="passwordConfirmation"/>
					<p>
						<form:label path="permissionLevel">Make Admin?</form:label>
						<form:checkbox path="permissionLevel" value="2"/>
						<form:hidden path="permissionLevel" value="1" />
					</p>
					<input type="submit" value="Register!"/>
				</form:form>
			</div>
		</div>
		<form class="exit" action="/" method="GET">
			<input type="submit" value="Return" />
		</form>
	</body>
</html>