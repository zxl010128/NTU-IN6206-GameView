<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="../CSS/Main.css" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
		<title>GameView</title>
	</head>
	
	<body>

	<%@ include file="../Components/Preload.jsp" %>
	<%@ include file="../Components/NavBar.jsp" %>
	
	<div class="mainContainer">
	
		<div style="width: 100%; ">
			<img src="../../Assets/Pics/MainPage.png" style="width: 100%;"/>	
		</div>
		
		<div style="width: 95%; margin: 40px auto 0 auto;  background-color: rgba(255,255,255,0.25); border-radius: 20px">
			<div style="width: 100%;">
				<div style="width: 95%; margin: 0 auto; padding: 40px 0" >
					<%@ include file="../Components/Carousel.jsp" %>
				</div>
			</div>
			
			<div class="rankingContainer"> 
				<div class="rankingInnerBox">
					<div class="rankingBox">
						<div class="rankingTopBox">
							<img src="../../Assets/Icons/Fire.svg" class="fireLogo"/>
							<h4 class="rankingTitle">Top 10 Game</h4>
						</div>
						<hr style="left: 10%; width:80%; background-color: #A3A3A3; border: 0; border-bottom: 4px solid #A3A3A3; margin: 4px 0">
						<div id="rankingGame"></div>
					</div>
				
					<div class="rankingBox">
						<div class="rankingTopBox">
							<img src="../../Assets/Icons/Fire.svg" class="fireLogo"/>
							<h4 class="rankingTitle">Top 10 Group</h4>
						</div>
						<hr style="left: 10%; width:80%; background-color: #A3A3A3; border: 0; border-bottom: 4px solid #A3A3A3; margin: 4px 0">
						<div id="rankingDiscussion"></div>
					</div>

					<div class="rankingBox">
						<div class="rankingTopBox">
							<img src="../../Assets/Icons/Fire.svg" class="fireLogo"/>
							<h4 class="rankingTitle">Top 10 Post</h4>
						</div>
						<hr style="left: 10%; width:80%; background-color: #A3A3A3; border: 0; border-bottom: 4px solid #A3A3A3; margin: 4px 0">
						<div id="rankingPost"></div>
					</div>								
				
				</div>

			</div>
		
		</div>
		

	</div>

	<%@ include file="../Components/Footer.jsp" %>
	</body>
	<script src="https://code.jquery.com/jquery-3.6.1.js" integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI=" crossorigin="anonymous"></script>
	<script>
	
		function MaintoCommentPage(id) {
			
			window.location = "Comment.jsp?id=" + id;
		}
		
		function MaintoGamePage(id) {
			
			window.location = "Game.jsp?id=" + id;
		}
		
		$.ajax({
			type: "GET",
			url: "/NTU-IN6206-GameView/Top10Game",
			async: false,
			
			success: function(data) {
				var obj = JSON.parse(data)
				for (var i = 0; i < obj.data.length; i++) {

				  	const parent = document.getElementById('rankingGame')
				  	const img = document.createElement('div')
				  	img.className = 'rankingGame'
				  	
				  	const leftText = document.createElement('span')
				  	
				  	if (obj.data[i].gameName.length > 16) {
				  		leftText.innerHTML = obj.data[i].gameName.substr(0,16) + "..."
				  	} else {
				  		leftText.innerHTML = obj.data[i].gameName
				  	}
				  	
				  	const rightBox = document.createElement('div')
				  	const icon = document.createElement('img')
				  	icon.setAttribute('src', '../../Assets/Icons/Score.svg')
				  	icon.setAttribute('style', 'width: 20px; margin-right: 3px; cursor: pointer')
				  	
				  	icon.addEventListener('click', MaintoGamePage.bind(this,obj.data[i].id))
				  	
				  	const rightText = document.createElement('span')
				  	rightText.innerHTML = obj.data[i].score
				  	
				  	rightBox.append(icon);
				  	rightBox.append(rightText)	
				  
				  	img.append(leftText);
				  	img.append(rightBox);
				  	
				  	parent.append(img)
				}
			}
			
		});	
		
		$.ajax({
			type: "GET",
			url: "/NTU-IN6206-GameView/Top10Group",
			async: false,
			
			success: function(data) {
				var obj = JSON.parse(data)
				for (var i = 0; i < obj.data.length; i++) {
				  	console.log(obj.data[i].gamePicture)
				  	const parent = document.getElementById('rankingDiscussion')
				  	const img = document.createElement('div')
				  	img.className = 'rankingGame'
				  	
				  	const leftText = document.createElement('span')
				  	if (obj.data[i].gameName.length > 16) {
				  		leftText.innerHTML = obj.data[i].gameName.substr(0,13) + "..."
				  	} else {
				  		leftText.innerHTML = obj.data[i].gameName
				  	}
				  	
				  	const rightBox = document.createElement('div')
				  	const icon = document.createElement('img')
				  	icon.setAttribute('src', '../../Assets/Icons/Post.svg')
				  	icon.setAttribute('style', 'width: 20px; margin-right: 3px; cursor: pointer')
				  	
				  	icon.addEventListener('click', MaintoGamePage.bind(this,obj.data[i].id))
				  	
				  	const rightText = document.createElement('span')
				  	rightText.innerHTML = obj.data[i].posts
				  	
				  	rightBox.append(icon);
				  	rightBox.append(rightText)	
				  
				  	img.append(leftText);
				  	img.append(rightBox);
				  	
				  	parent.append(img)
				}
			}
			
		});	

		$.ajax({
			type: "GET",
			url: "/NTU-IN6206-GameView/Top10Post",
			async: false,
			
			success: function(data) {
				var obj = JSON.parse(data)
				for (var i = 0; i < obj.data.length; i++) {

				  	const parent = document.getElementById('rankingPost')
				  	const img = document.createElement('div')
				  	img.className = 'rankingGame'
				  	
				  	const leftText = document.createElement('span')
				  	if (obj.data[i].content.length > 16) {
				  		leftText.innerHTML = obj.data[i].content.substr(0,13) + "..."
				  	} else {
				  		leftText.innerHTML = obj.data[i].content
				  	}
				  	
				  	const rightBox = document.createElement('div')
				  	const icon = document.createElement('img')
				  	icon.setAttribute('src', '../../Assets/Icons/love.svg')
				  	icon.setAttribute('style', 'width: 20px; margin-right: 3px; cursor: pointer')
				  	
				  	icon.addEventListener('click', MaintoCommentPage.bind(this,obj.data[i].ID))
				  	
				  	const rightText = document.createElement('span')
				  	rightText.innerHTML = obj.data[i].totalLike
				  	
				  	rightBox.append(icon);
				  	rightBox.append(rightText)	
				  
				  	img.append(leftText);
				  	img.append(rightBox);
				  	
				  	
				  	parent.append(img)
				}
			}
			
		});
	</script>
</html>