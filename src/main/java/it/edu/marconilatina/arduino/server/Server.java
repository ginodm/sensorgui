package it.edu.marconilatina.arduino.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.javafx.event.EventDispatchChainImpl;

import it.edu.marconilatina.arduino.event.SensorDataEvent;
import it.edu.marconilatina.arduino.model.sensor.ISensor;
import it.edu.marconilatina.arduino.model.sensor.impl.FakeSensor;
import it.edu.marconilatina.arduino.server.data.DataDecoder;
import javafx.application.Platform;
import javafx.event.EventTarget;

/**
 * La classe definisce un codice che puo' essere seguito all'interno di un thread.
 * 
 * Viene lanciato in esecuzione il matodo run.
 */
public class Server implements Runnable {

	private static final Logger LOG = LogManager.getLogger(Server.class);

	private int port;
	private boolean running;
	private ServerSocket serverSocket;
	private EventTarget eventTarget;

	public Server(EventTarget eventTarget) {
		super();
		init(5000, eventTarget);
	}

	public Server(int port, EventTarget et) {
		super();
		init(port, et);
		LOG.debug("Initialization Server complete");
	}

	/**
	 * Metodo creato per definire la configurazione del server che viene richamato
	 * all'interno dei costruttori. <br>
	 * @param port
	 * @param et
	 */
	protected void init(int port, EventTarget et) {
		this.port = port;
		this.eventTarget = et;
		this.running = true;

	}

	@Override
	public void run() {

		try {
			serverSocket = new ServerSocket(port);

			LOG.info("Sterting Server ...");
			while (running) {

				Socket clientSocket = serverSocket.accept();

				InputStream in = clientSocket.getInputStream();

				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

				// double value = DataSimulator.getFakeValue();
				// ISensor sensor = new FakeSensor("Temeperature", value, port);
				byte[] data = new byte[DataDecoder.DIMENSIONE_PACCHETTO];
				
				DataDecoder dd = new DataDecoder();
				
				while ((in.read(data)!= -1) && running) {
					try {
						
						dd.readPackets(data);
						
						ISensor sensor = new FakeSensor(dd.getTipoDato().getDescrizione(), 
														dd.getValore(), dd.getTimestamp());
						
						LOG.debug(String.format("Receiving data (%s)", sensor));

						SensorDataEvent event = new SensorDataEvent(sensor);
						/*
						 * Viene creata una catena per propagare gli eventi a tutti
						 * gli event target registrati passando SensorDataEvent
						 */
						Platform.runLater(() -> {
							eventTarget.buildEventDispatchChain(
									new EventDispatchChainImpl()
									).dispatchEvent(event);
							LOG.debug("Evnto generto");
						});

						
						
					} catch (Exception e) {
						LOG.error(e);
					}
				}
				
				// Thread.sleep(5000); // Pausa di 5 sec per generare il nuovo dato

			}

		} catch (Exception e) {
			if (running) {
				LOG.error(e);
			}
		} finally {
			stopServer();
		}

	}

	/**
	 * Metodo utilizzato per chiudere il server.
	 */
	public void stopServer() {
		running = false;
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (Exception e) {
			LOG.error(e);
		}

	}
}
