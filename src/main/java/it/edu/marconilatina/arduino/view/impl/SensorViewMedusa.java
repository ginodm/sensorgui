package it.edu.marconilatina.arduino.view.impl;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import it.edu.marconilatina.arduino.view.ISensorView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Implementazione degli strimenti digitali inseriti nella finestra.
 */
public class SensorViewMedusa extends Pane implements ISensorView{

	private Gauge thermometer;
    private Gauge lcdDisplay;
    
    public SensorViewMedusa() {
    	// Creazione termometro con Medusa
        thermometer = GaugeBuilder.create()
            .skinType(Gauge.SkinType.DASHBOARD)
            .title("Termometro")
            .unit("°C")
            .minValue(-50)
            .maxValue(50)
            .animated(true)
            .maxSize(Double.MAX_VALUE, Double.MAX_VALUE)
            .build();

        // Creazione display LCD per temperatura numerica
        lcdDisplay = GaugeBuilder.create()
            .skinType(Gauge.SkinType.LCD)
            .title("Temperatura")
            .unit("°C")
            .minValue(-50)
            .maxValue(50)
            .animated(true)
            .build();

        getChildren().addAll(lcdDisplay);
        
	}
    
    
    public void updateSensorData(String sensorName, float value) {
        lcdDisplay.setValue(value);
        thermometer.setValue(value);
    }
}
