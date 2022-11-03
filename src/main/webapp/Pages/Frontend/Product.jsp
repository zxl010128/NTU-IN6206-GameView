<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
		<link href="../CSS/Product.css" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>GameView</title>
	</head>
	
	<body>

	<%@ include file="../Components/Preload.jsp" %>
	<%@ include file="../Components/NavBar.jsp" %>
	
	<div class="productContainer">
	
		<div style="width: 100%; ">
			<img src="../../Assets/Pics/ProductPage.png" style="width: 100%;"/>	
		</div>
		
		<div class="productBox" id="productContainer">

		</div>

	
	</div>


	<%@ include file="../Components/Footer.jsp" %>
		
	</body>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.6.1.js" integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI=" crossorigin="anonymous"></script>
	<script>
	
		var isLogin = localStorage.getItem("token") != null && localStorage.getItem("token") != ''
		
		function redeemProduct(id) {
			if (confirm("Confirm to redeem it?") == true) {
				$.ajax({
					type: "POST",
					url: "/NTU-IN6206-GameView/Redeem",
					data: {
						token: localStorage.getItem("token"),
						product_id: id
					},
					async: false,
					
					success: function(data) {
						var obj = JSON.parse(data)
						if (obj.status_code !== 200) {
							alert(obj.message);
							return;
						} else {
							alert("Successfully redeem it.");
							window.location = "Main.jsp";
						}
					}	
				})
			} else {
				return;
			}
			
		}
		
		
		$.ajax({
			type: "GET",
			url: "/NTU-IN6206-GameView/ShowProduct",
			async: false,
			
			success: function(data) {
				var obj = JSON.parse(data)
				console.log(obj)

				for (var i = 0; i < obj.data.length; i++) {
					
					const pid = obj.data[i].id
				  	const parent = document.getElementById('productContainer')
				  	
				  	const card = document.createElement('div')
				  	card.className = "card"
				  	card.setAttribute('style', 'width: 300px; margin-top: 40px; box-shadow: 5px 5px 10px -4px black;')
				  	
				  	parent.append(card)
				  	
				  	const img = document.createElement('img')
				  	img.setAttribute('src', obj.data[i].productPic)
				  	img.className = "card-img-top"
				  	
				  	card.append(img)
				  	
				  	const card_body = document.createElement('div')
				  	card_body.className = "card-body"
				  	
				  	card.append(card_body)
				  	
				  	const card_title = document.createElement('h5')
				  	card_title.setAttribute('style', "font-family: Lucida Console; font-size: 15px")
				  	card_title.innerHTML = obj.data[i].productName
				  	
				  	card_body.append(card_title)
				  	
				  	const smallBox = document.createElement('div')
				  	smallBox.setAttribute('style', "display: flex; flex-direction: row; justify-content: space-between; align-items: center;")
				
					card_body.append(smallBox)
					
					const rightBox = document.createElement('div')
				  	const icon = document.createElement('img')
				  	icon.setAttribute('src', '../../Assets/Icons/Coin.svg')
				  	icon.setAttribute('style', 'width: 30px; margin-right: 10px')
				  	
					const coin = document.createElement('span')
					coin.innerHTML = obj.data[i].price
				  	
				  	rightBox.append(icon);
				  	rightBox.append(coin)	
				
					smallBox.append(rightBox)
					
					const redeemButton = document.createElement('a')
					if (!isLogin) {
						redeemButton.className = "btn btn-primary disabled"
					} else {
						redeemButton.className = "btn btn-primary"
					}
					redeemButton.innerHTML = "Redeem"
					redeemButton.addEventListener('click', redeemProduct.bind(this, pid))
					
					smallBox.append(redeemButton)

				}
				
			}
			
		});	
		

	
	</script>

</html>