package polito.it.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.PriorityQueue;

import polito.it.noleggio.model.Event.EventType;


public class Simulator {
	
	//Eventi
	private PriorityQueue<Event> queue;
	
	//Parametri d'ingresso
	private int NC; //number of cars
	private Duration T_IN; //intervallo di tempo tra i clienti
	private LocalTime oraApertura = LocalTime.of(8, 0);
	private LocalTime oraChiusura = LocalTime.of(20, 0);

	
	//Stato del sistema
	private int nAuto; //auto attualmente presenti
	
	//Misure in uscita
	private int nClienti;
	private int nClientiInsoddisfatti;
	
	//impostazioni dei parametri iniziali
	
	public void setNumCars(int NC) {
		this.NC = NC;
	}
	
	public void setClientFrequency(Duration d) {
		this.T_IN = d;
	}
	
	//Simulazione
	
	public void run() {
		this.queue = new PriorityQueue<Event>();
			
			//Stato iniziale
			this.nAuto = NC; //all'inizio della simulazione tutte le auto sono in garage 
			this.nClienti = 0;
			this.nClientiInsoddisfatti = 0;
			
			//Eventi iniziali
			LocalTime ora = this.oraApertura ;
			while(ora.isBefore(this.oraChiusura)) {
				this.queue.add(new Event(ora, EventType.NUOVO_CLIENTE));
				ora = ora.plus(T_IN); //incremento l'ora
			}
			
			//Ciclo di simulazione 
			while(!this.queue.isEmpty()) {//finchè la coda non è vuota
				Event e = this.queue.poll(); //estrai il prossimo evento
			    //System.out.print(e);
				processEvent(e);//ed elaboralo
			}
	}
			private void processEvent(Event e) {
				switch(e.getType()) {
				
				case NUOVO_CLIENTE: //nel caso ho un nuovo cliente faccio una cosa
					this.nClienti++;//in ogni caso aumento il numero di clienti
					//devo controllare se a questo nuovo cliente osso dare un'auto oppure no
					if(this.nAuto>0) {
						//noleggia
						this.nAuto--;
						//devo anche prevedere che l'auto rientri in un certo intervallo di tempo
						
						//estraggo casualmente il tempo
						double num = Math.random()*3; //estraggo un numero casuale [0,3)
						if (num<1) {//prevedo che arriverà l'auto tra un'ora
							this.queue.add(new Event (e.getTime().plus(Duration.of(1, ChronoUnit.HOURS)),//l'ora di adesso più un'ora
									EventType.RITORNO_AUTO));

						}else {
							this.queue.add(new Event(e.getTime().plus(Duration.of(2, ChronoUnit.HOURS)),
									EventType.RITORNO_AUTO));
						}
						
					}else {
						//insoddisfatto
						this.nClientiInsoddisfatti++;
					}
					break;
				case RITORNO_AUTO:  //nel caso un cliente restituisce un auto faccio un'altra cosa
					this.nAuto++;
					break;
					
				}
			}
		
			
	public int getTotClients() {
		return this.nClienti;
	}
	
	
	public int getDissatisfied() {
		return this.nClientiInsoddisfatti;
	}
	

}
