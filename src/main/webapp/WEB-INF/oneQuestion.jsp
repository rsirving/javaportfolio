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
		<title>Question Details</title>
		<link href="https://fonts.googleapis.com/css?family=Roboto|Poiret+One" rel="stylesheet">
		<link href="/css/style.css" rel="stylesheet" >
		<link href="/css/overflow.css" rel="stylesheet">
	</head>

	<body>
		<div class="wrapper">
			<h1>OverFlow</h1>
			<div id="questionInfo">
				<h2><c:out value="${question.body}"/></h2>
				<h3>Tags:</h3>
				<c:forEach items="${question.tags}" var="tag">
					<button class="tag" disabled>${tag.name}</button>
				</c:forEach>
			</div>
			<br>
			<div id="answerSection">
				<form method="post" action="/overflow/answer/new/${question.id}">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<label name="body">Add Answer:</label><br>
					<p class="error">${answerError}</p>
					<textarea name="body" id="body" cols="30" rows="5"></textarea>
					<br>
					<input type="submit" value="Submit">
				</form>
				<h3>Answers:</h3>
				<table>
					<c:forEach items="${answers}" var="answer"><tr>
						<td>${answer.body}</td>
					</tr>
					</c:forEach>
				</table>
			</div>
			<form class="exit" action="/overflow" method="GET">
				<input type="submit" value="Return">
			</form>
		</div>
	</body>
</html>