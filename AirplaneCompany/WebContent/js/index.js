$(document).ready(function() {
	
	var nav = $('#nav');
	var navUser = $('#navLogged');
	
	$.get('UserServlet', {}, function(data){
		console.log('Logged: ' + data.logged);
		if(data.logged != null){
			nav.append("<li class='nav-item'>" +
							"<a class='nav-link' href='#'>Reservation/Sale ticket</a>" +
					   "</li>" +
					   "<li class='nav-item dropdown'>" +
					   		"<a class='nav-link dropdown-toggle' href='#' id='navbarDropdown' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'> Profile</a>" +
				   			"<div class='dropdown-menu' aria-labelledby='navbarDropdown'>" +
					   			"<a class='dropdown-item' href='user.html?id="+data.logged.id+"'>My Profile</a>" +
					   			"<a class='dropdown-item' href='users.html' id='manageUsers'>Manage Users</a>" +
					   			"<div class='dropdown-divider'></div>" +
				   				"<a class='dropdown-item' href='LogOutServlet'>Logout</a>" +
				   			"</div>" +
				   	   "</li>");
			if(data.logged.role == "ADMIN"){
				$('#mainn').append("<button type='button' id='addFlight' class='btn btn-primary'><i class='fas fa-plus'></i>  Add new flight</button>");
				document.getElementById('manageUsers').style.display='block';
			}
			
		}else{
			navUser.append("<li class='nav-item'>" +
								"<a class='nav-link' href='login.html'>Login</a>" +
						   "</li>" +
						   "<li class='nav-item'>" +
						   		"<a class='nav-link' href='register.html'>Register</a>" +
						   "</li>");
		}
	});
	
	$.get('FlightServlet', {}, function(data){
		for(i in data.flights){
			var f = data.flights[i];
			$('#tbody').append("<tr>" +
							      "<td><a href='flight.html?id="+f.id+"' class='numberID'>"+f.number+"</a></td>" +
							      "<td>"+f.dateGoing+"</td>" +
							      "<td>"+f.dateComing+"</td>" +
							      "<td>"+f.goingAirport.name+"</td>" +
							      "<td>"+f.comingAirport.name+"</td>" +
							      "<td>"+f.priceTicket+" $</td>" +
							    "</tr>");
		}
	});
	
	$(document).on('click',"#searchOK", function(event){
		var inputText = $('#inputSearch').val();
		
		$.get("SearchServlet",{"inputText": inputText},function(data){
			$('#tbody').empty();
			for(i in data.flights){
				var f = data.flights[i];
				$('#tbody').append("<tr>" +
								      "<td><a href='flight.html?id="+f.id+"' class='numberID'>"+f.number+"</a></td>" +
								      "<td>"+f.dateGoing+"</td>" +
								      "<td>"+f.dateComing+"</td>" +
								      "<td>"+f.goingAirport.name+"</td>" +
								      "<td>"+f.comingAirport.name+"</td>" +
								      "<td>"+f.priceTicket+" $</td>" +
								    "</tr>");
			}
		});
		
		event.preventDefault();
		return false;
	});
	
});