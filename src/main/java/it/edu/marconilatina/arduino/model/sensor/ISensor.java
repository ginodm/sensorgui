package it.edu.marconilatina.arduino.model.sensor;

public interface ISensor {

	public String getSensorName();
    public double getValue();
    public long getTimestamp();
}
