package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DateConverter;
import dao.FlightDAO;
import dao.TicketDAO;
import dao.UserDAO;
import model.Flight;
import model.ReservationTicket;
import model.User;

public class TicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User logged = null;
		ArrayList<ReservationTicket> tickets = null;
		ReservationTicket ticket = null;
		
		try {
			HttpSession session = request.getSession();
			logged = (User) session.getAttribute("loggedUser");
			
			String text = request.getParameter("id");
			if(text == null) {
				tickets = TicketDAO.getAll();
			}else {
				int id = Integer.parseInt(text);
				ticket = TicketDAO.getOne(id);
			}
			

		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("logged", logged);
		data.put("tickets", tickets);
		data.put("ticket", ticket);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		System.out.println(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String status = request.getParameter("status");
		
		if(status.equals("add")) {
			
			int idd = TicketDAO.getTicketId();
			
			String firstNameP = request.getParameter("firstNameP");
			String lastNameP = request.getParameter("lastNameP");
			
			String flightGo = request.getParameter("goFlight");
			int fg = Integer.parseInt(flightGo);
			Flight f = FlightDAO.getOne(fg);
			String flightCo = request.getParameter("coFlight");
			int fc = Integer.parseInt(flightCo);
			Flight f2 = FlightDAO.getOne(fc);
			
			String userRR = request.getParameter("loggedUser");
			int user = Integer.parseInt(userRR);
			User u = UserDAO.getOneId(user);
			
			String typeCard = request.getParameter("type");
			
			int seatGGGG = Integer.parseInt(request.getParameter("seatGoing"));
			int seatCCCC = Integer.parseInt(request.getParameter("seatComing"));
			
			Date d = new Date();
			String date = DateConverter.dateToStringForWrite(d);
			
			if(typeCard.equals("reservation")) {
				ReservationTicket ticket = new ReservationTicket(idd, f, f2, seatGGGG, seatCCCC, date, null, u, firstNameP, lastNameP);
				TicketDAO.create(ticket);
			}else {
				ReservationTicket ticket = new ReservationTicket(idd, f, f2, seatGGGG, seatCCCC, null, date, u, firstNameP, lastNameP);
				TicketDAO.create(ticket);
			}
			
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", "success");
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			
		}else if(status.equals("buy")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Date d = new Date();
			String date = DateConverter.dateToStringForWrite(d);
			
			ReservationTicket t = TicketDAO.getOne(id);
			t.setDateOfSaleTicket(date);
			t.setDateReservation(null);
			
			TicketDAO.update(t);
			
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
