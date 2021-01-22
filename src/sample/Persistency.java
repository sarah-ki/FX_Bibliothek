package sample;

// Name Sarah Franke
// MN 17617
//Datum der Erstellung: 11/27/2020
//Datum der Letzten Aenderung: 11/29/2020

/**
 * Interface welches Sicherstellt dass andere Persistierungen auch load und save Methoden besitzen
 */
public interface Persistency {
     public void save(Zettelkasten zk, String dateiname);
     public Zettelkasten load(String dateiname);
    }

