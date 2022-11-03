<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link href="../CSS/Preload.css" rel="stylesheet" type="text/css">
		<title>Insert title here</title>
	</head>
	<body>
		<div id="loader-wrapper">
		    <div id="loader"></div>
		</div>
	</body>
	<script>
	  const preloader = document.querySelector("#loader-wrapper");
	  if (preloader) {
		setTimeout(function () {
			preloader.remove();
		}, 1000);
	  }
	</script>
</html>