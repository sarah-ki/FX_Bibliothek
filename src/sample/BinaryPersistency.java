package sample;

import java.io.*;

// Name Sarah Franke
// MN 17617
// Datum der Erstellung: ?
// Datum der Letzten Aenderung: 11/29/2020
public class BinaryPersistency implements Persistency{
    public BinaryPersistency(){

    }

    /**
     * Speichert den Zettelkasten nach der Ausgabe der zu speichernden Daten in einer Datei ab
     * @param zk uebergebenes zettelkasten Objekt, welches mit dieser methode Serialisiert in eine Datei geschrieben werden soll
     * @param dateiname Name der Datei zur Speicherung
     */
    @Override
    public void save(Zettelkasten zk, String dateiname) {
        System.out.println("\n(BinaryPersistency) Serialisierung folgender Daten: ");
        for(int i=0; i<zk.medienarr.size(); i++){
System.out.println(zk.medienarr.get(i).calculateRepresentation());}
        try {
            ObjectOutput out = new ObjectOutputStream(new FileOutputStream(dateiname+".ser"));
            out.writeObject(zk);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Serialisierung nihct moeglich");
            System.exit(0);
        }

    }

    /**
     * Deserialisierung der daten eines Zettelkastens + Test mit Hilfe einer Ausgabe auf der Konsole
     * @param dateiname Name der zu lesenden Datei
     */
    @Override
    public Zettelkasten load(String dateiname) {
        try {
            System.out.println("(BinaryPersistency) Deserialisierung: ");
       ObjectInput in = new ObjectInputStream(new FileInputStream(dateiname+".ser"));
       Zettelkasten zk2 = (Zettelkasten) in.readObject();
        in.close();
            for(int i=0; i<zk2.medienarr.size(); i++){
                System.out.println(zk2.medienarr.get(i).calculateRepresentation());}
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
            e.printStackTrace();
            System.out.println("Deserialisierung nicht moeglich");
            System.exit(0);
    }
        return null;
    }
}
