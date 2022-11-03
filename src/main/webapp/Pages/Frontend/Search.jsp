<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="../CSS/Search.css" rel="stylesheet" type="text/css">
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
			<img src="../../Assets/Pics/SearchPage.png" style="width: 100%;"/>	
		</div>
		
		<div id="bigArea">
			<div class="areaBox">
				<h3 class="discussionTitle"> Game </h3>
				<div class="picArea" id="picArea"></div>
			</div>
			
			<div class="areaBox">
				<h3 class="discussionTitle"> People </h3>
				<div class="picArea" id="nameList"></div>
			</div>
		</div>
		

	</div>

	<%@ include file="../Components/Footer.jsp" %>
	</body>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.6.1.js" integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI=" crossorigin="anonymous"></script>
	<script>
	
		function SearchtoGamePage(id) {
			window.location = "Game.jsp?id=" + id;
		}
		
		function SearchtoProfilePage(id) {
			window.location = "Profile.jsp?id=" + id;
		}
		
		function getQueryVariable(variable)
		{
		    var query = window.location.search.substring(1);
		    var vars = query.split("&");
		    for (var i=0;i<vars.length;i++) {
		        var pair = vars[i].split("=");
		        if(pair[0] == variable){return pair[1];}
		    }
		    return(false);
		}
		
		const query = getQueryVariable('query')
	
		$.ajax({
			type: "GET",
			url: "/NTU-IN6206-GameView/SearchGames",
			data: {
				gamename: query,
			},
			async: false,
			
			success: function(data) {
				var obj = JSON.parse(data)
				console.log(obj)
				for (var i = 0; i < obj.data.length; i++) {

				  	const parent = document.getElementById('picArea')
					const outer = document.createElement('div')
				  	const img = document.createElement('img')
				  	img.setAttribute('src', obj.data[i].game.gamePicture)
			 		img.className = 'pic'
				  		
			 		outer.addEventListener('click', SearchtoGamePage.bind(this,obj.data[i].game.id))
				  		
			  		outer.append(img)
			  		
			  		parent.append(outer)
			  

				}
			}
			
		});	
		
		$.ajax({
			type: "GET",
			url: "/NTU-IN6206-GameView/SearchUsers",
			data: {
				username: query,
			},
			async: false,
			
			success: function(data) {
				var obj = JSON.parse(data)
				console.log(obj)
				for (var i = 0; i < obj.data.length; i++) {

				  	const parent = document.getElementById('nameList')
					const outer = document.createElement('div')
				  	const img = document.createElement('img')
				  	
				  	if (obj.data[i].user.facepic == '') {
				  		img.setAttribute('src', '../../Assets/Icons/UserImage.png')
				  	} else {
				  		img.setAttribute('src', obj.data[i].user.facepic)
				  	}
				  	
				  	const text = document.createElement('div')
				  	text.innerHTML = obj.data[i].user.userName
				  	
			 		img.className = 'facePic'
				  		
			 		outer.addEventListener('click', SearchtoProfilePage.bind(this,obj.data[i].user.id))
				  		
			  		outer.append(img)
			  		outer.append(text)
			  		
			  		parent.append(outer)
			  

				}
			}
			
		});	
	
	</script>
</html>