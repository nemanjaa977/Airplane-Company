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
import dao.FlightDAO;
import dao.TicketDAO;
import dao.UserDAO;
import model.Flight;
import model.ReservationTicket;
import model.User;

public class TicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
			
		}
		
	}

}
