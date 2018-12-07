package model;

public class ReservationTicket {
	
	private int id;
	private Flight goingFlight;
	private Flight reverseFlight;
	private int seatOnGoingFlight;
	private int seatOnReverseFlight;
	private String dateReservation;
	private String dateOfSaleTicket;
	private User userCreateReservationOrSaleTicket;
	private String firstNamePassenger;
	private String lastNamePassenger;
	
	public ReservationTicket() {
		
	}

	public ReservationTicket(int id, Flight goingFlight, Flight reverseFlight, int seatOnGoingFlight,
			int seatOnReverseFlight, String dateReservation, String dateOfSaleTicket,
			User userCreateReservationOrSaleTicket, String firstNamePassenger, String lastNamePassenger) {
		super();
		this.id = id;
		this.goingFlight = goingFlight;
		this.reverseFlight = reverseFlight;
		this.seatOnGoingFlight = seatOnGoingFlight;
		this.seatOnReverseFlight = seatOnReverseFlight;
		this.dateReservation = dateReservation;
		this.dateOfSaleTicket = dateOfSaleTicket;
		this.userCreateReservationOrSaleTicket = userCreateReservationOrSaleTicket;
		this.firstNamePassenger = firstNamePassenger;
		this.lastNamePassenger = lastNamePassenger;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Flight getGoingFlight() {
		return goingFlight;
	}

	public void setGoingFlight(Flight goingFlight) {
		this.goingFlight = goingFlight;
	}

	public Flight getReverseFlight() {
		return reverseFlight;
	}

	public void setReverseFlight(Flight reverseFlight) {
		this.reverseFlight = reverseFlight;
	}

	public int getSeatOnGoingFlight() {
		return seatOnGoingFlight;
	}

	public void setSeatOnGoingFlight(int seatOnGoingFlight) {
		this.seatOnGoingFlight = seatOnGoingFlight;
	}

	public int getSeatOnReverseFlight() {
		return seatOnReverseFlight;
	}

	public void setSeatOnReverseFlight(int seatOnReverseFlight) {
		this.seatOnReverseFlight = seatOnReverseFlight;
	}

	public String getDateReservation() {
		return dateReservation;
	}

	public void setDateReservation(String dateReservation) {
		this.dateReservation = dateReservation;
	}

	public String getDateOfSaleTicket() {
		return dateOfSaleTicket;
	}

	public void setDateOfSaleTicket(String dateOfSaleTicket) {
		this.dateOfSaleTicket = dateOfSaleTicket;
	}

	public User getUserCreateReservationOrSaleTicket() {
		return userCreateReservationOrSaleTicket;
	}

	public void setUserCreateReservationOrSaleTicket(User userCreateReservationOrSaleTicket) {
		this.userCreateReservationOrSaleTicket = userCreateReservationOrSaleTicket;
	}

	public String getFirstNamePassenger() {
		return firstNamePassenger;
	}

	public void setFirstNamePassenger(String firstNamePassenger) {
		this.firstNamePassenger = firstNamePassenger;
	}

	public String getLastNamePassenger() {
		return lastNamePassenger;
	}

	public void setLastNamePassenger(String lastNamePassenger) {
		this.lastNamePassenger = lastNamePassenger;
	}
	
	

}
