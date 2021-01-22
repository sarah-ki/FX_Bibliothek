package sample;

import java.io.Serializable;

// Name Sarah Franke
// MN 17617
//Datum der Erstellung: ?
//Datum der Letzten Aenderung: 11/29/2020
public class CD extends Medium implements Serializable {
    private String label;
    private String kuenstler;

    /**
     * Getter & Setter
     */
    public String getLabel() {
        return label;
    }

    public String getKuenstler() {
        return kuenstler;
    }

    public void setKuenstler(String kuenstler) {
        this.kuenstler = kuenstler;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Konstruktor
     * @param titel Titel des Mediums
     * @param label Label der CD
     * @param kuenstler Kuenstler der CD
     */
    public CD(String titel, String label, String kuenstler) {
        super(titel);
        this.label=label;
        this.kuenstler=kuenstler;
    }

    /**
     * Methode zur Ausgabe von Informationen ueber die CD
     * @return String
     */


    @Override
    public String calculateRepresentation() {
        StringBuilder sb= new StringBuilder();
        sb.append("Titel: ");
        sb.append(getTitel());
        sb.append("\nLabel: ");
        sb.append(getLabel());
        sb.append("\nKuenstler: ");
        sb.append(getKuenstler()+"\n");

        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
