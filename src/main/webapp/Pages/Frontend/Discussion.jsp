<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="../CSS/Discussion.css" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
		<title>GameView</title>
	</head>
	
	<body>

	<%@ include file="../Components/Preload.jsp" %>
	<%@ include file="../Components/NavBar.jsp" %>
	
	<div class="discussionContainer">
	
		<div style="width: 100%; ">
			<img src="../../Assets/Pics/DiscussionPage.png" style="width: 100%;"/>	
		</div>
		
		<div id="bigArea">

		</div>
		

	</div>

	<%@ include file="../Components/Footer.jsp" %>
	</body>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.6.1.js" integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI=" crossorigin="anonymous"></script>
	<script>
	
		function toGamePage(id) {
			
			window.location = "Game.jsp?id=" + id;
		}
	
		$.ajax({
			type: "GET",
			url: "/NTU-IN6206-GameView/AllGames",
			async: false,
			
			success: function(data) {
				var obj = JSON.parse(data)
				console.log(obj)
				for (var i = 0; i < obj.data.length; i++) {

				  	const parent = document.getElementById('bigArea')
				  	const area = document.createElement('div')
				  	area.className = 'areaBox'
				  	
				  	parent.append(area)
				  	
				  	const title = document.createElement('h3')
				  	title.innerHTML = obj.data[i].category
				  	title.className = "discussionTitle"
				  	
				  	area.append(title)
				  	
				  	const picArea = document.createElement('div')
				  	picArea.className = 'picArea'
				  	
				  	area.append(picArea)
				  	
				  	for (var j = 0; j < obj.data[i].games.length; j++) {
				  		const outer = document.createElement('div')
				  		const img = document.createElement('img')
				  		img.setAttribute('src', obj.data[i].games[j].gamePicture)
				  		img.className = 'pic'
				  		
				  		outer.addEventListener('click', toGamePage.bind(this,obj.data[i].games[j].id))
				  		
				  		outer.append(img)
				  		picArea.append(outer)
				  		
				  	}
				 
				}
			}
			
		});	
	
	</script>
</html>