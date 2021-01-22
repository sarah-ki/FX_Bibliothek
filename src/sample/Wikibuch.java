package sample;

import java.util.Date;

// Name Sarah Franke
// MN 17617
//Datum der Erstellung: Novemeber
//Datum der Letzten Aenderung: 01/06/2020

public class Wikibuch extends OnlineMedium{
    private String[] kapitel;
    private String regal;
    private Date timestamp;
    private String urheber;


    /**
     * Konstruktor um ein Medium ohne xml zu erstellen
     * @param titel Titel des Mediums
     * @param url Adresse des MEdiums
     * @param timestamp Letzter Bearbeitungsstand d.M.
     * @param urheber d.M.
     * @param regal d.M.
     * @param kapitel, welche das Buch besitzt
     */
    public Wikibuch(String titel, String url, Date timestamp, String urheber, String regal, String[] kapitel) {
        super(titel, url);
        this.regal= regal;
        this.kapitel = kapitel;
        this.timestamp= timestamp;
        this.urheber= urheber;
    }

    /**
     * xml Konstruktor
     * @param titel s.o.
     * @param url s.o.
     */
    public Wikibuch(String titel, String url){
        super(titel, url);


    }

    /**
     * Methode um die Eigenschaften des Wikibuches auszugeben
     * @return
     */
    public String calculateRepresentation(){
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Titel: ");
            sb.append(getTitel());
            sb.append("\nURL: ");
            sb.append(getUrl());
            sb.append("\nRegal: ");
            sb.append(getRegal());
            for (int i = 1; i < getKapitel().length; i++) {
                // sb.append("\n" +i+ " Kapitel: ");
                sb.append("\n" + kapitel[i]);
            }
            sb.append("\nLetzte Bearbeitung: ");
            sb.append(getTimestamp());
            sb.append("\nUrheber: ");
            sb.append(getUrheber());

            return sb.toString();

        }catch(Exception e){
           // e.printStackTrace();
            System.err.println("Fehler: Kein gültiges Wikibuch oder Schreibfehler (Bitte gross und kleinschreibung beachten!), Element fehlt!");
        }
     return "Kein Wikibuch gefunden";
    }


    /**
     * getter & setter
     * @return
     */
    public String[] getKapitel() {
        return kapitel;
    }

    public void setKapitel(String[] kapitel) {
        this.kapitel = kapitel;
    }

    public String getRegal() {
        return regal;
    }

    public void setRegal(String regal) {
        this.regal = regal;
    }
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrheber() {
        return urheber;
    }

    public void setUrheber(String urheber) {
        this.urheber = urheber;
    }

}
