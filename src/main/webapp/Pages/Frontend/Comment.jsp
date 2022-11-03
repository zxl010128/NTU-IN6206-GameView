<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
		<link href="../CSS/Comment.css" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>GameView</title>
	</head>
	
	<body>

	<%@ include file="../Components/Preload.jsp" %>
	<%@ include file="../Components/NavBar.jsp" %>
	
	<div class="commentContainer" id="commentContainer">
	
		<div style="width: 100%; ">
			<img src="../../Assets/Pics/GamePage.png" style="width: 100%;"/>	
		</div>
		
		<div style="width: 95%; margin: 40px auto 0 auto; border-radius: 20px" id="replyControl">
			<button type="button" class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#replyModal">New Reply</button>
			<button type="button" class="btn btn-outline-primary" id="likeButton" style="margin-left: 20px">Like</button>
			<button type="button" class="btn btn-outline-primary" id="dislikeButton" style="margin-left: 20px">Dislike</button>
			<button type="button" class="btn btn-outline-primary" id="bookButton" style="margin-left: 20px">Bookmark</button>
			
		</div>
		
		<div style="width: 95%; margin: 40px auto 0 auto;  background-color: rgba(255,255,255,0.25); border-radius: 20px">
			<div class="card">
			  <div class="card-header" id="commentTime">
			  </div>
			  <div class="card-body">
			    <p class="card-text" id="commentContent"></p>
			  </div>
			</div>
			
		</div>
		
	
	</div>


	<%@ include file="../Components/Footer.jsp" %>
	
	<div class="modal fade" id="replyModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="exampleModalLabel">New Reply</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <form>
	          <div class="mb-3">
	            <label for="message-text" class="col-form-label">Reply:</label>
	            <textarea class="form-control" id="newReply"></textarea>
	          </div>
	        </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary" onClick=handleNewReply()>Submit</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	</body>
	<script src="https://code.jquery.com/jquery-3.6.1.js" integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI=" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
	<script>

	if (localStorage.getItem("token") == null || localStorage.getItem("token") == '' ) { 		
		document.querySelector("#replyControl").remove();
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
	
	const post_id = getQueryVariable('id')
	
	
	$.ajax({
		type: "GET",
		url: "/NTU-IN6206-GameView/ShowReplys",
		data: {
			post_id: post_id
		},
		async: false,
			
		success: function(data) {
			var obj = JSON.parse(data)
			console.log(obj)
			
			document.getElementById('commentTime').innerHTML = obj.data.comment.createTime
			document.getElementById('commentContent').innerHTML = obj.data.comment.content
			
			
			for (var i = 0; i < obj.data.allreplys.length; i++) {
				
				const parent = document.getElementById('commentContainer')
				
				const child = document.createElement('div')
				child.setAttribute('style', "width: 95%; margin: 40px auto 0 auto;  background-color: rgba(255,255,255,0.25); border-radius: 20px")
				
				parent.append(child)
				
			  	const infoBox = document.createElement('div')
			  	infoBox.className = 'card'
			  	infoBox.setAttribute('style', 'margin-top: 20px; box-shadow: 5px 5px 10px -4px black;')
			  	
			  	child.append(infoBox)
			  	
			  	const upText = document.createElement('div')
			  	const downText = document.createElement('div')
			  	
			  	upText.className = 'card-header'
			  	upText.innerHTML = obj.data.allreplys[i].user.userName + "  replied on " + 	obj.data.allreplys[i].reply.createTime
			  	downText.className = 'card-body'

			  	infoBox.append(upText)
			  	infoBox.append(downText)
			  	
			  	const text = document.createElement('p')
			  	text.className = 'card-text'
			  	text.innerHTML = obj.data.allreplys[i].reply.content
			  	
			  	downText.append(text)
				
			}
		}
			
	});
	
	function handleNewReply() {
		
		var content =  document.getElementById("newReply").value
		
		if (content == '') {
			alert("Please input your reply")
			return;
		}
		
		$.ajax({
			type: "POST",
			url: "/NTU-IN6206-GameView/NewReply",
			data: {
				post_id: post_id,
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
					alert("Successfully posted");
					window.location = "Comment.jsp?id=" + post_id;
				}
					
			}
				
		});
		
	}
	
	function likeComment(id) {
		
		$.ajax({
			type: "POST",
			url: "/NTU-IN6206-GameView/AddLike",
			data: {
				post_id: id,
				token: localStorage.getItem("token"),
			},
			async: false,
				
			success: function(data) {
				var obj = JSON.parse(data)
				console.log(obj)
				
				if (obj.status_code !== 200) {
					alert(obj.message);
					return;
				} else {
					alert("Successfully liked");
					window.location = "Comment.jsp?id=" + id;
				}
					
			}
				
		});
	}
	
	function dislikeComment(id) {
		
		$.ajax({
			type: "POST",
			url: "/NTU-IN6206-GameView/AddDislike",
			data: {
				post_id: id,
				token: localStorage.getItem("token"),
			},
			async: false,
				
			success: function(data) {
				var obj = JSON.parse(data)
				console.log(obj)
				
				if (obj.status_code !== 200) {
					alert(obj.message);
					return;
				} else {
					alert("Successfully disliked");
					window.location = "Comment.jsp?id=" + id;
				}
					
			}
				
		});
	}
	
	function bookmarkComment(id) {
		
		$.ajax({
			type: "POST",
			url: "/NTU-IN6206-GameView/MarkComment",
			data: {
				post_id: id,
				token: localStorage.getItem("token"),
			},
			async: false,
				
			success: function(data) {
				var obj = JSON.parse(data)
				console.log(obj)
				
				if (obj.status_code !== 200) {
					alert(obj.message);
					return;
				} else {
					alert("Successfully bookmarked");
					window.location = "Comment.jsp?id=" + id;
				}
					
			}
				
		});
	}
	
	document.getElementById("likeButton").addEventListener('click', likeComment.bind(this,post_id))
	document.getElementById("dislikeButton").addEventListener('click', dislikeComment.bind(this,post_id))
	document.getElementById("bookButton").addEventListener('click', bookmarkComment.bind(this,post_id))
	</script>

</html>