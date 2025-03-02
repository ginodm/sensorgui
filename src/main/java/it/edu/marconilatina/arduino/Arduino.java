package it.edu.marconilatina.arduino;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.edu.marconilatina.arduino.server.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
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
        
		try {
			
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main-scne.fxml"));
	        VBox root = loader.load();
	        
	        server = new Server(loader.getController());
	        thread = new Thread(server);
	                
	        Scene scene = new Scene(root, 900, 700);

	        primaryStage.setTitle("Sensor Simulator");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
	        LOG.info("Start application");
	        
	        thread.setDaemon(true);
	        thread.start();
		} catch (Exception e) {
			
			LOG.error("", e);
			
		}
    }

	@Override
	public void stop() throws Exception {
		LOG.info("Application stoped");
		if(server != null ) {
			server.stopServer();
			LOG.info("Server stoped");
		}
		
	}

}
