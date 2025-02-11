package it.edu.marconilatina.arduino;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.edu.marconilatina.arduino.controller.SensorControllerED;
import it.edu.marconilatina.arduino.server.Server;
import it.edu.marconilatina.arduino.view.ISensorView;
import it.edu.marconilatina.arduino.view.impl.SensorViewMedusa;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Arduino extends Application {

	private static final Logger LOG = LogManager.getLogger(Arduino.class);
	
	private Server server;
	private Thread thread;
	
	public static void main(String[] args) {
        launch(args);
    }
	
	@Override
    public void start(Stage primaryStage) {
        ISensorView root = new SensorViewMedusa();
        
        LOG.info("Start application");
        
        server = new Server(new SensorControllerED(root));
        thread = new Thread(server);
                
        Scene scene = new Scene((SensorViewMedusa)root, 300, 250);

        primaryStage.setTitle("Sensor Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        thread.setDaemon(true);
        thread.start();
    }

	@Override
	public void stop() throws Exception {
		
		if(server != null ) {
			server.stopServer();
			LOG.info("Application stoped");
		}
		
	}

}
