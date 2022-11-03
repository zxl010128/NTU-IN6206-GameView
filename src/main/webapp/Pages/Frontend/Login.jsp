<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous"><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
		<link href="../CSS/Auth.css" rel="stylesheet" type="text/css">
		<title>GameView</title>
		<script src="https://www.google.com/recaptcha/api.js"></script>
	</head>
	
	<body>
		<%@ include file="../Components/Preload.jsp" %>
		
			<div class=loginADBox>
				<img src="../../Assets/Pics/GameView.jpg" class="loginAD"/>
			</div>
			
			<div class="loginBox">
			
				<div onClick=loginToMain()>
					<img src="../../Assets/Logos/backIcon.svg" class="authBack"/>
				</div>
				<span class="loginTitle">
					Login to GameView
				</span>
		
				<div class="loginInfoBox">
					<div style="width: 90%; margin: 0 auto;">
						<div>
							<span>Username / Email</span>
							<input type="text" class="form-control" id="loginFirst">
						</div>
						
						<div style="margin-top: 20px">
							<span>Password</span>
							<input type="password" class="form-control" id="loginPassword">	
							<button type="button" class="btn btn-outline-light" data-bs-toggle="modal" data-bs-target="#exampleModal">
							  <span class="loginForget">Forget the password?</span>
							</button>
						</div>
						<div class="g-recaptcha"
						data-sitekey="6Lfrn5kiAAAAALSxGoGw_qmSfQGX9KqGfxjTqBUt"></div>
					</div> 
					
				</div>
				
				<button type="button" class="btn btn-primary" id="firstButtonAuth" onClick=submitLogin()>Log In</button>
				<button type="button" class="btn btn-outline-primary" id="secondButtonAuth" onClick=loginToRegister()>Register</button>
				
			</div>
		<!-- Modal -->
		<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h1 class="modal-title fs-5" id="exampleModalLabel">Reset Password</h1>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      <div class="modal-body">
		        <form>
		          <div class="mb-3">
		            <label for="recipient-name" class="col-form-label">Email Address:</label>
		            <input type="text" class="form-control" id="forgetPasswordEmail">
		          </div>
		          <div class="mb-3" id="forgetBox" style="display: none">
		            <label for="recipient-name" class="col-form-label">Reset Code:</label>
		            <input type="text" class="form-control" id="resetCode">
		            <label for="recipient-name" class="col-form-label">New Password:</label>
		            <input type="text" class="form-control" id="newPassword">
		          </div>
		        </form>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
		        <button type="button" class="btn btn-primary" id="forgetSubmit" onClick=forgetPassword()>Submit</button>
		      </div>
		    </div>
		  </div>
		</div>
	
	</body>
	<script src="https://code.jquery.com/jquery-3.6.1.js" integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI=" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
	<script>
		function submitLogin() {
			
			if (grecaptcha.getResponse() == '') {
				alert("Please Verify Your Identity");
				return;
			}
			
			var username = document.getElementById("loginFirst").value
			var password = document.getElementById("loginPassword").value
			
			if (username == '') {
				alert('Please input your username');
				return;
			} 
			
			if (password == '') {
				alert('Please input your password');
				return;
			}

			$.ajax({
				type: "POST",
				url: "/NTU-IN6206-GameView/Login",
				data: {
					input: username,
					password: password
				},
				async: false,
				
				success: function(data) {
					var obj = JSON.parse(data)
					if (obj.status_code !== 200) {
						alert(obj.message);
						return;
					} else {
						alert(obj.message);
						localStorage.setItem('id', obj.data.id);
						localStorage.setItem('token', obj.data.token);
						window.location = "Main.jsp";
					}
				}
				
			});	
			
		}
		
		function loginToRegister() {
			window.location = "Register.jsp";
		}

		function loginToMain() {
			window.location = "Main.jsp";
		}	
		
		function forgetPassword() {
			var forgetEmail = document.getElementById("forgetPasswordEmail").value
			if (forgetEmail == '') {
				alert('Please input your Email');
				return;
			}
			$.ajax({
				type: "POST",
				url: "/NTU-IN6206-GameView/Reset",
				data: {
					email: forgetEmail,
				},
				async: false,
				
				success: function(data) {
					var obj = JSON.parse(data)
					if (obj.status_code !== 200) {
						alert("Please check your email address!");
						return;
					} else {
						$("#forgetBox").css("display","initial");
						$("#forgetSubmit").attr("onclick", '');
						$("#forgetSubmit").on("click", function(){
							var forgetEmail = document.getElementById("forgetPasswordEmail").value
							var resetCode = document.getElementById("resetCode").value
							var newPassword = document.getElementById("newPassword").value
							
							if (forgetEmail == '' || resetCode == '' || newPassword == '') {
								alert('Please fill all the input!');
								return;
							}
							
							$.ajax({
								type: "POST",
								url: "/NTU-IN6206-GameView/Validation",
								data: {
									resetcode: resetCode,
									email: forgetEmail,
									newpassword: newPassword
								},
								async: false,
								
								success: function(data) {
									var obj = JSON.parse(data)
									if (obj.status_code !== 200) {
										alert(obj.message);
										return;
									} else {
										alert("Successfully changed the password");
										window.location = "Login.jsp";
									}
								}
								
							});	
							
							
						});
						alert("Reset Code has been sent to your email.");
					}
				}
				
			});	
		}
	</script>
</html>