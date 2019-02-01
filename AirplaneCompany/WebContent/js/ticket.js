$(document).ready(function() {
	
	var nav = $('#nav');
	var navUser = $('#navLogged');
	var ticketId = window.location.search.slice(1).split('&')[0].split('=')[1];
	
	var dateGoingForEdit = null;
	
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
			}
			
		}
	});
	
	$.get('TicketServlet', {'id': ticketId}, function(data){
			
			$('#goingFlight').text('#' + data.ticket.goingFlight.id);
			$('#goingFlight').attr("href", "flight.html?id="+data.ticket.goingFlight.id);
			$('#reverseFlight').text('#' + data.ticket.reverseFlight.id);
			$('#reverseFlight').attr("href", "flight.html?id="+data.ticket.reverseFlight.id);
			$('#seatGoing').text(data.ticket.seatOnGoingFlight);
			$('#seatReverse').text(data.ticket.seatOnReverseFlight);
			$('#dateReservation').text(data.ticket.dateReservation);
			$('#dateSale').text(data.ticket.dateOfSaleTicket); //
			$('#userr').text(data.ticket.userCreateReservationOrSaleTicket.username);
			$('#userr').attr("href", "user.html?id="+data.ticket.userCreateReservationOrSaleTicket.id);
			$('#namePass').text(data.ticket.firstNamePassenger);
			$('#lastnamePass').text(data.ticket.lastNamePassenger);
			$('#proceTT').text("Price $");
			
			if(data.logged != null){
				if(data.ticket.dateReservation != null){
					if(data.logged.username == data.ticket.userCreateReservationOrSaleTicket.username || data.logged.role == 'ADMIN')
						$('#divButton').append("<button type='button' id='"+data.ticket.id+"' class='btn btn-success buyTicket'><i class='fas fa-ticket-alt'></i>  Buy ticket</button>");
						$('#dateSale').text('---');
					}
				}
		
			if(data.logged.username == data.ticket.userCreateReservationOrSaleTicket.username || data.logged.role == 'ADMIN'){
				$('#divButton').append("<button type='button' id='"+data.ticket.id+"' class='btn btn-primary editTicketButton'><i class='far fa-edit'></i>  Edit</button>" +
						"<button type='button' id='"+data.ticket.id+"' class='btn btn-danger deleteTicketButton'><i class='fas fa-trash'></i>  Delete</button>");
			}
			
			if(data.ticket.dateOfSaleTicket != null){
				$('#dateReservation').text('---');
				$('.editTicketButton').hide();
				$('.deleteTicketButton').hide();
			}
			
		});
	
	$(document).on('click',".buyTicket", function(event){
		var ticketID = $(this).attr('id');	
		var json = {
				'status': 'buy',
				'id': ticketID
		}
		
		$.post('TicketServlet',json,function(data){
			window.location.reload();
		});
		
		event.preventDefault();
		return false;
	});
	
	
	
});