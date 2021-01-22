package sample;
// Name Sarah Franke
// MN 17617
//Datum der Erstellung: 11/27/2020
//Datum der Letzten Aenderung: 11/29/2020
import java.io.Serializable;
import java.net.URL;

/**
 * onlineMedium erbt von Medium (Polymorphie)
 */
public class OnlineMedium extends Medium implements Serializable {
private String url;

    /**
     * Konstruktor
     * @param titel Titel des Mediums
     * @param url Internet Adresse des Online Mediums
     */
    public OnlineMedium(String titel, String url) {
        super(titel);
        try {
            setUrl(url);
        }catch(ISBNException e){
            e.printStackTrace();
            System.out.println("ungueltige url?\nMedium kann nicht hinzugefuegt werden! ");
            System.exit(0);

        }
    }

    /**
     * Getter & Setter
     */
    public String getUrl() {
        return url;
    }

    /**
     * Aufruf des Setters prueft die Gueltigkeit der Addesse
     */
    public void setUrl(String url){
        try
        {
            URL _url = new URL(url);
            _url.toURI();
            this.url = url;
        } catch (Exception exception)
        {
            throw new ISBNException("url ungueltig!");
        }


    }

    /**
     * Ausgabe der Informationen ueber das Online Medium
     * @return
     */

    public String calculateRepresentation(){
        StringBuilder sb= new StringBuilder();
        sb.append("Titel: ");
        sb.append(getTitel());
        sb.append("\nURL: ");
        sb.append(getUrl()+"\n");


        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
