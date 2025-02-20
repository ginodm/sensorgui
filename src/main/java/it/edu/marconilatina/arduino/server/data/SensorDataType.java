package it.edu.marconilatina.arduino.server.data;

/**
 * <b>Cosa è un enum in Java?</b></br>
 * 
 * Un enum (enumeration) in Java &egrave; un tipo 
 * speciale di classe che rappresenta un insieme fisso di costanti.
 * </br>
 * Le caratteristche pi&ugrave; sono:
 * <ul>
 * <li>Ogni valore di un enum &egrave; una costante pubblica e statica.</li>
 * <li>Gli enum possono avere campi, metodi e costruttori, proprio come una classe.</li>
 * <li> Gli enum non possono essere istanziati direttamente con new, 
 * perch&eacute, le istanze sono definite all'interno dell'enum.
 * </li>
 * </ul>
 * Migliora la leggibilit&agrave; 
 * del codice rispetto all'uso di numeri o stringhe per rappresentare stati o tipi e 
 * definendo un se di valori fissi peviene gli errori.
 * 
 */
public enum SensorDataType {
	/*
	 * Definamo tre ipi di dati fissi per identificarei tipo di valori ricevuti.
	 */
	TEMPERATURA(0x01, "Temperatura"),
	UMIDITA(0x02, "Umidità"),
	SCONOSCIUTO(0x00, "Sconosciuto");
	
	private final int codice;
    private final String descrizione;
    
    SensorDataType(int codice, String descrizione) {
        this.codice = codice;
        this.descrizione = descrizione;
    }
    
    /**
     * Ritorna il codice del tipo di dato
     * 
     * @return int code
     */
    public int getCodice() {
        return codice;
    }

    /**
     * Ritrna il nome
     * @return string Nom mnemonico
     */
    public String getDescrizione() {
        return descrizione;
    }

    // Metodo per ottenere il TipoDato dal valore ricevuto
    public static SensorDataType fromByte(byte valore) {
        for (SensorDataType tipo : SensorDataType.values()) {
            if (tipo.codice == (valore & 0xFF)) {
                return tipo;
            }
        }
        return SCONOSCIUTO;
    }
}
