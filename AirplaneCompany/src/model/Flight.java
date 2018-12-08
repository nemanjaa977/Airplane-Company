package model;

public class Flight {
	
	private int id;
	private String number;
	private String dateGoing;
	private String dateComing;
	private Airport goingAirport;
	private Airport comingAirport;
	private int seatNumber;
	private double priceTicket;
	private boolean deleted;
	
	public Flight() {
		
	}

	public Flight(int id, String number, String dateGoing, String dateComing, Airport goingAirport, Airport comingAirport,
			int seatNumber, double priceTicket, boolean deleted) {
		super();
		this.id = id;
		this.number = number;
		this.dateGoing = dateGoing;
		this.dateComing = dateComing;
		this.goingAirport = goingAirport;
		this.comingAirport = comingAirport;
		this.seatNumber = seatNumber;
		this.priceTicket = priceTicket;
		this.deleted = deleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDateGoing() {
		return dateGoing;
	}

	public void setDateGoing(String dateGoing) {
		this.dateGoing = dateGoing;
	}

	public String getDateComing() {
		return dateComing;
	}

	public void setDateComing(String dateComing) {
		this.dateComing = dateComing;
	}

	public Airport getGoingAirport() {
		return goingAirport;
	}

	public void setGoingAirport(Airport goingAirport) {
		this.goingAirport = goingAirport;
	}

	public Airport getComingAirport() {
		return comingAirport;
	}

	public void setComingAirport(Airport comingAirport) {
		this.comingAirport = comingAirport;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public double getPriceTicket() {
		return priceTicket;
	}

	public void setPriceTicket(double priceTicket) {
		this.priceTicket = priceTicket;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
}
