<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
		<link href="../CSS/Profile.css" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>GameView</title>
	</head>
	
	<body>

	<%@ include file="../Components/Preload.jsp" %>
	<%@ include file="../Components/NavBar.jsp" %>
	
	<div class="profileContainer">
	
		<div style="width: 100%; ">
			<img src="../../Assets/Pics/ProfilePage.png" style="width: 100%;"/>	
		</div>
		
		<div style="width: 95%; margin: 40px auto 0 auto;  background-color: rgba(255,255,255,0.25); border-radius: 20px">
			<div class="profileInfoBox">
				<img id="profileFace" style="width: 150px; height: 150px"/>	
				<div class="profilrInfoSmallBox">
					<h3 class="profileInfoTitle">UserName</h3>
					<div id="profileUsername"></div>
					<h3 class="profileInfoTitle">Email</h3>
					<div id="profileEmail"></div>
					<h3 class="profileInfoTitle">Phone</h3>
					<div id="profilePhone"></div>
				</div>
				
				<div class="profilrInfoSmallBox">
					<h3 class="profileInfoTitle">DoB</h3>
					<div id="profileDoB"></div>
					<h3 class="profileInfoTitle">Gender</h3>
					<div id="profileGender"></div>
					<h3 class="profileInfoTitle">Coins</h3>
					<div id="profileCoins"></div>
				</div>
			</div>
			
		</div>
		
		<div id="profileControl" style="width: 95%; margin: 40px auto 0 auto;  background-color: rgba(255,255,255,0.25); border-radius: 20px; padding: 0 20px 20px 20px; display: flex; flex-direction: row; justify-content: space-around; flex-wrap: wrap;"> 	
			<button type="button" class="btn btn-primary" style="margin-top: 20px" data-bs-toggle="modal" data-bs-target="#uploadModel"> Pic Update</button>
			<button type="button" class="btn btn-primary" style="margin-top: 20px" data-bs-toggle="modal" data-bs-target="#updateModel">Info Update</button>
			<button type="button" class="btn btn-primary" style="margin-top: 20px" onClick=submitLogout()>Log Out</button>
		</div>
		
		<div style="width: 95%; margin: 40px auto 0 auto;  background-color: rgba(255,255,255,0.25); border-radius: 20px; padding: 10px "> 	
			<select class="form-select" aria-label="Default select example" id="profileSelector" onchange="selectChangedProfile(this.options[this.selectedIndex].value)">
			  <option value="1" selected>Posts</option>
			  <option value="2">Liked Posts</option>
			  <option value="3">Bookmarked Posts</option>
			</select>
			<div id="postList">
			</div>
		</div>
		
	
	</div>


	<%@ include file="../Components/Footer.jsp" %>
		
	<div class="modal fade" id="updateModel" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="exampleModalLabel">Info Update</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <form>
	          <div class="mb-3">
	            <label for="recipient-name" class="col-form-label">Email:</label>
	            <input type="text" class="form-control" id="updateEmail" value="">
	          </div>
	          <div class="mb-3">
	            <label for="recipient-name" class="col-form-label">Phone Number:</label>
	            <input type="text" class="form-control" id="updatePhone" value="">
	          </div>
	          <div class="mb-3">
	            <label for="recipient-name" class="col-form-label">DoB:</label>
	            <input type="date" class="form-control" id="updateDoB" value="">
	          </div>
	          <div class="mb-3">
	            <label for="recipient-name" class="col-form-label">Gender:</label>
	            	<select class="form-select" aria-label="Default select example" id="updateGender">
	            		<option value="3" selected>No Change</option>
						<option value="2">Prefer not to say</option>
						<option value="0">Male</option>
						<option value="1">Female</option>
					</select>
	          </div>
	        </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary" onClick=submitEdit()>Update</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="modal fade" id="uploadModel" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="exampleModalLabel">Facepic Update</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
				<div class="wrapper">
				    <input type="file"  id="input-uploader" name="fileUpload"
				        onchange="handleFileChange(this)" />
				</div>
	      </div>
	    </div>
	  </div>
	</div>
	
	</body>
	<script src="https://code.jquery.com/jquery-3.6.1.js" integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI=" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
	<script>
	
		if (localStorage.getItem("token") == null || localStorage.getItem("token") == '' ) { 		
			document.querySelector("#profileControl").remove();
		} 
		
		function ProfiletoCommentPage(id) {
			
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
		
		const user_id = getQueryVariable('id')
		
		$.ajax({
			type: "GET",
			url: "/NTU-IN6206-GameView/ProfilePostList",
			data: {
				token: localStorage.getItem("token"),
				user_id: user_id
			},
			async: false,
				
			success: function(data) {
				var obj = JSON.parse(data)
				console.log(obj)
				if (obj.data.is_me == 0) {
					document.querySelector("#profileControl").remove();
				}
				document.getElementById("profileUsername").innerHTML = obj.data.user_name;
				document.getElementById("profileEmail").innerHTML = obj.data.email;
				document.getElementById("profilePhone").innerHTML = obj.data.phonenumber;
				document.getElementById("profileDoB").innerHTML = obj.data.dob;
				document.getElementById("updateEmail").value = obj.data.email;
				document.getElementById("updatePhone").value = obj.data.phonenumber;
				document.getElementById("updateDoB").value = obj.data.dob;
				if (obj.data.gender == 0 ) {
					document.getElementById("profileGender").innerHTML = "Male";
				} else if (obj.data.gender == 1 ) {
					document.getElementById("profileGender").innerHTML = "Female";
				} else if (obj.data.gender == 2 ) {
					document.getElementById("profileGender").innerHTML = "Prefer not to say";
				} 
				
				if (obj.data.facepicture == '') {
					document.getElementById("profileFace").setAttribute('src', '../../Assets/Icons/UserImage.png')
				} else {
					document.getElementById("profileFace").setAttribute('src', obj.data.facepicture)
				}
				
				document.getElementById("profileCoins").innerHTML = obj.data.coin;
				
				for (var i = 0; i < obj.data.post_list.length; i++) {
					
					const parent = document.getElementById('postList')
				  	const infoBox = document.createElement('div')
				  	infoBox.className = 'postInfoBox'
				  	
				  	infoBox.addEventListener('click', ProfiletoCommentPage.bind(this,obj.data.post_list[i].ID))
				  	parent.append(infoBox)
				  	
				  	const leftText = document.createElement('span')
				  	
				  	if (obj.data.post_list[i].content.length > 40) {
				  		leftText.innerHTML = obj.data.post_list[i].content.substr(0,40) + "..."
				  	} else {
				  		leftText.innerHTML = obj.data.post_list[i].content
				  	}
				  	
				  	const rightText = document.createElement('span')
				  	rightText.innerHTML = obj.data.post_list[i].createTime
				  	
				  	infoBox.append(leftText);
				  	infoBox.append(rightText);
				  	
				}
			}
				
		});
		
		function submitLogout() {
			$.ajax({
				type: "POST",
				url: "/NTU-IN6206-GameView/Logout",
				data: {
					token: localStorage.getItem("token")
				},
				async: false,
				
				success: function(data) {
					var obj = JSON.parse(data)
					if (obj.status_code !== 200) {
						alert(obj.message);
						return;
					} else {
						alert("Successfully logged out!");
						localStorage.removeItem('id');
						localStorage.removeItem('token');
						window.location = "Main.jsp";
					}
				}
				
			});	
		}
		
		function submitEdit() {

			var email = document.getElementById("updateEmail").value
			var phonenum = document.getElementById("updatePhone").value
			var dob = document.getElementById("updateDoB").value
			var gender = document.getElementById("updateGender").value
			
			console.log(email, phonenum, dob, gender)
			$.ajax({
				type: "POST",
				url: "/NTU-IN6206-GameView/ChangeProfile",
				data: {
					token: localStorage.getItem("token"),
					email: email,
					phonenumber: phonenum,
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
						alert("Successfully changed profile");
						window.location = "Profile.jsp?id=" + localStorage.getItem("id");
					}
				}
				
			});	
		}
		
		function selectChangedProfile(id) {
			
			if (id == '1') {
				const user_id = getQueryVariable('id')
				
				$.ajax({
					type: "GET",
					url: "/NTU-IN6206-GameView/ProfilePostList",
					data: {
						token: localStorage.getItem("token"),
						user_id: user_id
					},
					async: false,
						
					success: function(data) {
						var obj = JSON.parse(data)
						
						const parent = document.getElementById('postList')
						
						while (parent.firstChild) {
						    parent.firstChild.remove()
						}

						for (var i = 0; i < obj.data.post_list.length; i++) {
							
							const parent = document.getElementById('postList')
						  	const infoBox = document.createElement('div')
						  	infoBox.className = 'postInfoBox'
						  	
						  	infoBox.addEventListener('click', ProfiletoCommentPage.bind(this,obj.data.post_list[i].ID))
						  	parent.append(infoBox)
						  	
						  	const leftText = document.createElement('span')
						  	
						  	if (obj.data.post_list[i].content.length > 40) {
						  		leftText.innerHTML = obj.data.post_list[i].content.substr(0,40) + "..."
						  	} else {
						  		leftText.innerHTML = obj.data.post_list[i].content
						  	}
						  	
						  	const rightText = document.createElement('span')
						  	rightText.innerHTML = obj.data.post_list[i].createTime
						  	
						  	infoBox.append(leftText);
						  	infoBox.append(rightText);
						  	
						}
					}
						
				});
				
			} else if (id == '2') {
				const user_id = getQueryVariable('id')
				
				$.ajax({
					type: "GET",
					url: "/NTU-IN6206-GameView/ProfileLikeList",
					data: {
						token: localStorage.getItem("token"),
						user_id: user_id
					},
					async: false,
						
					success: function(data) {
						var obj = JSON.parse(data)
						
						const parent = document.getElementById('postList')
						
						while (parent.firstChild) {
						    parent.firstChild.remove()
						}

						for (var i = 0; i < obj.data.like_list.length; i++) {
							
							const parent = document.getElementById('postList')
						  	const infoBox = document.createElement('div')
						  	infoBox.className = 'postInfoBox'
						  	
						  	infoBox.addEventListener('click', ProfiletoCommentPage.bind(this,obj.data.like_list[i].ID))
						  	parent.append(infoBox)
						  	
						  	const leftText = document.createElement('span')
						  	
						  	if (obj.data.like_list[i].content.length > 40) {
						  		leftText.innerHTML = obj.data.like_list[i].content.substr(0,40) + "..."
						  	} else {
						  		leftText.innerHTML = obj.data.like_list[i].content
						  	}
						  	
						  	const rightText = document.createElement('span')
						  	rightText.innerHTML = obj.data.like_list[i].createTime
						  	
						  	infoBox.append(leftText);
						  	infoBox.append(rightText);
						  	
						}
					}
						
				});				
				
			} else if (id == '3') {
				const user_id = getQueryVariable('id')
				
				$.ajax({
					type: "GET",
					url: "/NTU-IN6206-GameView/ProfileBookmarkList",
					data: {
						token: localStorage.getItem("token"),
						user_id: user_id
					},
					async: false,
						
					success: function(data) {
						var obj = JSON.parse(data)
						
						const parent = document.getElementById('postList')
						
						while (parent.firstChild) {
						    parent.firstChild.remove()
						}

						for (var i = 0; i < obj.data.bookmark_list.length; i++) {
							
							const parent = document.getElementById('postList')
						  	const infoBox = document.createElement('div')
						  	infoBox.className = 'postInfoBox'
						  	infoBox.addEventListener('click', ProfiletoCommentPage.bind(this,obj.data.bookmark_list[i].ID))
						  	parent.append(infoBox)
						  	
						  	const leftText = document.createElement('span')
						  	
						  	if (obj.data.bookmark_list[i].content.length > 40) {
						  		leftText.innerHTML = obj.data.bookmark_list[i].content.substr(0,40) + "..."
						  	} else {
						  		leftText.innerHTML = obj.data.bookmark_list[i].content
						  	}
						  	
						  	const rightText = document.createElement('span')
						  	rightText.innerHTML = obj.data.bookmark_list[i].createTime
						  	
						  	infoBox.append(leftText);
						  	infoBox.append(rightText);
						  	
						}
					}
						
				});				
				
			}
			
		}
		
	    const requestApi = ({
	        url,
	        method = 'GET',
	        ...fetchProps
	    }) => {
	        return fetch(url, {
	            method,
	            ...fetchProps
	        })
	            .then(res => res && res.status === 200 && res.json())
	            .catch(err => alert('error'))
	            .then(res => res)
	    }


	    const showUploader = () => {
	        let uploaderInput = document.getElementById("input-uploader")
	        uploaderInput.click()
	    }

	    handleFileChange = () => {
	        let fileData = document.getElementById("input-uploader")
	        const { files, value, form } = fileData

	        let formData = new FormData();
	        Array.from(files).forEach(item => {
	        formData.append("uploadFile", item),
	        formData.append("token", localStorage.getItem("token"))
	        })
	        console.log(formData)
	        handleUploadFile(formData)
	            .then(res => {
	                if (res && res.success) {
	                    alert('success')
	                   
	                } else {
	                    alert(res.message || 'fail')
	                }
	                console.log(res)
	            })
	            .catch(err => alert('success'))
	    }

	    const handleUploadFile = (formData = {}) =>
	        requestApi({
	            url: 'http://localhost:8080//NTU-IN6206-GameView/ChangeFacepic',
	            method: 'POST',
	            body: formData
	        });
		
	</script>

</html>