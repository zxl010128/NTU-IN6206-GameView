<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
		<link href="../CSS/Auth.css" rel="stylesheet" type="text/css">
		<title>GameView</title>
	</head>
	
	<body>
	
		<%@ include file="../Components/Preload.jsp" %>
		<div class=loginADBox>
			<img src="../../Assets/Pics/GameView.jpg" class="loginAD"/>
		</div>
		
		<div class="loginBox">
		
				<div onClick=registerToMain()>
					<img src="../../Assets/Logos/backIcon.svg" class="authBack"/>
				</div>
			
			<span class="loginTitle">
				Register to GameView
			</span>
	
			<div class="loginInfoBox" style="margin-top: -15px">
				<div style="width: 90%; margin: 0 auto;">
					<div style="margin-top: 20px">
						<span>Username</span>
						<input type="text" class="form-control" id="registerUsername">
					</div>
					<div style="margin-top: 20px">
						<span>Email</span>
						<input type="text" class="form-control" id="registerEmail">
					</div>
					<div style="margin-top: 20px">
						<span>Phone Number</span>
						<input type="text" class="form-control" id="registerPhonenum">
					</div>
					<div style="margin-top: 20px">
						<span>DoB</span>
						<input type="date" class="form-control" id="registerDoB">
					</div>
					<div style="margin-top: 20px">
						<span>Gender</span>
						<select class="form-select" aria-label="Default select example" id="registerGender">
							<option selected value="2">Prefer not to say</option>
							<option value="0">Male</option>
							<option value="1">Female</option>
						</select>
					</div>
					<div style="margin-top: 20px">
						<span>Password</span>
						<input type="password" class="form-control" id="registerPassword">	
					</div>
				</div> 
			</div>
			
			<button type="button" class="btn btn-primary" id="firstButtonAuth" onClick=registerSubmit()>Sign Up</button>
			<span class="registerForget" onClick=registerToLogin()>Already have an account?</span>
			
		</div>
	
	</body>
	<script src="https://code.jquery.com/jquery-3.6.1.js" integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI=" crossorigin="anonymous"></script>
	<script type="text/javascript">
	
		function registerToLogin() {
			window.location = "Login.jsp";
		}
	
		function registerToMain() {
			window.location = "Main.jsp";
		}	
		
		function registerSubmit() {
			var username = document.getElementById("registerUsername").value
			var email = document.getElementById("registerEmail").value
			var phonenum = document.getElementById("registerPhonenum").value
			var dob = document.getElementById("registerDoB").value
			var gender = document.getElementById("registerGender").value
			var password = document.getElementById("registerPassword").value
			
			$.ajax({
				type: "POST",
				url: "/NTU-IN6206-GameView/Register",
				data: {
					username: username,
					email: email,
					phonenumber: phonenum,
					password: password,
					dob: dob,
					gender: gender
				},
				async: false,
				
				success: function(data) {
					var obj = JSON.parse(data)
					if (obj.status_code !== 200) {
						alert(obj.message);
						return;
					} else {
						alert("Successfully registered");
						window.location = "Login.jsp";
					}
				}
				
			});	
		}
	</script>
</html>