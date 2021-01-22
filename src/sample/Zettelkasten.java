package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

// Name Sarah Franke
// MN 17617
// Datum der Erstellung: 11/27/2020
// Datum der Letzten Aenderung: 11/29/2020
public class Zettelkasten implements Iterable<Medium>, Serializable {
    /**
     * medienarr ist eine Liste Welche Alle Medien verschiedener Klassen eines Zettelkastens enthaelt
     * sort steigert die Effizienz beim sortieren und wird true gesetzt sollte der Kasten schoneinmal die Funktion sort aufgerufen haben
     * sort wird wieder false gesetzt falls neu sortiert werden muss, z.B. wenn Medien hinzugefuegt oder geloescht werden.
     */
    public ArrayList<Medium> medienarr;
    boolean sort=false;

    /**
     * im Konstruktor wird die Liste lediglich initialisisert
     */
    public Zettelkasten(){
this.medienarr=new ArrayList<Medium>();

}

    /**
     * sort wird false gesetzt false die Liate bereits sortiert wurde, nun aber ein neues medium hinzugefuegt wurde und bei einem erneuten Aufruf die Reihenfolge aktualisiert werden muss
     * @param _medium der Liste hinzuzufuegendes Medium (z.B. Buch, OnlineMedium, ...)
     */
    public void addMedium(Medium _medium){

    medienarr.add(_medium);
    sort=false;
    }

    /**
     * Die Exception beendet das Programm
     * index speichert den index im Array des Fundes
     * gefunden zaehlt die Anzahl der Funde um herauszufunden ob eine Exception geworfen werden muss oder ob weiter gesucht wird
     * @param titel der Titel des zu loeschenden Mediums, bei mehreren uebereinstimmungen wird ein Fehler angezeigt und bei keinen uebereinstimmungen erhaelt der benutzer ebenfalls eine Nachricht auf der Konsole
     */
    public void dropMedium(String titel){
    int gefunden=0;
    int index=0;
    for(int i =0; i< medienarr.size(); i++){
    if((medienarr.get(i).getTitel()==titel)&&gefunden==0) {
       // System.out.println("gefunden: " + medienarr.get(i).getTitel().toString());
        gefunden++;
        index=i;

    }else if((medienarr.get(i).getTitel()==titel)&&gefunden>0){try{
throw new dublicateEntryException("Mehrere Eintraege mit gleichem Titel gefunden!");}
catch (dublicateEntryException e) {
System.out.print("Eventuell Methode dropMultipleMedia verwenden");
        e.printStackTrace();
        System.exit(0);
    }
        break;
        /**
         * wenn der gesamte Array auf den ges. Titel ueberpruft worden ist und nur ein Exemplar gefunden worden ist wird dies anhand des gespeicherten indexes geloescht
         */
    }else if(i==medienarr.size()&gefunden==1){
        medienarr.remove(index);
    }

    else{
        System.out.println("Medium wurde nicht gefunden  " /*+ medienarr.get(i).getTitel().toString()*/);}
    }
    }

    /**sort- Wenn Eintraege geloescht werden kann es sein, dass die ordnung der liste aktualisiert werden muss
     * gefunden- Speichert ob ueberhaupt irgendwelche titel vorhanden sind, welche den Vorgaben entsprechen
     * geloescht- Wie viele Eintrage werden geloescht (kontrolliert, dass nihct mehr eintraege als gewuenscht geloescht werden, sobald mehr vorhanden sind)
     *
     * Wenn es gewuenscht ist koennen mit dieser methode beliebig viele Eintraege geloescht werden
     * @param titel Titel des mediums (der Medien) welche geloescht werden sollen
     * @param anzahl Welche Anzahl an den gefundenen Eintraegen soll geloscht werden? 0-vorhandene Anzahl moeglich, ansonsten wird eine Exception geworfen und das Programm beendet
     *
     */
    public void dropMultipleMedia(String titel, int anzahl){
        sort=false;
        boolean gefunden=false;
        int geloescht =0;
        if(anzahl>medienarr.size()|anzahl<0){
            try {
                throw new IllegalArgumentException("Anzahl ungueltig!");
            } catch (Exception e) {
                System.out.print("Bitte erneut versuchen!");
                e.printStackTrace();
                System.exit(0);
            }
        }else {
            for (int i = 0; i < medienarr.size() & geloescht<anzahl; i++) {
                if ((medienarr.get(i).getTitel() == titel)) {
                 //   System.out.println("gefunden: " + medienarr.get(i).getTitel().toString());
                    medienarr.remove(i);
                    gefunden = true;
                    geloescht++;
                } else if ((i == medienarr.size()) & !gefunden) {
                    System.out.println("Medium wurde nicht gefunden  " /*+ medienarr.get(i).getTitel().toString()*/);
                } else {
                    try {
                        throw new Exception("Fehler");
                    } catch (Exception e) {
                        System.out.print("Bitte erneut versuchen!");
                        e.printStackTrace();
                        System.exit(0);
                    }

                }
            }
        }

    }

    /**
     * Gibt eine Liste zurueck mit allen gesuchten Objekten, sollten keine vorhanden sein eine Meldung auf der konsole
     * @param _title Nach Welchem Titel wird gesucht?
     * @return Liste mit allen entsprechenden, gefundenen Objekten
     */
    public List findMultipleMedia(String _title){
List<Medium> list = new ArrayList<Medium>();
        boolean gefunden = false;
        System.out.print("Suche Medium...\n");
        for (int i = 0; i < medienarr.size(); i++) {
            if (medienarr.get(i).getTitel() == _title) {
                list.add(medienarr.get(i));
                gefunden=true;

            }else{
                System.out.println(".  ");
            }
        }
        if (!gefunden){System.out.println("Medium wurde nicht gefunden  ");
        return null;}
        else{
            return list;
        }



    }

    /**
     *
     * @param titel Titel des Mediums
     * @return name (Titel) des Mediums, welches dem gesuchten entspricht, wenn keines gefunden wird, wird null zurueckgegeben + eine Meldung an den Benutzer
     */
    public String findMedium(String titel) {
        boolean gefunden = false;
    System.out.print("Suche Medium...\n");
        for (int i = 0; i < medienarr.size(); i++) {
            if (medienarr.get(i).getTitel() == titel) {
                System.out.println("gefunden: " + medienarr.get(i).getTitel().toString());
                gefunden=true;
                return medienarr.get(i).getTitel();
            } else {
                System.out.println(".  " /*+ medienarr.get(i).getTitel().toString()*/);
                gefunden=false;
            }

        }
        if (!gefunden)System.out.println("Medium wurde nicht gefunden  ");
        return null;
    }


    /**
     * Sortiert die Liste anhand des uebergebenen String Parameters. Es kann entweder "aufsteigend" oder "absteigend sortiert werden"
     * @param reihenfolge bestimmt in welcher reihenfolge sortiert wird (A->Z oder Z<-A)
     *                    wird ein anderer String uebergeben wird eine Exception geworfen
     *
     * Ist der Zettelkasten durch sort angegeben noch sortiert wird eine Nachricht an den Benutzer gegeben
     */
    public void sort(String reihenfolge) {

    //sort
if(sort=!false) {
    try {
        if (reihenfolge == "aufsteigend") {
            medienarr.sort(Medium::compareTo);
           // for (int i = 0; i < medienarr.size(); i++) {
            //    System.out.println(medienarr.get(i).getTitel().toString());
           // }
            sort=true;
        } else if (reihenfolge == "absteigend") {
            medienarr.sort(Medium::compareTo);
            Collections.reverse(medienarr);
          //  for (int i = 0; i < medienarr.size(); i++) {
          //      System.out.println(medienarr.get(i).getTitel().toString());
          //  }
            sort=true;
        } else {
            throw new IllegalArgumentException("Keine gueltige Eingabe! ");

        }
    } catch (Exception e) {
        e.printStackTrace();
        System.out.print("Bitte \"aufsteigend\" oder \"absteigend\" fuer die Sortiermethode angeben!");
    }

}else{
    System.out.println("\nZettelkasten ist schon sortiert!");
}
    }
//--------------------------------------------------

    /**
     * Iterator Interface fuer Aufgabe 4 um durch den Zettelkasten medienarr iterieren zu koennen
     * @return
     */

@Override
public Iterator iterator() {
    Iterator iterator = new Iterator() {

        private int i = 0;

        @Override
        public boolean hasNext() {
            return i < medienarr.size() && medienarr.get(i) != null;
        }

        @Override
        public Object next() {
            return medienarr.get(i++);
        }

        @Override
        public void remove() {

        }
    };
    return iterator;
}

}
