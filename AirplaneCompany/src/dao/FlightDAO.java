package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import model.Airport;
import model.Flight;
import model.User;
import model.User.Role;

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

}
