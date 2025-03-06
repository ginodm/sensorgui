package it.edu.marconilatina.arduino.view;


public interface ISensorView {

	/**
	 * Metodo usato per aggiornare i dati visualizzati nella finestra.<br>
	 * 
	 * Utilizzando due attributi discaccopiamo la vista dall'implementazione
	 * del modello {@link ISensor}
	 * 
	 * @param sensorName
	 * @param value
	 */
	public void updateSensorData(String sensorName, float value);
}
