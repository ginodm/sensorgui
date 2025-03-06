package it.edu.marconilatina.arduino.model.sensor;

public interface ISensor {

	public String getSensorName();
    public float getValue();
    public long getTimestamp();
}
