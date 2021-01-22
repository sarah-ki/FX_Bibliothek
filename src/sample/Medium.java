package sample;

import java.io.Serializable;

// Name Sarah Franke
// MN 17617
//Datum der Erstellung: 11/27/2020
//Datum der Letzten Aenderung: 11/29/2020
public abstract class Medium implements Comparable, Serializable {

    private String titel;

    /**
     *     Konstruktor
     *     Jedes Medium hat einen Titel, auch die Klassen,welche von medium erben
     */
    public Medium(String titel){
        try {
            setTitel(titel);
        }catch (ISBNException i){
            i.printStackTrace();
            System.out.println("Titelfeld leer?\nMedium kann nicht hinzugefuegt werden! ");
            System.exit(0);
        }
    }

    /**
     * Methode zur Ausgabe der Informationen ueber da Medium
     */
    public abstract String calculateRepresentation();

    /**
     * Getter & Setter
     */
    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {

        if(titel.isBlank())
            throw new ISBNException("Titel leer");
            else
        this.titel = titel;
    }

    /**
     * Sortiert die Medien indem es vergleicht, sollten die Titel gleich sein wird stattdessen nach Klasse getauscht
     * @param medium zu vergleichende Medien
     * @return 0 wenn die beiden Eintraege gleich sind, bei groesser 0 wenn der Titel ebenfalls hoeher eingeordnet werden sollte und kleiner 0 wenn dieser darunter eingeordnet werden sollte
     */
    public int compareTo(Medium medium) {
if(this.titel.compareTo(medium.titel)==0){
    return this.getClass().toString().compareTo(medium.getClass().toString());
}else{
    return this.titel.compareTo(medium.titel); }}


}
