package it.edu.marconilatina.arduino.view.impl;

import it.edu.marconilatina.arduino.view.ISensorView;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SensorView extends VBox implements ISensorView{

	private Label label;
	
	public SensorView() {
		label = new Label("Dati sensore: ");
        getChildren().add(label);
    }
	
	public void updateSensorData(String sensorName, float value) {
        label.setText(sensorName + ": " + value + "°C");
    }	
	
}
