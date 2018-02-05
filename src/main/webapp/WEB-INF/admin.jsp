<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Admin Dashboard</title>
		<link href="https://fonts.googleapis.com/css?family=Roboto|Poiret+One" rel="stylesheet">
		<link href="/css/style.css" rel="stylesheet" >
		<link href="/css/scriptions.css" rel="stylesheet">
	</head>

	<body>
		<h1>Admin Dashboard</h1>
		<form id="logoutForm" method="POST" action="/scriptions/logout">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input type="submit" value="Logout!" />
		</form>
		<div class="flexBox">
			<div class="adminBlock">
				<h2>Customers</h2>
				<table>
					<thead>
						<th>Name</th>
						<th>Due Date</th>
						<th>Due</th>
						<th>Package</th>
					</thead>
					<tbody>
						<c:forEach var="customer" items="${users}">
							<c:if test="${customer.option != null}">
								<tr>
									<td>${customer.firstname} ${customer.lastname}</td>
									<td><c:set var="dueDate" value="${customer.dueDate}"/>
										<% 
										int dueDate = (int) pageContext.getAttribute("dueDate");
										java.time.LocalDate nextDue = java.time.LocalDate.now();
										if (nextDue.getDayOfMonth() >= dueDate){
											nextDue = nextDue.plusMonths(1);
										}
										if (dueDate <= nextDue.lengthOfMonth()){
											nextDue = nextDue.withDayOfMonth(dueDate);
										} else {
											nextDue = nextDue.withDayOfMonth(nextDue.lengthOfMonth());
										} 
										pageContext.setAttribute("result", nextDue);
										%> <c:out value="${result}"/></td>
									<td><fmt:formatNumber value="${customer.option.cost}" type="currency"/></td>
									<td>${customer.option.name}</td>
								</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="adminBlock">
				<h2>Packages</h2>
				<table>
					<thead>
						<th>Name</th>
						<th>Price</th>
						<th>Active</th>
						<th>Users</th>
						<th>Actions</th>
					</thead>
					<tbody>
						<c:forEach var="option" items="${options}">
							<tr>
								<td>${option.name}</td>
								<td><fmt:formatNumber value="${option.cost}" type="currency" /></td>
								<c:choose>
									<c:when test="${option.active == true}"><td>Yes</td></c:when>
									<c:otherwise><td>No</td></c:otherwise>
								</c:choose>
								<td>${fn:length(option.users)}</td>
								<td>
									<c:choose>
										<c:when test="${option.active == true}"><a href="/scriptions/packages/${option.id}/toggle">Deactivate</a></c:when>
										<c:otherwise><a href="/scriptions/packages/${option.id}/toggle">Activate</a></c:otherwise>
									</c:choose>
									<c:if test="${fn:length(option.users) == 0}"><a href="/scriptions/packages/${option.id}/delete">Delete</a></c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="adminBlock">
				<h2>New Package</h2>
				<p class="error"><c:out value="${optionFlash}"/></p>
				<form:form method="POST" action="/scriptions/packages/new" modelAttribute="option">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<form:label path="name">Package Name:</form:label>
					<form:input path="name"/>
		
					<form:label path="cost">Cost:</form:label>
					<form:input type="number" min="0" step="0.01" path="cost"/>
		
					<input type="submit" value="Submit">
				</form:form>
			</div>
		</div>
	</body>
</html>