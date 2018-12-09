$(document).ready(function() {
	
	var nav = $('#nav');
	var navUser = $('#navLogged');
	var userID = window.location.search.slice(1).split('&')[0].split('=')[1];
	var admin = $('#adminId');
	var user = $('#userId');
	var username = $('#inputUsername');
	var password = $('#inputPassword');
	var p = null;
	
	$.get('UserServlet', {'id': userID}, function(data){
		console.log('Logged: ' + data.logged.role);
		
		$('#mainn').append("<a href='user.html?id="+data.user.id+"' id='userUsername'>"+data.user.username+"</a>" +
				   "<p id='userRole'>Role: "+data.user.role+"</p>" +
				   "<p id='userDate'>Date registration: "+data.user.dateRegistration+"</p>" +
				   "<div class='row'>" +
				   		"<div class='col-12' id='divB'>" +
					   		"<button type='button' class='btn btn-primary editButton' id='"+userID+"'><i class='far fa-edit'></i> Edit</button>" +
				   		"</div>" +
				   "</div>");
		
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
				document.getElementById('manageUsers').style.display='block';
				$('#divB').append("<button type='button' class='btn btn-dark blockButton' id='"+userID+"'><i class='fas fa-ban'></i> Block</button>" +
								  "<button type='button' class='btn btn-dark unblockButton' id='"+userID+"'><i class='fas fa-ban'></i> Unblock</button>" +
			   					  "<button type='button' class='btn btn-danger deleteButton' id='"+userID+"'><i class='fas fa-trash'></i> Delete</button>");
			}
			
			if(data.user.id == userID){
				console.log(data.user.id);
				if(data.user.blocked == true){
					$('.blockButton').hide();
				}else{
					$('.unblockButton').hide();
				}
			}
			
			//click open edit div
			$(document).on('click',".editButton", function(event){
				$('#editDiv').fadeIn();
				var userid = $(this).attr('id');
				$.get('UserServlet',{'id':userid},function(data){
					
					if(data.user.role != 'ADMIN'){
						$('#radioDiv').hide();
					}
					
					username.val(data.user.username);
					password.val(data.user.password);
					p = data.user.password;
					
					if(data.user.role == "ADMIN"){
						admin.prop('checked',true);
					}else{
						user.prop('checked',true);
					}
				});
				
				event.preventDefault();
				return false;
			});
			
			// edit submit button
			$(document).on('click',"#editSubmit", function(event){
				var newUsername = username.val();
				
				var pass = null;
				var newPassword = password.val();
				if(newPassword == ""){
					pass = p;
				}else{
					pass = newPassword;
				}
				
				var newRole = "REGISTERED_USER";
				var newId = userID;
				if(admin.is(':checked')){
					newRole = "ADMIN";
				}
				
				var json = {
						'status': 'edit',
						'username': newUsername,
						'password': pass,
						'role': newRole,
						'id': newId
				}
				
				$.post('UserServlet',json,function(data){
					if(data.status == "success"){
						var location="user.html?id="+userID;
						window.location.replace(location);
					}
				});
					
				event.preventDefault();
				return false;
			});
			
			$(document).on('click',"#closeEditDiv", function(event){
				$('#editDiv').fadeOut();
				
				event.preventDefault();
				return false;
			});
			
			//delete
			$(document).on('click',".deleteButton", function(event){
				var userId = $(this).attr('id');
				
				var json = {
						'status': 'delete',
						'id': userId
				}
				
				$.post('UserServlet',json,function(data){
					window.location.replace("users.html");
				});
				
				event.preventDefault();
				return false;
			});
			
			//block
			$(document).on('click',".blockButton", function(event){
				var userId = $(this).attr('id');
				
				var json = {
						'status': 'block',
						'id': userId
				}
				
				$.post('UserServlet',json,function(data){
					window.location.replace("users.html");
				});
				
				event.preventDefault();
				return false;
			});
			
			//unblock
			$(document).on('click',".unblockButton", function(event){
				var userId = $(this).attr('id');
				
				var json = {
						'status': 'unblock',
						'id': userId
				}
				
				$.post('UserServlet',json,function(data){
					window.location.replace("users.html");
				});
				
				event.preventDefault();
				return false;
			});
			
		}else{
			navUser.append("<li class='nav-item'>" +
								"<a class='nav-link' href='login.html'>Login</a>" +
						   "</li>" +
						   "<li class='nav-item'>" +
						   		"<a class='nav-link' href='register.html'>Register</a>" +
						   "</li>");
		}
		
		
	});
	
});