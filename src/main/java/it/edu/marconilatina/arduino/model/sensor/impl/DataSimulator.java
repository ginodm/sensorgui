package it.edu.marconilatina.arduino.model.sensor.impl;

import java.util.Random;

public class DataSimulator {

	private Random random = new Random();
	
	public FakeSensor getSensorReading() {
        float value = 20 + random.nextFloat() * 10; // Simulazione valore temperatura
        
        return new FakeSensor("TemperatureSensor", value, System.currentTimeMillis());
    }
	
	public static double getFakeValue() {
		Random random = new Random();
		double value = 20 + random.nextDouble() * 10; // Simulazione valore temperatura
		
		return value;
	}
}
