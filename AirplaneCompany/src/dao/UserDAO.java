package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import model.User;
import model.User.Role;

public class UserDAO {
	
	public static User getOne(String username) {
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM users WHERE username = ? AND deleted = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setBoolean(2, false);	
			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 1;
				Integer id = rset.getInt(index++);
				if(index == 2)
					index++;
				String password = rset.getString(index++);
				Date date = rset.getDate(index++);
				String dateRegistration = DateConverter.dateToString(date);
				Role role = Role.valueOf(rset.getString(index++));
				boolean blocked = rset.getBoolean(index++);
				boolean deleted = rset.getBoolean(index++);
			
				return new User(id, username, password, dateRegistration, role, blocked, deleted);
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
	
	public static User getOneId(int id) {
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM users WHERE id = ? AND deleted = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setBoolean(2, false);	
			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 2;
				String username = rset.getString(index++);
				String password = rset.getString(index++);
				Date date = rset.getDate(index++);
				String dateRegistration = DateConverter.dateToString(date);
				Role role = Role.valueOf(rset.getString(index++));
				boolean blocked = rset.getBoolean(index++);
				boolean deleted = rset.getBoolean(index++);
			
				return new User(id, username, password, dateRegistration, role, blocked, deleted);
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
	
	public static ArrayList<User> getAll() {
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<User> users = new ArrayList<User>();
		try {
			String query = "SELECT * FROM users WHERE deleted = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, false);
			
			rset = pstmt.executeQuery();

			 while(rset.next()) {
				int index = 1;
				Integer id = rset.getInt(index++);
				String username = rset.getString(index++);
				String password = rset.getString(index++);
				Date date = rset.getDate(index++);
				String dateRegistration = DateConverter.dateToString(date);
				Role role = Role.valueOf(rset.getString(index++));
				boolean blocked = rset.getBoolean(index++);
				boolean deleted = rset.getBoolean(index++);
				
				users.add(new User(id, username, password, dateRegistration, role, blocked, deleted));
				
			}
			 return users;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return null;
	}
	
	public static boolean create(User user) {
		Connection conn = ConnectionManager.getConnection();	
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO users (username, password, dateRegistration, role, blocked, deleted)"
					+ " VALUES (?, ?, ?, ?, ?, ?)";	
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, user.getUsername());
			pstmt.setString(index++, user.getPassword());
			Date myDate = DateConverter.stringToDateForWrite(user.getDateRegistration());
			java.sql.Date date = new java.sql.Date(myDate.getTime());
			pstmt.setDate(index++, date);
			pstmt.setString(index++, user.getRole().toString());
			pstmt.setBoolean(index++, user.isBlocked());
			pstmt.setBoolean(index++, user.isDeleted());
			
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return false;
	}
	
	public static boolean update(User user) {
		Connection conn = ConnectionManager.getConnection();	
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE users SET username = ?, password = ?, role = ?, blocked = ?, deleted = ? WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, user.getUsername());
			pstmt.setString(index++, user.getPassword());
			pstmt.setString(index++, user.getRole().toString());
			pstmt.setBoolean(index++, user.isBlocked());
			pstmt.setBoolean(index++, user.isDeleted());
			pstmt.setInt(index++, user.getId());
			
			
			return pstmt.executeUpdate() == 1;	
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return false;
	}
	
	public static int getUserId() {
		Connection conn = ConnectionManager.getConnection();
		int id=0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT MAX(id) FROM users";
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
