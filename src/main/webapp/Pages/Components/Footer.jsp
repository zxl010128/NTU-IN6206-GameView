<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<link href="../CSS/Footer.css" rel="stylesheet" type="text/css">
		<meta charset="UTF-8">
		<title>GameView</title>
	</head>
	<body>
		<nav class="navbar" style="width: 100%; background-color: #18123d; color: white; padding-top: 40px">
		  <div class="container-fluid" style="display: flex; flex-direction: column">
			<hr style="width:100%; background-color: white; border: 0; border-bottom: 4px solid white; margin: 4px 0">
			<div class="footerBox">
			    <div class="footerleftarea">
				    <img src="../../Assets/Icons/NTU_Logo.png" class="ntulogo" />
				    <div class="footerLeft">
				    	<span class="sign">
				      		WKWSCI IN6206 Team 9
				    	</span>
				    	<span style="margin-top: 20px">Zhang Xinlei</span>
				    	<span>Yin Xueyang</span>
				    	<span>Wang Yongzhi</span>
				    	<span>Chen Hongjin</span>
				    </div>

			    </div>
			    <div class="footerRight">
			    	<span class="logosign">
			    		GameView v1.0
			    	</span>
			    	<span class="logosign">
			    		2022.10
			    	</span>
			    	<img src="../../Assets/Icons/GameViewNewLogo.png" class="gameviewLogo"/>
			    	<button type="button" class="btn btn-outline-info" style="margin-top: 20px" data-bs-toggle="modal" data-bs-target="#exampleModal">Report</button>
			    </div>			
			</div>

		  </div>
		</nav>
		<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h1 class="modal-title fs-5" id="exampleModalLabel">Report</h1>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      <div class="modal-body">
		        <form>
		          <div class="mb-3">
		            <label for="name" class="col-form-label">Name:</label>
		            <input type="text" class="form-control" id="name">
		          </div>
		          <div class="mb-3">
		            <label for="contact" class="col-form-label">Contact:</label>
		            <input type="text" class="form-control" id="contact">
		          </div>
		 		  <div class="mb-3">
		            <label for="message" class="col-form-label">Report Content:</label>
		            <textarea class="form-control" id="message"></textarea>
		          </div>
		        </form>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
		        <button type="button" class="btn btn-primary" onClick=handleReport()>Report</button>
		      </div>
		    </div>
		  </div>
		</div>
	</body>
	
	<script type="text/javascript">
		function handleReport() {
			
			var name = document.getElementById("name").value
			var contact = document.getElementById("contact").value
			var content = document.getElementById("message").value
			
			if (name == '') {
				alert('Please input your name');
				return;
			} 
			
			if (contact == '') {
				alert('Please input your contact');
				return;
			}
			
			if (content == '') {
				alert('Please input your report content');
				return;
			}
	
			$.ajax({
				type: "POST",
				url: "/NTU-IN6206-GameView/Report",
				data: {
					name: name,
					contact: contact,
					content: content
				},
				async: false,
				
				success: function(data) {
					var obj = JSON.parse(data)
					if (obj.status_code !== 200) {
						alert(obj.message);
						return;
					} else {
						alert("Successfully reported it. Thank you for your feedback~");
						$("#name").val('');
						$("#contact").val('');
						$("#message").val('');
					}
				}
				
			});	
			
		}
	</script>

</html>