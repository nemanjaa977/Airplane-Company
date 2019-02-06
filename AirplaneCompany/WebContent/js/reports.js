$(document).ready(function() {
	
	var nav = $('#nav');
	var navUser = $('#navLogged');
	
	$.get('UserServlet', {}, function(data){
		console.log(data);
		if(data.logged != null){
			nav.append("<li class='nav-item'>" +
							"<a class='nav-link' href='#'>Reservation/Sale ticket</a>" +
					   "</li>" +
					   "<li class='nav-item dropdown'>" +
					   		"<a class='nav-link dropdown-toggle' href='addTicket.html' id='navbarDropdown' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'> Profile</a>" +
				   			"<div class='dropdown-menu' aria-labelledby='navbarDropdown'>" +
					   			"<a class='dropdown-item' href='user.html?id="+data.logged.id+"'>My Profile</a>" +
					   			"<a class='dropdown-item' href='users.html' id='manageUsers'>Manage Users</a>" +
					   			"<a class='dropdown-item' href='reports.html' id='reports'>Reports</a>" +
					   			"<div class='dropdown-divider'></div>" +
				   				"<a class='dropdown-item' href='LogOutServlet'>Logout</a>" +
				   			"</div>" +
				   	   "</li>");
			
		}
		
	});
	
	$(document).on('click',"#clickDateOk", function(event){
		var firstDate = $('#firstDate').val();
		var secondDate = $('#secondDate').val();
		
		var param = {
				'firstDate': firstDate,
				'secondDate': secondDate
		}
		
		if(firstDate == "" || secondDate == ""){
			alert("You must select dates");
		}else{
			$.get('ReportServlet', param, function(data){
				for(i in data.reports){
					var f = data.reports[i];
					$('#tbody').append("<tr>" +
									      "<td class='table-light'>"+f.airportName+"</td>" +
									      "<td class='table-light'>"+f.flightCount+"</td>" +
									      "<td class='table-light'>"+f.ticketsCount+"</td>" +
									    "</tr>");
				}
			});
		}
		
		
		event.preventDefault();
		return false;
	});
	
});