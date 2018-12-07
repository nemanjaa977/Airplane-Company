package model;

public class User {
	
	public enum Role {UNREGISTERED_USER, REGISTERED_USER, ADMIN}
	
	private int id;
	private String username;
	private String password;
	private String dateRegistration;
	private Role role;
	private boolean blocked;
	private boolean deleted;
	
	public User() {
		
	}

	public User(int id, String username, String password, String dateRegistration, Role role, boolean blocked,
			boolean deleted) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.dateRegistration = dateRegistration;
		this.role = role;
		this.blocked = blocked;
		this.deleted = deleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDateRegistration() {
		return dateRegistration;
	}

	public void setDateRegistration(String dateRegistration) {
		this.dateRegistration = dateRegistration;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	

}
