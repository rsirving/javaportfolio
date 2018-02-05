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
		<title>Package Selection</title>
		<link href="https://fonts.googleapis.com/css?family=Roboto|Poiret+One" rel="stylesheet">
		<link href="/css/style.css" rel="stylesheet" >
		<link href="/css/scriptions.css" rel="stylesheet">
	</head>

	<body>
		<h1>Welcome to Dojoscriptions, ${currentUser.firstname}!</h1>
		<form class="exit" method="POST" action="/scriptions/logout">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input type="submit" value="Logout!" />
		</form>
		<h2>Please choose a subscription option and start date.</h2>
		<div class="flexBox">
			<div>
				<form method="POST" action="/scriptions/selection">
					<label name="dueDate">Due Date (day of month):
						<input type="number" min="1" max="31" name="dueDate">
					</label>
					<label name="option">Package:
						<select name="option">
							<c:forEach var="option" items="${options}">
								<c:if test="${option.active}">
									<option value="${option.id}">${option.name} (<fmt:formatNumber value="${option.cost}" type="currency"/>)</option>
								</c:if>
							</c:forEach>
						</select>
					</label>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>			
					<input type="submit" value="Subscribe">
				</form>
			</div>
		</div>
	</body>
</html>