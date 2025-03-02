package it.edu.marconilatina.arduino.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.edu.marconilatina.arduino.event.SensorDataEvent;
import it.edu.marconilatina.arduino.model.sensor.ISensor;
import it.edu.marconilatina.arduino.model.sensor.impl.FakeSensor;
import it.edu.marconilatina.arduino.view.impl.SensorViewMedusa;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Il controller e anche un event Target che riceve si registra per consumare
 * gli eneventi di tipo {@link SensorDataEvent}.
 */
public class SensorControllerED implements EventTarget, Initializable {

	private SensorViewMedusa view;

	@FXML
	private TableView<FakeSensor> tab;
	@FXML
    private TableColumn<FakeSensor, String> sensorColumn;

    @FXML
    private TableColumn<FakeSensor, Double> valueColumn;
	@FXML
	private Label statusLabel;

	@FXML
	private GridPane grid;

	private final ObservableList<FakeSensor> sensorDataList = FXCollections.observableArrayList();

	
	@FXML
	private void handleClose() {
		Platform.exit();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		view = new SensorViewMedusa();

		grid.add(view, 0, 0);
		GridPane.setHgrow(view, Priority.ALWAYS);
        GridPane.setVgrow(view, Priority.ALWAYS);
		statusLabel.setText("pippo");
		// Impostazione delle colonne della TableView
        sensorColumn.setCellValueFactory(new PropertyValueFactory<>("sensorName"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        tab.setItems(sensorDataList);
	}

	/**
	 * Metodo che gestisce l'evento e aggiorna i dati all'interno 
	 * degli strumenti digitali.
	 * @param e
	 */
	public void fireEvent(SensorDataEvent e) {
		ISensor s = e.getSensor();
		
		view.updateSensorData(s.getSensorName(), s.getValue());
		sensorDataList.add((FakeSensor) s);
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
