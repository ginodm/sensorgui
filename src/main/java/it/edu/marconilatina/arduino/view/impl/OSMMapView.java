package it.edu.marconilatina.arduino.view.impl;

import java.net.URL;

import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class OSMMapView extends Pane {

	private final WebView webView;
    private final WebEngine webEngine;
    
    public OSMMapView() {
    	
    	webView = new WebView();
    	webEngine = webView.getEngine();
    	
        URL url = getClass().getResource("/html/map.html");
        
        // Carica lapagina html dove viene visualizzata la mappa
        if (url != null) {
            webEngine.load(url.toExternalForm());
        }
        
        getChildren().add(webView);
    }
    
    // Metodo per aggiungere un marcatore
    public void addMarker(double lat, double lng, String id) {
        webEngine.executeScript("addMarker(" + lat + ", " + lng + ", '" + id + "');");
    }

    // Metodo per far lampeggiare un marcatore
    public void blinkMarker(String id) {
        webEngine.executeScript("blinkMarker('" + id + "');");
    }
}
