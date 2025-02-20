package it.edu.marconilatina.arduino.server.data;

public interface CampoDati {

	public static final int HEADER 		= 0x00; // Byte 0 Intstazione
	
	public static final int TEMP_H		= 0x01; // Byte 1 Parte alta del dato Timestamp
	public static final int TEMP_L		= 0x02; // Byte 2 Parte bassa del dato Timestamp
	
	public static final int TIPO_DATO   = 0x03;  // Byte 3 Tipo di dato
	
	public static final int VALORE_H    = 0x04;  // Byte 4 Parte alta del valore
	public static final int VALORE_L    = 0x05;  // Byte 5 Parte bassa del valore
	
	public static final int CHECKSUM    = 0x06;  // Byte 6 Checksum
}
