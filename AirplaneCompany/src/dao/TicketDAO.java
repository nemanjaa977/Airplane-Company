package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import model.Flight;
import model.ReservationTicket;
import model.User;

public class TicketDAO {
	
	public static ReservationTicket getOne(int id) {
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM tickets WHERE id = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 2;	
				Integer flightGoing = rset.getInt(index++);
				Flight fg = FlightDAO.getOneForDeletedFlight(flightGoing);
				Integer flightComing = rset.getInt(index++);
				Flight fc = FlightDAO.getOneForDeletedFlight(flightComing);
				Integer seatOfGoingF = rset.getInt(index++);
				Integer seatOfComingF = rset.getInt(index++);
				
				Date dateReservation = rset.getDate(index++);
				String dc = null;
				if(dateReservation != null) {
					dc = DateConverter.dateToString(dateReservation);
				}
								
				Date dateSale = rset.getDate(index++);
				String ds = null;
				if(dateSale != null) {
					ds = DateConverter.dateToString(dateSale);
				}
				
				Integer userID = rset.getInt(index++);
				User u = UserDAO.getOneId(userID);
				String firstnameP = rset.getString(index++);
				String lastNameP = rset.getString(index++);
			
				return new ReservationTicket(id, fg, fc, seatOfGoingF, seatOfComingF, dc, ds, u, firstnameP, lastNameP);
			}
			
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return null;
	}
	
	public static ArrayList<ReservationTicket> getAll() {
		
		Connection conn = ConnectionManager.getConnection();
		Statement pstmt = null;
		ResultSet rset = null;
		ArrayList<ReservationTicket> tickets = new ArrayList<ReservationTicket>();
		try {
			String query = "SELECT * FROM tickets";

			pstmt = conn.createStatement();	
			rset = pstmt.executeQuery(query);

			 while(rset.next()) {
				int index = 1;
				Integer id = rset.getInt(index++);
				Integer flightGoing = rset.getInt(index++);
				Flight fg = FlightDAO.getOne(flightGoing);
				Integer flightComing = rset.getInt(index++);
				Flight fc = FlightDAO.getOne(flightComing);
				Integer seatOfGoingF = rset.getInt(index++);
				Integer seatOfComingF = rset.getInt(index++);
				
				Date dateReservation = rset.getDate(index++);
				String dc = null;
				if(dateReservation != null) {
					dc = DateConverter.dateToString(dateReservation);
				}
								
				Date dateSale = rset.getDate(index++);
				String ds = null;
				if(dateSale != null) {
					ds = DateConverter.dateToString(dateSale);
				}
						
				Integer userID = rset.getInt(index++);
				User u = UserDAO.getOneId(userID);
				String firstnameP = rset.getString(index++);
				String lastNameP = rset.getString(index++);
				
				tickets.add(new ReservationTicket(id, fg, fc, seatOfGoingF, seatOfComingF, dc, ds, u, firstnameP, lastNameP));
				
			}
			 return tickets;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return null;
	}
	
	public static int getTicketId() {
		Connection conn = ConnectionManager.getConnection();
		int id=0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT MAX(id) FROM tickets";
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
		
			if (rset.next()) {
				id=rset.getInt(1);
				
			}
			id++;
			return id;
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
			try {
				rset.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return 0;
	}
	
	public static boolean create(ReservationTicket ticket) {
		Connection conn = ConnectionManager.getConnection();	
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO tickets (goingFlight, reverseFlight, seatOnGoingFlight, seatOnReverseFlight, dateReservation, dateOfSaleTicket,"
					+ "userCreateReservationOrSaleTicket, firstNamePassenger, lastNamePassenger)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";	
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setInt(index++, ticket.getGoingFlight().getId());
			pstmt.setInt(index++, ticket.getReverseFlight().getId());
			pstmt.setInt(index++, ticket.getSeatOnGoingFlight());
			pstmt.setInt(index++, ticket.getSeatOnReverseFlight());
			Date myDate;
			java.sql.Date date;
			if(ticket.getDateReservation() !=null) {
				myDate= DateConverter.stringToDateForWrite(ticket.getDateReservation());
				date = new java.sql.Date(myDate.getTime());
			}else {
				date=null;
			}
			pstmt.setDate(index++, date);
			Date myDate2;
			java.sql.Date date2;
			if(ticket.getDateOfSaleTicket() != null) {
				myDate2 = DateConverter.stringToDateForWrite(ticket.getDateOfSaleTicket()); 
				date2 = new java.sql.Date(myDate2.getTime());
			}else {
				date2 = null;
			}		
			pstmt.setDate(index++, date2);
			pstmt.setInt(index++, ticket.getUserCreateReservationOrSaleTicket().getId());
			pstmt.setString(index++, ticket.getFirstNamePassenger());
			pstmt.setString(index++, ticket.getLastNamePassenger());
			
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return false;
	}
	
	public static boolean update(ReservationTicket ticket) {
		Connection conn = ConnectionManager.getConnection();	
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE tickets SET goingFlight = ?, reverseFlight = ?, seatOnGoingFlight = ?, seatOnReverseFlight = ?, dateReservation = ?, dateOfSaleTicket = ?,"
					+ "userCreateReservationOrSaleTicket = ?, firstNamePassenger = ?, lastNamePassenger = ? WHERE id = ?";	
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setInt(index++, ticket.getGoingFlight().getId());
			pstmt.setInt(index++, ticket.getReverseFlight().getId());
			pstmt.setInt(index++, ticket.getSeatOnGoingFlight());
			pstmt.setInt(index++, ticket.getSeatOnReverseFlight());
			Date myDate;
			java.sql.Date date;
			if(ticket.getDateReservation() !=null) {
				myDate= DateConverter.stringToDateForWrite(ticket.getDateReservation());
				date = new java.sql.Date(myDate.getTime());
			}else {
				date=null;
			}
			pstmt.setDate(index++, date);
			Date myDate2;
			java.sql.Date date2;
			if(ticket.getDateOfSaleTicket() != null) {
				myDate2 = DateConverter.stringToDateForWrite(ticket.getDateOfSaleTicket()); 
				date2 = new java.sql.Date(myDate2.getTime());
			}else {
				date2 = null;
			}		
			pstmt.setDate(index++, date2);
			pstmt.setInt(index++, ticket.getUserCreateReservationOrSaleTicket().getId());
			pstmt.setString(index++, ticket.getFirstNamePassenger());
			pstmt.setString(index++, ticket.getLastNamePassenger());
			pstmt.setInt(index++, ticket.getId());
			
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return false;
	}
	
	public static void delete(int id) {
		Connection conn = ConnectionManager.getConnection();	
		PreparedStatement pstmt = null;

		try {
			String query = "DELETE FROM tickets WHERE id = ?";	
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

	}
	
	public static ArrayList<ReservationTicket> getAllForOneFlightForDelete(int flightId) {
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<ReservationTicket> tickets = new ArrayList<ReservationTicket>();
		try {
			String query = "SELECT * FROM  tickets t JOIN flights f ON t.goingFlight = f.goingAirport WHERE f.id = ? AND NOT ISNULL(t.dateReservation);";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, flightId);
			rset = pstmt.executeQuery();

			 while(rset.next()) {
				int index = 1;
				Integer id = rset.getInt(index++);
				Integer flightGoing = rset.getInt(index++);
				Flight fg = FlightDAO.getOne(flightGoing);
				Integer flightComing = rset.getInt(index++);
				Flight fc = FlightDAO.getOne(flightComing);
				Integer seatOfGoingF = rset.getInt(index++);
				Integer seatOfComingF = rset.getInt(index++);
				
				Date dateReservation = rset.getDate(index++);
				String dc = null;
				if(dateReservation != null) {
					dc = DateConverter.dateToString(dateReservation);
				}
								
				Date dateSale = rset.getDate(index++);
				String ds = null;
				if(dateSale != null) {
					ds = DateConverter.dateToString(dateSale);
				}
						
				Integer userID = rset.getInt(index++);
				User u = UserDAO.getOneId(userID);
				String firstnameP = rset.getString(index++);
				String lastNameP = rset.getString(index++);
				
				tickets.add(new ReservationTicket(id, fg, fc, seatOfGoingF, seatOfComingF, dc, ds, u, firstnameP, lastNameP));
				
			}
			 return tickets;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return null;
	}

}
