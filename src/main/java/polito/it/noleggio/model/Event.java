package polito.it.noleggio.model;

import java.time.LocalTime;

//rappresenta l'evento del mio simulatore
public class Event implements Comparable<Event>{
	
	public enum EventType{
		//enum = classi che definisco automaticamente dele costanti
		NUOVO_CLIENTE,//vale 0
		RITORNO_AUTO//vale 1
	}
	
	private LocalTime time;
	private EventType type;//questo type può valere 0 o 1
	
	@Override
	public int compareTo(Event other) {
		//faccio il confronto tra i tempi
		return this.time.compareTo(other.time);
	}

	public Event(LocalTime time, EventType type) {
		super();
		this.time = time;
		this.type = type;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Event [time=" + time + ", type=" + type + "]" +"\n";
	}
	
	
	
	
}
