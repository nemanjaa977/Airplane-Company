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
		
		if(data.logged != null){
			$('#divButton').append("<button type='button' id='"+data.flight.id+"' class='btn btn-success takeTicketFlight'><i class='fas fa-ticket-alt'></i>  Take a ticket</button>");
		}
	
		if(data.logged.role == 'ADMIN'){
			$('#divButton').append("<button type='button' id='"+data.flight.id+"' class='btn btn-primary editFlightButton'><i class='far fa-edit'></i>  Edit</button>" +
					"<button type='button' id='"+data.flight.id+"' class='btn btn-danger'><i class='fas fa-trash'></i>  Delete</button>");
			
		}
		
	});
	
	$(document).on('click',".takeTicketFlight", function(event){
		var path = "addTicket.html?id="+flightId;
		window.location.replace(path);
		
		event.preventDefault();
		return false;
	});
	
	// all reservation ticket for admin
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
	
	// all sold ticket for admin
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
	
	$(document).on('click',".editFlightButton", function(event){
		var idFlight = $(this).attr('id');
		$('#formEditFlight').fadeIn();
		
		$.get('AirportServlet', {}, function(data){
			for(i in data.airports){
				$('#goingAirport').append("<option value='"+data.airports[i].id+"'>"+data.airports[i].name+"</option>");
				$('#comingAirport').append("<option value='"+data.airports[i].id+"'>"+data.airports[i].name+"</option>");
			}
		});
		
		$.get('FlightServlet', {'id': idFlight}, function(data){
			$('#inputFlightNumber').val(data.flight.number);
			$('#dateGoing').val(data.flight.dateGoing);
			$('#dateComing').val(data.flight.dateComing);
			$('#inputSeatNumber').val(data.flight.seatNumber);
			$('#inputPriceTicket').val(data.flight.priceTicket);
			
			$('#goingAirport').val(data.flight.goingAirport.id);
			$('#comingAirport').val(data.flight.comingAirport.id);
			
		});
		
		
		event.preventDefault();
		return false;
	});

	// click edit submit button flight
	$(document).on('click',"#editFlightSubmit", function(event){
		var dateComingForEdit = $('#dateComing').val();
		var seatNumberForEdit = $('#inputSeatNumber').val();
		var priceTicketForEdit = $('#inputPriceTicket').val();
		var goingAirportForEdit = $('#goingAirport').val();
		var comingAirportForEdit = $('#comingAirport').val();
		
		var json = {
				'status': 'edit',
				'id': flightId,
				'dateC': dateComingForEdit,
				'airportG': goingAirportForEdit,
				'airportC': comingAirportForEdit,
				'seatNumber': seatNumberForEdit,
				'price': priceTicketForEdit
		};
		var datee = new Date();
		console.log(datee);
		var dateComigConvert = new Date(dateComingForEdit);
		console.log(dateComigConvert);
		
		if(dateComigConvert > datee){
			$.post('FlightServlet',json,function(data){
				if(data.status == "success"){
					var location="flight.html?id="+flightId;
					window.location.replace(location);
				}
			});
		}else{
			alert('False date for coming!');
		}
		
		event.preventDefault();
		return false;
	});
	
	$(document).on('click',"#closeEditForm", function(event){
		$('#formEditFlight').fadeOut();
		
		event.preventDefault();
		return false;
	});
	
	
});

