package it.polito.tdp.food.model;


public class Event implements  Comparable<Event>{
	
	public enum EventType{
		PREPARAZIONE, FINE
	}
	
	private Double time;
	private EventType type;
	private Cucina c;
	private Food f;
	
	public Event(Double time, EventType type, Cucina c, Food f) {
		super();
		this.time = time;
		this.type = type;
		this.c = c;
		this.f = f;
	}

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Cucina getC() {
		return c;
	}

	public void setC(Cucina c) {
		this.c = c;
	}

	public Food getF() {
		return f;
	}

	public void setF(Food f) {
		this.f = f;
	}

	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.getTime());
	}

}
