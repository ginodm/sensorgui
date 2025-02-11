package it.edu.marconilatina.arduino.event;

import it.edu.marconilatina.arduino.model.sensor.ISensor;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * Evento creato per segnalare la ricezione di dati.
 */
public class SensorDataEvent extends Event {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Attributi utilizzato per savare i dati ricevuti 
	 * e propagarli allinterno dell'applicazione
	 */
	private final ISensor sensor;
	
	public static final EventType<SensorDataEvent> SENSOR_DATA_RECEIVED =
            new EventType<>(Event.ANY, "SENSOR_DATA_RECEIVED");

	public SensorDataEvent(ISensor sensor) {
		super(SENSOR_DATA_RECEIVED);
		this.sensor = sensor;
	}

	public ISensor getSensor() {
		return sensor;
	}

	@Override
	public String toString() {
		return "SensorDataEvent [sensor=" + sensor + "]";
	}
	
}
