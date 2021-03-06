$(document).ready(function() {
	
	var nav = $('#nav');
	var navUser = $('#navLogged');
	
	$.get('UserServlet', {}, function(data){
		console.log(data);
		if(data.logged != null){
			nav.append("<li class='nav-item'>" +
							"<a class='nav-link' href='addTicket.html'>Reservation/Sale ticket</a>" +
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
		
		for(i in data.users){
			var u = data.users[i];
			$('#tbody').append("<tr class='table-light'>" +
							      "<td><a href='user.html?id="+u.id+"' class='numberID'>"+u.username+"</a></td>" +
							      "<td>"+u.dateRegistration+"</td>" +
							      "<td>"+u.role+"</td>" +
							    "</tr>");
		}
		
	});
	
	$(document).on('click',"#searchOK", function(event){
		var inputText = $('#inputSearch').val();
		
		$.get("SearchUserServlet",{"inputText": inputText},function(data){
			$('#tbody').empty();
			for(i in data.users){
				var u = data.users[i];
				$('#tbody').append("<tr class='table-light'>" +
								      "<td><a href='user.html?id="+u.id+"' class='numberID'>"+u.username+"</a></td>" +
								      "<td>"+u.dateRegistration+"</td>" +
								      "<td>"+u.role+"</td>" +
								    "</tr>");
			}
		});
		
		event.preventDefault();
		return false;
	});
	
});