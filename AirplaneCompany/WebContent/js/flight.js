$(document).ready(function() {
	
	var nav = $('#nav');
	var navUser = $('#navLogged');
	var flightId = window.location.search.slice(1).split('&')[0].split('=')[1];
	
	$.get('UserServlet', {}, function(data){
		console.log('Logged: ' + data.logged);
		if(data.logged != null){
			nav.append("<li class='nav-item'>" +
							"<a class='nav-link' href='#'>Reservation/Sale ticket</a>" +
					   "</li>" +
					   "<li class='nav-item dropdown'>" +
					   		"<a class='nav-link dropdown-toggle' href='#' id='navbarDropdown' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'> Profile</a>" +
				   			"<div class='dropdown-menu' aria-labelledby='navbarDropdown'>" +
					   			"<a class='dropdown-item' href='user.html?username="+data.logged.id+"'>My Profile</a>" +
					   			"<a class='dropdown-item' href='users.html' id='manageUsers'>Manage Users</a>" +
					   			"<div class='dropdown-divider'></div>" +
				   				"<a class='dropdown-item' href='LogOutServlet'>Logout</a>" +
				   			"</div>" +
				   	   "</li>");
			if(data.logged.role == "ADMIN"){
				$('#mainn').append("<button type='button' class='btn btn-primary'><i class='fas fa-plus'></i>  Add new flight</button>");
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
	
	$.get('FlightServlet', {'id': flightId}, function(data){
		
		$('#title').text("Flight for " + data.flight.number + " number");
		
		$('#readNumber').text(data.flight.number);
		$('#readGoingDate').text(data.flight.dateGoing);
		$('#readcomingDate').text(data.flight.dateComing);
		$('#readGoingAirport').text(data.flight.goingAirport.name);
		$('#readCominDate').text(data.flight.comingAirport.name);
		$('#readPriceTicket').text(data.flight.priceTicket + " $");
	});
	

	
});