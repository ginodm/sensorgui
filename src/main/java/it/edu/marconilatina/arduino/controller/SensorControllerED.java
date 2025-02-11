package it.edu.marconilatina.arduino.controller;

import it.edu.marconilatina.arduino.event.SensorDataEvent;
import it.edu.marconilatina.arduino.model.sensor.ISensor;
import it.edu.marconilatina.arduino.view.ISensorView;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.event.EventTarget;

/**
 * Il controller e anche un event Target che riceve si registra per consumare
 * gli eneventi di tipo {@link SensorDataEvent}.
 */
public class SensorControllerED implements EventTarget {

	private final ISensorView view;
	
	public SensorControllerED(ISensorView view) {
		super();
		this.view = view;
	}

	/**
	 * Metodo che gestisce l'evento e aggiorna i dati all'interno 
	 * degli strumenti digitali.
	 * @param e
	 */
	public void fireEvent(SensorDataEvent e) {
		ISensor s = e.getSensor();
		view.updateSensorData(s.getSensorName(), s.getValue());
	}

	/* Cos'e' la Event Dispatch Chain?
	 * 
	 * Quando un evento viene generato in JavaFX, 
	 * non viene gestito immediatamente da un solo componente ma viene crea una catena 
	 * di distribuzione degli eventi che permette a più nodi di intercettare, 
	 * modificare o gestire l'evento prima che arrivi al target finale.
	 * Questa catena è utile per:
	 *    - Propagare eventi in una gerarchia di nodi JavaFX.
	 *    - Permettere a più oggetti di gestire lo stesso evento.
	 * Questo metoto Viene chiamato automaticamente dal sistema JavaFX quando un evento 
	 * viene inviato a un EventTarget.
	 * Se una classe implementa EventTarget, deve fornire un'implementazione di questo 
	 * metodo per aggiungere se stessa alla catena degli eventi.
	 */
	@Override
	public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
		
		return tail.prepend(new EventDispatcher() { // Creo una classe anonima per gestire l'evento
			
			@Override
			public Event dispatchEvent(Event event, EventDispatchChain tail) {
				
				if(event instanceof SensorDataEvent) {
					
					fireEvent((SensorDataEvent) event);
					
					return null; // bolocco la propagazione dell'evento
				}
				return tail.dispatchEvent(event);
			}
		});
	}

}
