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
		<title>Overflow</title>
		<link href="https://fonts.googleapis.com/css?family=Roboto|Poiret+One" rel="stylesheet">
		<link href="/css/style.css" rel="stylesheet" >
		<link href="/css/overflow.css" rel="stylesheet">
	</head>

	<body>
		<div class="wrapper">
			<h1>OverFlow</h1>
			<h2>Dashboard</h2>
			<c:if test="${empty questions}">
				No questions yet.<br>
			</c:if>
			<c:if test="${not empty questions}">
				<table>
					<thead>
						<th>Question</th>
						<th>Tags</th>
					</thead>
					<tbody>
						<c:forEach items="${questions}" var="question"><tr>
							<td><a href="/overflow/questions/${question.id}"><c:out value="${question.body}"/></a></td>
							<td><c:forEach items="${question.tags}" var="tag">
								<button disabled>${tag.name}</button>
							</c:forEach></td></tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<br>
			<form method="GET" action="/overflow/questions/new">
				<input type="submit" value="New Question">
			</form>
			<form class="exit" action="/" method="GET">
				<input type="submit" value="Return">
			</form>
		</div>
	</body>
</html>