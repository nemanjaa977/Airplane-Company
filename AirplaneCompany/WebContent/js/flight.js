$(document).ready(function() {
	
	var nav = $('#nav');
	var navUser = $('#navLogged');
	var flightId = window.location.search.slice(1).split('&')[0].split('=')[1];
	
	$.get('UserServlet', {}, function(data){
		console.log('Logged: ' + data.logged);
		if(data.logged != null){
			nav.append("<li class='nav-item'>" +
							"<a class='nav-link' href='addTicket.html'>Reservation/Sale ticket</a>" +
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
				document.getElementById('manageUsers').style.display='block';
				$('#allReservationTicketByAdmin').show();
				$('#allSoldTicketByAdmin').show();
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
		
		$('#divButton').append("<button type='button' id='"+data.flight.id+"' class='btn btn-success takeTicketFlight'><i class='fas fa-ticket-alt'></i>  Take a ticket</button>");
	
		if(data.logged.role == 'ADMIN'){
			$('#divButton').append("<button type='button' id='"+data.flight.id+"' class='btn btn-primary'><i class='far fa-edit'></i>  Edit</button>" +
					"<button type='button' id='"+data.flight.id+"' class='btn btn-danger'><i class='fas fa-trash'></i>  Delete</button>");
		}
		
	});
	
	$(document).on('click',".takeTicketFlight", function(event){
		var path = "addTicket.html?id="+flightId;
		window.location.replace(path);
		
		event.preventDefault();
		return false;
	});
	
	$.get('TicketServlet', {}, function(data){
		console.log(data);
		for(i in data.tickets){
			var t = data.tickets[i];
			if(t.goingFlight.id == flightId && t.dateReservation != null){
				$('#tbody_2').append("<tr id='allData'>" +
					      "<td><a href='ticket.html?id="+t.id+"' class='dateRR'>"+t.dateReservation+"</a></td>" +
					      "<td id='seatSorttt'>"+t.seatOnGoingFlight+"</td>" +
					      "<td>"+t.seatOnReverseFlight+"</td>" +
					      "<td><a href='user.html?id="+t.userCreateReservationOrSaleTicket.id+"' class='userRR'>"+t.userCreateReservationOrSaleTicket.username+"</a></td>" +
					    "</tr>");
			}
		}
	});
	
	$.get('TicketServlet', {}, function(data){
		console.log(data);
		for(i in data.tickets){
			var t = data.tickets[i];
			if(t.goingFlight.id == flightId && t.dateOfSaleTicket != null){
				$('#tbody_3').append("<tr>" +
					      "<td><a href='ticket.html?id="+t.id+"' class='dateSS'>"+t.dateOfSaleTicket+"</a></td>" +
					      "<td>"+t.seatOnGoingFlight+"</td>" +
					      "<td>"+t.seatOnReverseFlight+"</td>" +
					      "<td><a href='user.html?id="+t.userCreateReservationOrSaleTicket.id+"' class='userRR'>"+t.userCreateReservationOrSaleTicket.username+"</a></td>" +
					    "</tr>");
			}
		}
	});
	
	
});

