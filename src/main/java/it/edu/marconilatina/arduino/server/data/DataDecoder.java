package it.edu.marconilatina.arduino.server.data;

import java.io.InputStream;

/**
 * Il compito di questa classe &egreve; decodificare i dati ricevuti.
 * </br>
 * Separare la logica di lettura miglira la riusabilt&agreva;
 * del codice e aunmenta il disaccoppiamneto.
 */
public class DataDecoder {

	public static final int DIMENSIONE_PACCHETTO = 7; // 7 byte previsti dal protocollo

	private int timestamp;
    private SensorDataType tipoDato;
    private int valore;
    private int checksum;
   
	public int getTimestamp() {
		return timestamp;
	}

	public SensorDataType getTipoDato() {
		return tipoDato;
	}

	public int getValore() {
		return valore;
	}

	public int getChecksum() {
		return checksum;
	}

	public void readPackets(byte[] buffer) {
    	
		if(buffer.length != DIMENSIONE_PACCHETTO) {
			throw new ArrayIndexOutOfBoundsException(
					String.format("Il pachetto troppo grande! MAX %s byrìte", DIMENSIONE_PACCHETTO)
					);
		}
		
    	/* 
    	 * Estrarre il timestamp
    	 * 
    	 * In Java, il tipo byte è con segno (signed) e rappresenta valori tra -128 e 127.
    	 * Ma spesso nei protocolli di rete, nei file binari o nei sistemi embedded, 
    	 * i byte sono trattati senza segno (unsigned), ovvero con valori tra 0 e 255.
    	 * Se interpretiamo direttamente un byte in Java, può diventare negativo,
    	 * perchè il bit più significativo (MSB) è il bit di segno.
    	 * Java preserva il bit segno, questo è chiamato sign extension. es:
    	 * +----------------------+----+---------------------------------------+
    	 * | byte b = (byte) 200; |-56 |1100 1000 (8 bit)                      |
    	 * |+---------------------+----+---------------------------------------+
    	 * | int valore = b;      |-56 |1111 1111 1111 1111 1111 1111 1100 1000|
    	 * |                      |    | (32 bit con estensione di segno)      |
    	 * +----------------------+----+---------------------------------------+
    	 * 0xFF in binario è 0000 0000 0000 0000 0000 0000 1111 1111, 
    	 * l'AND bitwise (&) mantiene solo gli ultimi 8 bit (1100 1000)
     	 * 
    	 * L'operazione & 0xFF trasforma il byte con segno in un numero senza segno.
    	 */
        this.timestamp = ((buffer[CampoDati.TEMP_H] & 0xFF) << 8) | 
        		         ( buffer[CampoDati.TEMP_L] & 0xFF);
        
        // Estrarre il tipo di dato
        this.tipoDato = SensorDataType.fromByte(buffer[CampoDati.TIPO_DATO]);
        
        /*
         * Estrarre il valore
         * 
         * Nel casodel valore dobbiamo considerare anche il segno
         * e percio' la parte alta del valore deve riportare il bit egno senza
         * alterazioni.
         */
        this.valore = ((buffer[CampoDati.VALORE_H]) << 8) | 
        		       (buffer[CampoDati.VALORE_L] & 0xFF);

        // Estrarre il checksum (opzionale, dipende dal protocollo)
        this.checksum = buffer[CampoDati.CHECKSUM] & 0xFF;
	}

	@Override
	public String toString() {
		return "DataDecoder [timestamp=" + timestamp + ", tipoDato=" + tipoDato + ", valore=" + valore + ", checksum="
				+ checksum + "]";
	}
		
}
