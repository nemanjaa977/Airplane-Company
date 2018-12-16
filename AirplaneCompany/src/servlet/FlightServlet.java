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

import dao.AirportDAO;
import dao.FlightDAO;
import dao.UserDAO;
import model.Airport;
import model.Flight;
import model.User;
import model.User.Role;

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

		String status = request.getParameter("status");
		
		if(status.equals("add")) {
			
			String fNumber = request.getParameter("number");
		    String dg = request.getParameter("dateG");
		    String dc = request.getParameter("dateC");
		    
		    String aGO = request.getParameter("goingA");
		    int ag = Integer.parseInt(aGO);
		    Airport a1 = AirportDAO.getOne(ag); 
		    String aCO = request.getParameter("comingA");
		    int ac = Integer.parseInt(aCO);
		    Airport a2 = AirportDAO.getOne(ac);
		    
		    String seat = request.getParameter("seatNumber");
		    int seatNumer = Integer.parseInt(seat);
		    
		    String ticket = request.getParameter("priceTicket");
			double priceT = Double.parseDouble(ticket);
			int id = FlightDAO.getFlightId();
			
			Flight f = new Flight(id, fNumber, dg, dc, a1, a2, seatNumer, priceT, false);
			FlightDAO.create(f);
			
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
