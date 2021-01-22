package sample;
// Name Sarah Franke
// MN 17617
//Datum der Erstellung: 11/27/2020
//Datum der Letzten Aenderung: 11/29/2020


/**
 * Benutzerdefinierte Exception, da es eine Runtime Exception ist kann diese in einer Klasse geworfen und in einer anderen abgefangen werden
 * wird geworfen z.B. bei unguelteiger ULR
 */
public class ISBNException extends RuntimeException {
    public ISBNException(String s) {
        super(s);
    }
}
