package it.edu.marconilatina.arduino.model.sensor.impl;

import java.util.Objects;

import it.edu.marconilatina.arduino.model.sensor.ISensor;

public class FakeSensor implements ISensor {

	private String sensorName;
    private double value;
    private long timestamp;
    
	public FakeSensor(String sensorName, double value, long timestamp) {
		super();
		this.sensorName = sensorName;
		this.value = value;
		this.timestamp = timestamp;
	}

	@Override
	public String getSensorName() {
		
		return this.sensorName;
	}

	@Override
	public double getValue() {
		
		return this.value;
	}

	@Override
	public long getTimestamp() {
	
		return this.timestamp;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sensorName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FakeSensor other = (FakeSensor) obj;
		return Objects.equals(sensorName, other.sensorName);
	}

	@Override
	public String toString() {
		return "FakeSensor [sensorName=" + sensorName + ", value=" + value + ", timestamp=" + timestamp + "]";
	}

}
