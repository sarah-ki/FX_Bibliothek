package sample;

// Name Sarah Franke
// MN 17617
//Datum der Erstellung: ?
//Datum der Letzten Aenderung: 11/29/2020

/**
 * Benutzerdefinierte Exception, wird geworfen, wenn z.B. doppelte Eintraege in der drop Methode vorkommen
 */
public class dublicateEntryException extends Throwable {
    public dublicateEntryException(){
        super();
    }

    public dublicateEntryException(String message){
        super(message);
    }
}

