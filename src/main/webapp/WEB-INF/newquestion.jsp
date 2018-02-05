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
		<title>New Question</title>
		<link href="https://fonts.googleapis.com/css?family=Roboto|Poiret+One" rel="stylesheet">
		<link href="/css/style.css" rel="stylesheet" >
		<link href="/css/overflow.css" rel="stylesheet">
	</head>

	<body>
		<div class="wrapper">
			<h1>OverFlow</h1>
			<h2>What is your question?</h2>
			<form method="POST" action="/overflow/questions/new/submit">
				<p class="error">${errors0}</p>
				<label name="body">Question:</label>
				<br>
				<p class="error">${errors1}</p>
				<textarea name="body" id="body" rows="5" cols="30"></textarea>
				<br>
				<label name="tag">Add Tags:</label>
				<br>
				<p class="error">${errors2}</p>
				<p class="error">${errors3}</p>
				<input type="text" name="tag"/><br><br>
				<input type="submit" value="Submit">
			</form>
			<form class="exit" action="/overflow" method="GET">
				<input type="submit" value="Return">
			</form>
		</div>
	</body>
</html>