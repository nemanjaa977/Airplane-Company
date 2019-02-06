package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import model.Airport;
import model.ReportModel;

public class AirportDAO {
	
	public static Airport getOne(int id) {
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM airports WHERE id = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 2;
				String name = rset.getString(index++);
			
				return new Airport(id, name);
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
	
	public static ArrayList<Airport> getAll() {
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Airport> airports = new ArrayList<Airport>();
		try {
			String query = "SELECT * FROM airports";

			pstmt = conn.prepareStatement(query);	
			rset = pstmt.executeQuery();

			 while(rset.next()) {
				int index = 1;
				Integer id = rset.getInt(index++);
				String name = rset.getString(index++);
				
				airports.add(new Airport(id, name));		
			}
			 return airports;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return null;
	}
	
	public static ArrayList<ReportModel> getAirportsForReports(String firstDate, String secondDate) {
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<ReportModel> airports = new ArrayList<ReportModel>();
		try {
			String query = "SELECT DISTINCT a.name, COUNT(f.goingAirport), COUNT(t.dateOfSaleTicket) " + 
					"FROM airports a " + 
					"JOIN flights f " + 
					"ON a.id = f.goingAirport " + 
					"LEFT JOIN tickets t ON t.goingFlight = f.id " + 
					"WHERE f.dateGoing BETWEEN ? AND ? GROUP BY a.name";

			pstmt = conn.prepareStatement(query);
			Date myDate = DateConverter.stringToDateForWrite(firstDate);
			java.sql.Date date = new java.sql.Date(myDate.getTime());
			pstmt.setDate(1, date);
			Date myDate2 = DateConverter.stringToDateForWrite(secondDate);
			java.sql.Date date2 = new java.sql.Date(myDate2.getTime());
			pstmt.setDate(2, date2);
			rset = pstmt.executeQuery();

			 while(rset.next()) {
				int index = 1;
				String name = rset.getString(index++);
				Integer flightCount = rset.getInt(index++);
				Integer ticketsCount = rset.getInt(index++);
				
				airports.add(new ReportModel(name, flightCount, ticketsCount));		
			}
			 return airports;
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
