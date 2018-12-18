$(document).ready(function() {
	
	var nav = $('#nav');
	var navUser = $('#navLogged');
	var flightId = window.location.search.slice(1).split('&')[0].split('=')[1];
	var message = $('#poruka');
	
	var startFlight = null;
	var endFlight = null;
	var loggedUU = null;
	
	$.get('UserServlet', {}, function(data){
		console.log('Logged: ' + data.logged);
		loggedUU = data.logged.id; 
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
			
		}else{
			navUser.append("<li class='nav-item'>" +
								"<a class='nav-link' href='login.html'>Login</a>" +
						   "</li>" +
						   "<li class='nav-item'>" +
						   		"<a class='nav-link' href='register.html'>Register</a>" +
						   "</li>");
		}
	});
	
	if(flightId != null){
		$.get('FlightServlet', {'id':flightId }, function(data){
				var f = data.flight;
				$('#tbody_1').append("<tr>" +
								      "<td>"+f.number+"</td>" +
								      "<td>"+f.dateGoing+"</td>" +
								      "<td>"+f.goingAirport.name+"</td>" +
								      "<td><button type='button' id='"+f.id+"' class='btn btn-success takeTicketFlight' disabled><i class='fas fa-check'></i> Check</button></td>" +
								    "</tr>");
		});
		
	}else{
		$.get('FlightServlet', {}, function(data){
			for(i in data.flights){
				var f = data.flights[i];
				$('#tbody_1').append("<tr>" +
								      "<td>"+f.number+"</td>" +
								      "<td>"+f.dateGoing+"</td>" +
								      "<td>"+f.goingAirport.name+"</td>" +
								      "<td><button type='button' id='"+f.id+"' class='btn btn-success takeTicketFlight'><i class='fas fa-check'></i> Check</button></td>" +
								    "</tr>");
			}	
		});
		
		// click to pick up going flight
		$(document).on('click',".takeTicketFlight", function(event){
			var oo = $(this).attr('id');
			startFlight = oo;
			// load seat for going flight
			$.get('FlightServlet', {'id':oo }, function(data){
				var f = data.flight;
				for(var i=1;i<f.seatNumber+1;i++){
					$('#seatOfGoingFlight').append("<option value='"+i+"'>"+i+"</option>");
				}
				
				$('#priceForOneTicket').text(f.priceTicket + '$'); // price ticket for going flight 
		    });
			
			$('#sale_3').fadeIn();
			
			$.get('FlightServlet', {}, function(data){
				for(i in data.flights){
					var f = data.flights[i];
					$('#tbody_3').append("<tr>" +
									      "<td>"+f.number+"</td>" +
									      "<td>"+f.dateGoing+"</td>" +
									      "<td>"+f.goingAirport.name+"</td>" +
									      "<td><button type='button' id='"+f.id+"' class='btn btn-success takeAirportComing'><i class='fas fa-check'></i> Check</button></td>" +
									    "</tr>");
				}
			});
			
			$('.takeTicketFlight').prop('disabled', true);
			
			event.preventDefault();
			return false;
		});
		
		// click to pick up coming flight
		$(document).on('click',".takeAirportComing", function(event){
			var ooo = $(this).attr('id');
			endFlight = ooo;
			// load seat for coming flight
			$.get('FlightServlet', {'id':ooo }, function(data){
				var f = data.flight;
				for(var i=1;i<f.seatNumber+1;i++){
					$('#seatOfComingFlight').append("<option value='"+i+"'>"+i+"</option>");
				}
		    });
			
			$('.takeAirportComing').prop('disabled', true);
				
			$('#sale_4').fadeIn();
			$('#sale_5').fadeIn();
			
			event.preventDefault();
			return false;
		});
		
		// click to reservation ticket
		$(document).on('click',"#reservationnSubmit", function(event){
			
			var firstName = $("#firstNamePass").val();
			var namePass = $("#lastNamePass").val();
			
			var seatGooo = $('#seatOfGoingFlight').val();
			var seatCoo = $('#seatOfComingFlight').val();
			
			if(firstName == "" || namePass == ""){
				console.log(firstName);
				message.text("You need to fill in all fields!");
				$('.message').fadeIn();
			}else{
				json = {
						'status': 'add',
						'goFlight': startFlight,
						'coFlight': endFlight,
						'seatGoing': seatGooo,
						'seatComing': seatCoo,
						'loggedUser': loggedUU,
						'firstNameP': firstName,
						'lastNameP': namePass,
						'type': 'reservation'
				}
				$.post('TicketServlet', json, function(data){
					window.location.replace("index.html");
				});
			}
			
			event.preventDefault();
			return false;
		});
		
		// click to buy a ticket
		$(document).on('click',"#saleTicketSubmit", function(event){
			
			var firstName = $("#firstNamePass").val();
			var namePass = $("#lastNamePass").val();
			
			var seatGooo = $('#seatOfGoingFlight').val();
			var seatCoo = $('#seatOfComingFlight').val();
			
			if(firstName == "" || namePass == ""){
				console.log(firstName);
				message.text("You need to fill in all fields!");
				$('.message').fadeIn();
			}else{
				json = {
						'status': 'add',
						'goFlight': startFlight,
						'coFlight': endFlight,
						'seatGoing': seatGooo,
						'seatComing': seatCoo,
						'loggedUser': loggedUU,
						'firstNameP': firstName,
						'lastNameP': namePass,
						'type': 'sale'
				}
				$.post('TicketServlet', json, function(data){
					window.location.replace("index.html");
				});
			}
			
			event.preventDefault();
			return false;
		});
		
		
	}
	

	
});