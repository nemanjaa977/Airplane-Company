package servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DateConverter;
import dao.UserDAO;
import model.User;
import model.User.Role;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String status = "success";
		String newUsername = request.getParameter("username");
		String newPassword = request.getParameter("password");
		
		try {
			User existingUser = UserDAO.getOne(newUsername);
			if(existingUser != null) {
				throw new Exception("User already exist!");
			}
			int id = UserDAO.getUserId();
			Date d = new Date();
			String date = DateConverter.dateToStringForWrite(d);
			
			User user = new User(id, newUsername, newPassword, date, Role.REGISTERED_USER, false, false);
			UserDAO.create(user);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("status", status);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
