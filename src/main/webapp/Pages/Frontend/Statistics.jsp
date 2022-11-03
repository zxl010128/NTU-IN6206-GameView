<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="../CSS/Statistics.css" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>GameView</title>
	</head>
	
	<body>

	<%@ include file="../Components/Preload.jsp" %>
	<%@ include file="../Components/NavBar.jsp" %>
	
	<div class="statContainer">
	
		<div style="width: 100%; ">
			<img src="../../Assets/Pics/StatisticsPage.png" style="width: 100%"/>	
		</div>
		
		<div class="statBox">
			<div class="statInfoBox">
				<div class="smallInfoBox">
					<h2 class="smallInfoBoxTitle">Total Games</h2>
					<span class="smallInfoBoxText" style="color: #FF4236" id="TG"></span>
				</div>
				<div class="smallInfoBox">
					<h2 class="smallInfoBoxTitle">Total Users</h2>
					<span class="smallInfoBoxText" style="color: #4A00BB" id="TU"></span>
				</div>
				<div class="smallInfoBox">
					<h2 class="smallInfoBoxTitle">Active Users</h2>
					<span class="smallInfoBoxText" style="color: #25BD00" id="AU"></span>
				</div>
			</div>
			<div id="genderRatio"></div>
			<div id="CommentRatio"></div>
			<div id="averageChart"></div>
		</div>

	
	</div>

	<%@ include file="../Components/Footer.jsp" %>
		
	</body>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.6.1.js" integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI=" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/echarts/5.4.0/echarts.min.js" integrity="sha512-LYmkblt36DJsQPmCK+cK5A6Gp6uT7fLXQXAX0bMa763tf+DgiiH3+AwhcuGDAxM1SvlimjwKbkMPL3ZM1qLbag==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script>
		$.ajax({
			type: "GET",
			url: "/NTU-IN6206-GameView/Data",
			async: false,
			
			success: function(data) {
				var obj = JSON.parse(data)
				$("#TG").html(obj.data.games);
				$("#TU").html(obj.data.users);
				$("#AU").html(obj.data.onlineusers);
			}
			
		});	
		
		var genderChart = echarts.init(document.getElementById('genderRatio'));
		
		$.ajax({
			type: "GET",
			url: "/NTU-IN6206-GameView/GenderRatio",
			async: false,
			
			success: function(data) {
				var obj = JSON.parse(data)
				
				var chartData = []
				chartData.push({value: obj.data["Female Users"], name: "Female Users"})	
				chartData.push({value: obj.data["Male Users"], name: "Male Users"})	
				chartData.push({value: obj.data["Unknown Users"], name: "Unknown Users"})	
				
				var option = {
				      title: {
				          text: 'Gender Ratio of the GameView Forum',
				          left: 'center',
				          padding: [20,0,0,0]
			          },
					  tooltip: {
					    trigger: 'item'
					  },
					  legend: {
					    top: '8%',
					    left: 'center'
					  },
					  series: [
					    {
					      name: 'Access From',
					      type: 'pie',
					      radius: ['40%', '70%'],
					      avoidLabelOverlap: false,
					      itemStyle: {
					        borderRadius: 10,
					        borderColor: '#fff',
					        borderWidth: 2
					      },
					      label: {
					        show: false,
					        position: 'center'
					      },
					      emphasis: {
					        label: {
					          show: true,
					          fontSize: '30',
					          fontWeight: 'bold'
					        }
					      },
					      labelLine: {
					        show: false
					      },
					      data: chartData,
					    }
					  ]
					};
				
				genderChart.setOption(option)
			}
			
		});	
		
		var commentChart = echarts.init(document.getElementById('CommentRatio'));
		
		$.ajax({
			type: "GET",
			url: "/NTU-IN6206-GameView/CommentData",
			async: false,
			
			success: function(data) {
				var obj = JSON.parse(data)
				
				var chartData = []
				
				for (var i = 0; i < obj.data.length; i++) {
					chartData.push({value:obj.data[i].comments , name:obj.data[i].category})
				}
				var option = {
				      title: {
				          text: 'Comment Ratio of the GameView Forum',
				          left: 'center',
				          padding: [20,0,0,0]
			          },
			          legend: {
			        	    top: '90%'
			        	  },
			        	  toolbox: {
			        	    show: true,
			        	  },
					  series: [
					    {
					    	name: 'Nightingale Chart',
					        type: 'pie',
					        radius: [50, 250],
					        center: ['50%', '50%'],
					        roseType: 'area',
					        itemStyle: {
					          borderRadius: 4
					        },
					      data: chartData,
					    }
					  ]
					};
				
				commentChart.setOption(option)
			
			}
			
		});	
		
		var averageChart = echarts.init(document.getElementById('averageChart'));
		
		$.ajax({
			type: "GET",
			url: "/NTU-IN6206-GameView/AverageScores",
			async: false,
			
			success: function(data) {
				var obj = JSON.parse(data)
				console.log(obj.data)
				
				var xData = [];
				var yData = [];
				
				for (var i = 0; i < obj.data.length; i++) {
					xData.push(obj.data[i].category)
					yData.push(obj.data[i].avgscore)
				}
			
				var option = {
					  title: {
				      text: 'Average Score of Different Game Category',
		  	          left: 'center',
			          padding: [20,0,0,0]
				      },
				      tooltip: {
				    	    trigger: 'axis',
				    	    axisPointer: {
				    	      type: 'shadow'
				    	    }
				    	  },
				      grid: {
				    	    left: '3%',
				    	    right: '4%',
				    	    bottom: '3%',
				    	    containLabel: true
				    	  },
					  xAxis: {
					    type: 'category',
					    data: xData,
					    axisLabel: {
					    	interval: 0,
					    	show: true,
					    	rotate: 315
					    }
					  },
					  yAxis: {
					    type: 'value'
					  },
					  series: [
					    {
					      data: yData,
					      type: 'bar'
					    }
					  ]
					};
				
				averageChart.setOption(option)
			}
			
		});	
	</script>
</html>