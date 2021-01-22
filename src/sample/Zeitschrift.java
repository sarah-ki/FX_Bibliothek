package sample;

import java.io.Serializable;

// Name Sarah Franke
// MN 17617
//Datum der Erstellung: 11/27/2020
//Datum der Letzten Aenderung: 11/29/2020
public class Zeitschrift extends Medium implements Serializable {
    private String issn;
    private int volume;
    private int nummer;

    /**
     * Konstruktor
     * @param titel Titel des Mediums
     * @param issn Pruefnumemr der Zeitschrift
     * @param volume Auflage der Zeitschrift
     * @param nummer Nummer zur Indentifikaton der Zeitschrift
     */
    public Zeitschrift(String titel, String issn, int volume, int nummer) {
        super(titel);
        this.issn=issn;
        this.volume=volume;
        this.nummer=nummer;
    }

    /**
     * Methide zur Ausgabe der Informationen
     * @return String
     */


    @Override
    public String calculateRepresentation() {
        StringBuilder sb= new StringBuilder();
        sb.append("Titel: ");
        sb.append(getTitel());
        sb.append("\nISSN: ");
        sb.append(getIssn());
        sb.append("\nVolume: ");
        sb.append(getVolume());
        sb.append("\nNummer: ");
        sb.append(getNummer()+"\n");
        return sb.toString();
    }

    /**
     * Getter & Setter
     */
    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
