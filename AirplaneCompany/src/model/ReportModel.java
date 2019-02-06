package model;

public class ReportModel {

	private String airportName;
	private int flightCount;
	private int ticketsCount;
	
	public ReportModel() {
		
	}
	
	public ReportModel(String airportName, int flightCount, int ticketsCount) {
		super();
		this.airportName = airportName;
		this.flightCount = flightCount;
		this.ticketsCount = ticketsCount;
	}
	public String getAirportName() {
		return airportName;
	}
	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}
	public int getFlightCount() {
		return flightCount;
	}
	public void setFlightCount(int flightCount) {
		this.flightCount = flightCount;
	}
	public int getTicketsCount() {
		return ticketsCount;
	}
	public void setTicketsCount(int ticketsCount) {
		this.ticketsCount = ticketsCount;
	}
	
	
}
