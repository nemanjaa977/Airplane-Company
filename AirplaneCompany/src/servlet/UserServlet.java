package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.FlightDAO;
import dao.UserDAO;
import model.User;
import model.User.Role;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User logged = null;
		ArrayList<User> users = null;
		User user = null;
		
		try {
			HttpSession session = request.getSession();
			logged = (User) session.getAttribute("loggedUser");
			
			String username = request.getParameter("id");
			if(username == null) {
				users = UserDAO.getAll();
			}else {
				int id = Integer.parseInt(username);
				user = UserDAO.getOneId(id);
			}
			

		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("logged", logged);
		data.put("users", users);
		data.put("user", user);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		System.out.println(jsonData);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String status = request.getParameter("status");
		
		if(status.equals("edit")) {
			
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String role = request.getParameter("role");
			int id = Integer.parseInt(request.getParameter("id"));
			
			Role newRole = Role.valueOf(role);
			
			User user = UserDAO.getOneId(id);
			
			user.setUsername(username);
			user.setPassword(password);
			user.setRole(newRole);
			
			UserDAO.update(user);
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", "success");
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			
		}else if(status.equals("delete")) {
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			User u = UserDAO.getOneId(id);
			u.setDeleted(true);
			
			UserDAO.update(u);
			
			Map<String, Object> data = new HashMap<>();		
			data.put("status", "success");
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		}
		
		
	}

}
