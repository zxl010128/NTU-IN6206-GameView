<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
		<link href="../CSS/Game.css" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>GameView</title>
	</head>
	
	<body>

	<%@ include file="../Components/Preload.jsp" %>
	<%@ include file="../Components/NavBar.jsp" %>
	
	<div class="gameContainer">
	
		<div style="width: 100%; ">
			<img src="../../Assets/Pics/GamePage.png" style="width: 100%;"/>	
		</div>
		
		<div style="width: 95%; margin: 40px auto 0 auto;  background-color: rgba(255,255,255,0.25); border-radius: 20px">
			<div class="gameInfoBox">
				<img id="gamePic" style="width: 300px;"/>	
				<div class="profilrInfoSmallBox">
					<h2 style="margin: 20px" id="gameName"></h2>
					<div id="gameIntro"></div>				
					<div style='margin-top: 20px' id="gameScore"></div>
				</div>
			</div>
			
		</div>
		
		<div style="width: 95%; margin: 40px auto 0 auto;  background-color: rgba(255,255,255,0.25); border-radius: 20px; padding: 10px "> 	
			
			<div style="display: flex; flex-direction: row; justify-content: space-between; margin: 30px 30px 0px 30px;">
				<select class="form-select" aria-label="Default select example" id="gameSelector" onchange="selectChangedGame(this.options[this.selectedIndex].value)">
			  		<option value="1" selected>Comments</option>
			  		<option value="2">Scores</option>
				</select>
				<button type="button" class="btn btn-outline-info" id="addButton" data-bs-toggle="modal" data-bs-target="#commentModal">Add</button>
			
			</div>			

			<div id="commentList">
			
			</div>

		</div>
		
	
	</div>


	<%@ include file="../Components/Footer.jsp" %>
	
	<div class="modal fade" id="commentModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="exampleModalLabel">New Comment</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <form>
	          <div class="mb-3">
	            <label for="message-text" class="col-form-label">Comment:</label>
	            <textarea class="form-control" id="newComment"></textarea>
	          </div>
	        </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary" onClick=handleNewComment()>Submit</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="modal fade" id="scoreModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="exampleModalLabel">New score</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <form>
	          <div class="mb-3">
	            <label for="recipient-name" class="col-form-label">Score:</label>
	            <input type="text" class="form-control" id="score">
	          </div>
	          <div class="mb-3">
	            <label for="message-text" class="col-form-label">Reason:</label>
	            <textarea class="form-control" id="reason"></textarea>
	          </div>
	        </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary" onClick=handleNewScore()>Submit</button>
	      </div>
	    </div>
	  </div>
	</div>

	
	
	</body>
	<script src="https://code.jquery.com/jquery-3.6.1.js" integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI=" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
	<script>

	if (localStorage.getItem("token") == null || localStorage.getItem("token") == '' ) { 		
		document.querySelector("#addButton").remove();
	} 

	function toCommentPage(id) {
		
		window.location = "Comment.jsp?id=" + id;
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
	
	const game_id = getQueryVariable('id')
	
	$.ajax({
		type: "GET",
		url: "/NTU-IN6206-GameView/GamePage",
		data: {
			game_id: game_id
		},
		async: false,
			
		success: function(data) {
			var obj = JSON.parse(data)
			console.log(obj)
			document.getElementById("gamePic").setAttribute('src', obj.data.gamePicture)
			document.getElementById("gameName").innerHTML = obj.data.gameName
			document.getElementById("gameIntro").innerHTML = obj.data.introduction
			document.getElementById("gameScore").innerHTML = "Star: " + obj.data.score
		}
			
	});
	
	$.ajax({
		type: "GET",
		url: "/NTU-IN6206-GameView/ShowComments",
		data: {
			game_id: game_id
		},
		async: false,
			
		success: function(data) {
			var obj = JSON.parse(data)
			console.log(obj)
			
			for (var i = 0; i < obj.data.allcomments.length; i++) {
				
				const parent = document.getElementById('commentList')
			  	const infoBox = document.createElement('div')
			  	infoBox.className = 'card'
			  	infoBox.setAttribute('style', 'margin-top: 20px; box-shadow: 5px 5px 10px -4px black; cursor: pointer')
			  	
			  	infoBox.addEventListener('click', toCommentPage.bind(this,obj.data.allcomments[i].comment.ID))
			  	parent.append(infoBox)
			  	
			  	const upText = document.createElement('div')
			  	const downText = document.createElement('div')
			  	
			  	upText.className = 'card-header'
			  	upText.innerHTML = obj.data.allcomments[i].user.userName
			  	downText.className = 'card-body'

			  	infoBox.append(upText)
			  	infoBox.append(downText)
			  	
			  	const text = document.createElement('p')
			  	text.className = 'card-text'
			  	text.innerHTML = obj.data.allcomments[i].comment.content
			  	
			  	downText.append(text)
				
			}
		}
			
	});
	
	function handleNewComment() {
		
		var content =  document.getElementById("newComment").value
		
		if (content == '') {
			alert("Please input your comment")
			return;
		}
		
		$.ajax({
			type: "POST",
			url: "/NTU-IN6206-GameView/NewComment",
			data: {
				game_id: game_id,
				token: localStorage.getItem("token"),
				content: content
			},
			async: false,
				
			success: function(data) {
				var obj = JSON.parse(data)
				console.log(obj)
				
				if (obj.status_code !== 200) {
					alert(obj.message);
					return;
				} else {
					alert("Successfully commented");
					window.location = "Game.jsp?id=" + game_id;
				}
					
			}
				
		});
		
	}
	function handleNewScore() {
		
		var score =  document.getElementById("score").value
		var reason =  document.getElementById("reason").value
		
		if (reason == '') {
			alert("Please input your reason")
			return;
		}
		
		if (parseFloat(score) > 10 || parseFloat(score) < 0) {
			alert("Please input a score between 0 to 10")
			return;
		}
		
		$.ajax({
			type: "POST",
			url: "/NTU-IN6206-GameView/Scoring",
			data: {
				game_id: game_id,
				token: localStorage.getItem("token"),
				score: score,
				reasons_for_scoring: reason,
			},
			async: false,
				
			success: function(data) {
				var obj = JSON.parse(data)
				console.log(obj)
				
				if (obj.status_code !== 200) {
					alert(obj.message);
					return;
				} else {
					alert("Successfully scored");
					window.location = "Game.jsp?id=" + game_id;
				}
					
			}
				
		});
		
	}
	
	function selectChangedGame(id) {
		if (id == 1) {
			const game_id = getQueryVariable('id')
			document.getElementById("addButton").setAttribute('data-bs-target', "#commentModal")
			$.ajax({
				type: "GET",
				url: "/NTU-IN6206-GameView/ShowComments",
				data: {
					game_id: game_id
				},
				async: false,
					
				success: function(data) {
					var obj = JSON.parse(data)
					
					const parent = document.getElementById('commentList')
						
					while (parent.firstChild) {
					    parent.firstChild.remove()
					}
					for (var i = 0; i < obj.data.allcomments.length; i++) {
						
						const parent = document.getElementById('commentList')
					  	const infoBox = document.createElement('div')
					  	infoBox.className = 'card'
					  	infoBox.setAttribute('style', 'margin-top: 20px; box-shadow: 5px 5px 10px -4px black; cursor: pointer')
					  	
					  	infoBox.addEventListener('click', toCommentPage.bind(this,obj.data.allcomments[i].comment.ID))
					  	parent.append(infoBox)
					  	
					  	const upText = document.createElement('div')
					  	const downText = document.createElement('div')
					  	
					  	upText.className = 'card-header'
					  	upText.innerHTML = obj.data.allcomments[i].user.userName
					  	downText.className = 'card-body'

					  	infoBox.append(upText)
					  	infoBox.append(downText)
					  	
					  	const text = document.createElement('p')
					  	text.className = 'card-text'
					  	text.innerHTML = obj.data.allcomments[i].comment.content
					  	
					  	downText.append(text)
						
					}
				}
					
			});
		} else if (id = 2) {
			const game_id = getQueryVariable('id')
			document.getElementById("addButton").setAttribute('data-bs-target', "#scoreModal")
			$.ajax({
				type: "GET",
				url: "/NTU-IN6206-GameView/ShowScoringReasons",
				data: {
					game_id: game_id
				},
				async: false,
					
				success: function(data) {
					var obj = JSON.parse(data)
					
					const parent = document.getElementById('commentList')
						
					while (parent.firstChild) {
					    parent.firstChild.remove()
					}
					for (var i = 0; i < obj.data.scorings.length; i++) {
						
						const parent = document.getElementById('commentList')
					  	const infoBox = document.createElement('div')
					  	infoBox.className = 'card'
					  	infoBox.setAttribute('style', 'margin-top: 20px; box-shadow: 5px 5px 10px -4px black;')
					  	
					  	parent.append(infoBox)
					  	
					  	const upText = document.createElement('div')
					  	const downText = document.createElement('div')
					  	
					  	upText.className = 'card-header'
					  	upText.innerHTML = obj.data.scorings[i].user.userName + "  Score: " + obj.data.scorings[i].score.score
					  	downText.className = 'card-body'

					  	infoBox.append(upText)
					  	infoBox.append(downText)
					  	
					  	const text = document.createElement('p')
					  	text.className = 'card-text'
					  	text.innerHTML = obj.data.scorings[i].score.reason
					  	
					  	downText.append(text)
						
					}
				}
					
			});
			
		}
		
	}
		
	</script>

</html>