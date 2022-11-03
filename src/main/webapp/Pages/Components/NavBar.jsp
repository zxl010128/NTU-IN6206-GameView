<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<link href="../CSS/NavBar.css" rel="stylesheet" type="text/css">
		<meta charset="UTF-8">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous"><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
		<title>GameView</title>
	</head>
	<body>
	<nav class="navbar sticky-top" id="navBar">
	  <div class="container-fluid">
	  	<div onClick=topFunction()>
	  	<img src="../../Assets/Icons/GameViewLogo.png" class="navbarLogo"/>
	  	</div>
	  	<div>
	  	<span class="joinUsText" id="joinUsText" onClick=navbarToLogin()> Join Us</span>
	    <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar" aria-controls="offcanvasNavbar" id="clickButtonNavBar">
	      <span class="navbar-toggler-icon"></span>
	    </button>
	    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel">
	      <div class="offcanvas-header">
	        <h5 class="offcanvas-title" id="offcanvasNavbarLabel">GameView</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
	      </div>
	      <div class="offcanvas-body">
	        <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
		        <li class="nav-item">
		          <a class="nav-link active" aria-current="page" href="Main.jsp">Home</a>
		        </li>
				<li class="nav-item">
		          <a class="nav-link" href="Discussion.jsp" >Discussion</a>
		        </li>
		        <li class="nav-item" id="profileButton">
		          <a class="nav-link" href="Profile.jsp" id="profileLink">Profile</a>
		        </li>
		        <li class="nav-item">
		          <a class="nav-link" href="Product.jsp" >Products</a>
		        </li>
		        <li class="nav-item">
		          <a class="nav-link" href="Statistics.jsp" >Statistics</a>
		        </li>
	        </ul>
	          <div style="display: flex; flex-direction: row">
	         	<input class="form-control me-2" type="search" placeholder="Search" aria-label="Search" id="searchInput" style="width: 250px;">
	          	<button class="btn btn-outline-success" type="submit" onClick=searchText()>Search</button>
	          </div>

	      </div>
	      </div>
	    </div>
	  </div>
	</nav>


	</body>
	
	<script type="text/javascript">
		if (localStorage.getItem("token") != null && localStorage.getItem("token") != '' ) { 		
			document.querySelector("#joinUsText").remove();
			document.getElementById("profileLink").setAttribute('href', 'Profile.jsp?id=' + localStorage.getItem("id"))
		} else {
			document.querySelector("#profileButton").remove();
		}
		
		function navbarToLogin() {
			window.location = "Login.jsp";
		}	
		
		function topFunction() {
		    document.body.scrollTop = 0;
		    document.documentElement.scrollTop = 0;
		}
		function searchText() {
			var searchInput = document.getElementById("searchInput").value
			console.log(searchInput)
			if (searchInput == "") {
				alert('Please enter the search query')
				window.location = "Main.jsp"
				return;
			}
			window.location = "Search.jsp?query=" + searchInput
		}
	</script>
</html>