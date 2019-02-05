$(document).ready(function() {
	
	var userNameInput = $('#inputUsername');
	var passwordInput = $('#inputPassword');
	var message = $('#provera');
	
	$('#registerSubmit').on('click', function(event) { 
		var userName = userNameInput.val();
		var password = passwordInput.val();
		
		if(userName == "" || password == ""){
			message.fadeIn();
			message.text("You need to fill in all fields!");
		}else{
			$.post('RegisterServlet', {'username': userName, 'password': password}, function(data) {
				if (data.status == 'success') {
					window.location.replace('index.html');
				}
				if (data.status == 'failure') {
					message.fadeIn();
					message.text("Selected incorrect data!");
				}
			});
		}

		event.preventDefault();
		return false;
	});
});