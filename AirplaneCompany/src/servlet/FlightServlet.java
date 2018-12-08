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
import model.Flight;
import model.User;

public class FlightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<Flight> flights = null;
		User logged = null;
		Flight flight = null;
		
		try {
			HttpSession session = request.getSession();
			logged = (User) session.getAttribute("loggedUser");
			
			String id = request.getParameter("id");
			if(id == null) {
				flights = FlightDAO.getAll();
			}else {
				int newId = Integer.parseInt(id);
				flight = FlightDAO.getOne(newId);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("flights", flights);
		data.put("logged", logged);
		data.put("flight", flight);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		response.setContentType("application/json");
		
		response.getWriter().write(jsonData);
		System.out.println(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
