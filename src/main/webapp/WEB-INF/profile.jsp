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
		<title>Profile</title>
		<link href="https://fonts.googleapis.com/css?family=Roboto|Poiret+One" rel="stylesheet">
		<link href="/css/style.css" rel="stylesheet" >
		<link href="/css/scriptions.css" rel="stylesheet">
	</head>

	<body>
		<h2>Welcome, ${currentUser.firstname}</h2>
		<form class="exit" method="POST" action="/scriptions/logout">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input type="submit" value="Logout!" />
		</form>
		<div class="flexBox">
			<table>
				<tr>
					<td>Current Package: </td>
					<td><c:out value="${currentUser.option.name}"/></td>
				</tr>
				<tr>
					<td>Next Due Date: </td>
					<td><c:out value="${dueDate}"/></td>
				</tr>
				<tr>
					<td>Amount Due: </td>
					<td><fmt:formatNumber value="${currentUser.option.cost}" type="currency"/></td>
				</tr>
				<tr>
					<td>User Since: </td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${currentUser.createdAt}"/></td>
				</tr>
			</table>
		</div>
	</body>
</html>