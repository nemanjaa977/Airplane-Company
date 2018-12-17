package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
				Flight fg = FlightDAO.getOne(flightGoing);
				Integer flightComing = rset.getInt(index++);
				Flight fc = FlightDAO.getOne(flightComing);
				Integer seatOfGoingF = rset.getInt(index++);
				Integer seatOfComingF = rset.getInt(index++);
				Date dateReservation = rset.getDate(index++);
				String dc = DateConverter.dateToString(dateReservation);
				Date dateSale = rset.getDate(index++);
				String ds = DateConverter.dateToString(dateSale);
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
			Date myDate = DateConverter.stringToDateForWrite(ticket.getDateReservation());
			java.sql.Date date = new java.sql.Date(myDate.getTime());
			pstmt.setDate(index++, date);
			Date myDate2 = DateConverter.stringToDateForWrite(ticket.getDateOfSaleTicket());
			java.sql.Date date2 = new java.sql.Date(myDate2.getTime());
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

}
