package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// Name Sarah Franke
// MN 17617
// Datum der Erstellung: ?
// Datum der Letzten Aenderung: 11/29/2020
public class HumanReadablePersistency implements Persistency{

public HumanReadablePersistency(){

}

    /**
     * Speichert den Array des Zettelkastens Eintrag fuer Eintrag lesbar in einer Datei ab
     * @param zk der zu spechernde Zettelkasten
     * @param dateiname Name der datei, in die gespecihert werden soll
     */
    @Override
    public void save(Zettelkasten zk, String dateiname) {
        try {

            File file = new File(dateiname+".ser");
            FileWriter writer = new FileWriter(dateiname+".ser");
             for(int i=0; i<zk.medienarr.size(); i++){
            writer.write(zk.medienarr.get(i).calculateRepresentation());}
            writer.close();

        } catch (IOException e) {

            e.printStackTrace();
            System.exit(0);
        }


    }

    /**
     * Wirft eine Exception um vor unerlaubtem Zugriff zu schuetzen und den benutzer zu warnen dass diese Funktion noch nicht zur Verfuegung steht, sollte dieser versuchen sie Aufzurufen
     * @param dateiname
     * @return - noch nihct implementiert
     */
    @Override
    public Zettelkasten load(String dateiname) {
    try{
        throw new UnsupportedOperationException("Noch nicht implementiert! ");}
    catch(Exception e){
        System.out.print("Noch nicht verfuegbar! ");
        e.printStackTrace();
        System.exit(0);
    }
        return null;
    }
}
