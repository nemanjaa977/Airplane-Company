package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import model.Airport;
import model.Flight;

public class FlightDAO {
	
	public static Flight getOne(int id) {
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM flights WHERE id = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 2;	
				String number = rset.getString(index++);
				Date date = rset.getDate(index++);
				String dateGoing = DateConverter.dateToString(date);
				Date date2 = rset.getDate(index++);
				String dateComing = DateConverter.dateToString(date2);
				Integer airportGoing = rset.getInt(index++);
				Airport ag = AirportDAO.getOne(airportGoing);
				Integer airportComing = rset.getInt(index++);
				Airport ac = AirportDAO.getOne(airportComing);
				Integer seatNumber = rset.getInt(index++);
				Double priceTicket = rset.getDouble(index++);
				boolean deleted = rset.getBoolean(index++);
			
				return new Flight(id, number, dateGoing, dateComing, ag, ac, seatNumber, priceTicket, deleted);
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
	
	public static ArrayList<Flight> getAll() {
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Flight> flights = new ArrayList<Flight>();
		try {
			String query = "SELECT * FROM flights";

			pstmt = conn.prepareStatement(query);	
			rset = pstmt.executeQuery();

			 while(rset.next()) {
				int index = 1;
				Integer id = rset.getInt(index++);
				String number = rset.getString(index++);
				Date date = rset.getDate(index++);
				String dateGoing = DateConverter.dateToString(date);
				Date date2 = rset.getDate(index++);
				String dateComing = DateConverter.dateToString(date2);
				Integer airportGoing = rset.getInt(index++);
				Airport ag = AirportDAO.getOne(airportGoing);
				Integer airportComing = rset.getInt(index++);
				Airport ac = AirportDAO.getOne(airportComing);
				Integer seatNumber = rset.getInt(index++);
				Double priceTicket = rset.getDouble(index++);
				boolean deleted = rset.getBoolean(index++);
				
				flights.add(new Flight(id, number, dateGoing, dateComing, ag, ac, seatNumber, priceTicket, deleted));
				
			}
			 return flights;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return null;
	}

	public static ArrayList<Flight> getSearchAll(String inputText) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ArrayList<Flight> flights = new ArrayList<Flight>();
		ResultSet rset = null;
		try {
			String query = "SELECT DISTINCT id, numberF, dateGoing, dateComing, goingAirport, comingAirport, seatNumber, priceTicket FROM flights "
					+ " WHERE numberF LIKE '%" +inputText+ "%' OR dateGoing LIKE '%" +inputText+ "%' OR dateComing LIKE '%" +inputText+ "%' OR goingAirport LIKE '%" +inputText+ "%'"
					+ " OR comingAirport LIKE '%" +inputText+ "%' OR priceTicket LIKE '%" +inputText+ "%' ";
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int index = 1;
				
				int id=rset.getInt(index++);
				String number = rset.getString(index++);
				Date date = rset.getDate(index++);
				String dateGoing = DateConverter.dateToString(date);
				Date date2 = rset.getDate(index++);
				String dateComing = DateConverter.dateToString(date2);
				Integer airportGoing = rset.getInt(index++);
				Airport ag = AirportDAO.getOne(airportGoing);
				Integer airportComing = rset.getInt(index++);
				Airport ac = AirportDAO.getOne(airportComing);
				Integer seatNumber = rset.getInt(index++);
				Double priceTicket = rset.getDouble(index++);
				boolean deleted = rset.getBoolean(index++);
				
				flights.add( new Flight(id, number, dateGoing, dateComing, ag, ac, seatNumber, priceTicket, deleted));
			}
	
			return flights;
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return null;
	}
	
	public static boolean create(Flight flight) {
		Connection conn = ConnectionManager.getConnection();	
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO flights (numberF, dateGoing, dateComing, goingAirport, comingAirport, seatNumber, priceTicket, deleted)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";	
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, flight.getNumber());
			Date myDate = DateConverter.stringToDateForWrite(flight.getDateGoing());
			java.sql.Date date = new java.sql.Date(myDate.getTime());
			pstmt.setDate(index++, date);
			Date myDate2 = DateConverter.stringToDateForWrite(flight.getDateComing());
			java.sql.Date date2 = new java.sql.Date(myDate2.getTime());
			pstmt.setDate(index++, date2);
			pstmt.setInt(index++, flight.getGoingAirport().getId());
			pstmt.setInt(index++, flight.getComingAirport().getId());
			pstmt.setInt(index++, flight.getSeatNumber());
			pstmt.setDouble(index++, flight.getPriceTicket());
			pstmt.setBoolean(index++, flight.isDeleted());
			
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return false;
	}
	
	public static int getFlightId() {
		Connection conn = ConnectionManager.getConnection();
		int id=0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT MAX(id) FROM flights";
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

}
