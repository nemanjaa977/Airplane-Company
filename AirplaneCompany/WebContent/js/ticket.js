$(document).ready(function() {
	
	var nav = $('#nav');
	var navUser = $('#navLogged');
	var ticketId = window.location.search.slice(1).split('&')[0].split('=')[1];
	
	var dateGoingForEdit = null;
	var goingFlightForLoadSeat = null;
	var comingFlightForLoadSeat = null;
	
	$.get('UserServlet', {}, function(data){
		console.log('Logged: ' + data.logged);
		if(data.logged != null){
			nav.append("<li class='nav-item'>" +
							"<a class='nav-link' id='rsTiket' href='addTicket.html'>Reservation/Sale ticket</a>" +
					   "</li>" +
					   "<li class='nav-item dropdown'>" +
					   		"<a class='nav-link dropdown-toggle' href='#' id='navbarDropdown' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'> Profile</a>" +
				   			"<div class='dropdown-menu' aria-labelledby='navbarDropdown'>" +
					   			"<a class='dropdown-item' href='user.html?username="+data.logged.id+"'>My Profile</a>" +
					   			"<a class='dropdown-item' href='users.html' id='manageUsers'>Manage Users</a>" +
					   			"<a class='dropdown-item' href='reports.html' id='reports'>Reports</a>" +
					   			"<div class='dropdown-divider'></div>" +
				   				"<a class='dropdown-item' href='LogOutServlet'>Logout</a>" +
				   			"</div>" +
				   	   "</li>");
			if(data.logged.role == "ADMIN"){
				document.getElementById('manageUsers').style.display='block';
				document.getElementById('reports').style.display='block';
			}
			
		}
	});
	
	$.get('TicketServlet', {'id': ticketId}, function(data){
			console.log(data);
		
			goingFlightForLoadSeat = data.ticket.goingFlight.id;
			comingFlightForLoadSeat = data.ticket.reverseFlight.id;
			
			$('#title').text('Ticket for ' + data.ticket.goingFlight.number + ' flight');
			
			$('#goingFlight').text(data.ticket.goingFlight.number);
			$('#reverseFlight').text(data.ticket.reverseFlight.number);
			$('#seatGoing').text(data.ticket.seatOnGoingFlight);
			$('#seatReverse').text(data.ticket.seatOnReverseFlight);
			$('#dateReservation').text(data.ticket.dateReservation);
			$('#dateSale').text(data.ticket.dateOfSaleTicket); //
			$('#userr').text(data.ticket.userCreateReservationOrSaleTicket.username);
			$('#userr').attr("href", "user.html?id="+data.ticket.userCreateReservationOrSaleTicket.id);
			$('#namePass').text(data.ticket.firstNamePassenger);
			$('#lastnamePass').text(data.ticket.lastNamePassenger);
			$('#proceTT').text(data.price + " $");
			
			if(data.ticket.dateOfSaleTicket == null){
				$('#goingFlight').attr("href", "flight.html?id="+data.ticket.goingFlight.id);
				$('#reverseFlight').attr("href", "flight.html?id="+data.ticket.reverseFlight.id);
			}
			
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
			
			if(data.logged.blocked == true){
				$('#rsTiket').hide();
				$('.buyTicket').hide();
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
	
	$(document).on('click',".editTicketButton", function(event){
		
		$.get('FlightServlet', {'id': goingFlightForLoadSeat }, function(data){
			var f = data.flight;
			for(var i=1;i<f.seatNumber+1;i++){
				$('#seatOfGoingFlight').append("<option value='"+i+"'>"+i+"</option>");
			}		
	    });
		$.get('FlightServlet', {'id': comingFlightForLoadSeat}, function(data){
			if(data.flight == null){
				alert("This ticket can't be changed because reverse flight was deleted!");
				$('#editDiv').fadeOut();
			}
			console.log(comingFlightForLoadSeat);
			var f = data.flight;
			for(var i=1;i<f.seatNumber+1;i++){
				$('#seatOfComingFlight').append("<option value='"+i+"'>"+i+"</option>");
			}
	    });
		
		$.get('TicketServlet', {'id': ticketId}, function(data){
			$('#firstNamePass').val(data.ticket.firstNamePassenger);
			$('#lastNamePass').val(data.ticket.lastNamePassenger);
			$('#seatOfGoingFlight').val(data.ticket.seatOnGoingFlight);
			$('#seatOfComingFlight').val(data.ticket.seatOnReverseFlight);
			console.log(data);
		});
		
		$('#editDiv').fadeIn();
		
		event.preventDefault();
		return false;
	});
	
	$(document).on('click',"#closeEdit", function(event){
		$('#editDiv').fadeOut();
		
		event.preventDefault();
		return false;
	});
	
	$(document).on('click',"#submitTicket", function(event){
		var newFirstName = $('#firstNamePass').val();
		var newLastName = $('#lastNamePass').val();
		var newSeatForGoing = $('#seatOfGoingFlight').val();
		var newSeatForComing = $('#seatOfComingFlight').val();
		
		var json = {
				'status': 'edit',
				'id': ticketId,
				'firstNamePassenger': newFirstName,
				'lastNamePassenger': newLastName,
				'seatForGoing': newSeatForGoing,
				'seatForComing': newSeatForComing
		}
		
		$.post('TicketServlet',json,function(data){
			if(data.status == "success"){
				var location="ticket.html?id="+ticketId;
				window.location.replace(location);
			}
		});
		
		event.preventDefault();
		return false;
	});
	
	$(document).on('click',".deleteTicketButton", function(event){
		
		var json = {
				'status': 'delete',
				'id': ticketId
		}
		
		var x=confirm("Are you shure ?");
		if(x){
			$.post('TicketServlet',json,function(data){
				if(data.status == "success"){
					var location="index.html";
					window.location.replace(location);
				}
			});
		}else{
			return ;
		}
		
		event.preventDefault();
		return false;
	});
	
	
	
});