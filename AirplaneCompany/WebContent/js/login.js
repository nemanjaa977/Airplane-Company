$(document).ready(function() {
	
	var userNameInput = $('#inputUsername');
	var passwordInput = $('#inputPassword');
	var message = $('#provera');
	
	$('#submitLogin').on('click', function(event) { 
		var userName = userNameInput.val();
		var password = passwordInput.val();
		
		if(userName == "" || password == "")
			message.text("You need to fill in all fields!");
		
		$.post('LoginServlet', {'username': userName, 'password': password}, function(data) {
			console.log(data);
			if (data.status == 'success') {
				window.location.replace('index.html');
			}
			if (data.status == 'failure') {
				message.text("Selected incorrect data!");
			}
		});

		event.preventDefault();
		return false;
	});
});