$(document).ready(function() {
	
	var nav = $('#nav');
	var navUser = $('#navLogged');
	var message = $('#provera');
	
	$.get('UserServlet', {}, function(data){
		console.log('Logged: ' + data.logged);
		if(data.logged != null){
			nav.append("<li class='nav-item'>" +
							"<a class='nav-link' href='addTicket.html'>Reservation/Sale ticket</a>" +
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
	
	$.get('AirportServlet', {}, function(data){
		for(i in data.airports){
			$('#goingAirport').append("<option value='"+data.airports[i].id+"'>"+data.airports[i].name+"</option>");
			$('#comingAirport').append("<option value='"+data.airports[i].id+"'>"+data.airports[i].name+"</option>");
		}
	});
	
	//open add flight form
	$(document).on('click',"#addFlight", function(event){
		
		$('#addFlightForm').fadeIn();
		
		event.preventDefault();
		return false;
	});
	
	//click submit form for add flight
	$(document).on('click',"#addSubmit", function(event){
		
		var numberF = $('#inputFlightNumber').val();
		var dateG = $('#dateGoing').val();
		var dateC = $('#dateComing').val();
		var goingA = $('#goingAirport').val();
		var comingA = $('#comingAirport').val();
		var seatNumber = $('#inputSeatNumber').val();
		var priceTicket = $('#inputPriceTicket').val();
	
		if(numberF == "" || seatNumber == "" || priceTicket == ""){
			message.text("You need to fill in all fields!");
		}else{
			
			json = {
					'status': 'add',
					'number': numberF,
					'dateG': dateG,
					'dateC': dateC,
					'goingA': goingA,
					'comingA': comingA,
					'seatNumber': seatNumber,
					'priceTicket': priceTicket
			}
			var date = new Date();
			var d = new Date(dateG);
			if(dateC > dateG && d > date){
				$.post('FlightServlet', json, function(data) {
					if (data.status == 'success') {
						window.location.replace('index.html');
					}
				});
			}else{
				message.text("Date of going can't bigger than date of coming!");
			}
			
		}

		event.preventDefault();
		return false;
	});
	
	// close add flight form
	$(document).on('click',"#closeAdd", function(event){
		
		$('#addFlightForm').fadeOut();
		
		$('#inputFlightNumber').val("");
		$('#dateGoing').val("");
		$('#dateComing').val("");
		$('#inputSeatNumber').val("");
		$('#inputPriceTicket').val("");
		message.text("");
		
		event.preventDefault();
		return false;
	});
	
});