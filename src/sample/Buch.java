package sample;

import java.io.Serializable;

// Name Sarah Franke
// MN 17617
//Datum der Erstellung: ?
//Datum der Letzten Aenderung: 11/29/2020
public class Buch extends Medium implements Serializable {
   private int erscheinungsjahr;
   private String verlag;
   private String isbn;
   private String verfasser;




    /**
     * Konstruktor
     * @param titel Titel des Mediums
     * @param erscheinungsjahr Jahr in dem das Buch verfasst worden ist
     * @param verlag Verlag, bei welchem das Buch erschienen ist
     * @param isbn Pruefnummer
     * @param verfasser Autor
     */
    public Buch(String titel, int erscheinungsjahr, String verlag, String isbn, String verfasser) {
        super(titel);
        this.erscheinungsjahr=erscheinungsjahr;
        this.verlag=verlag;
            try {
                setIsbn(isbn);
            }catch (ISBNException ie){
                ie.printStackTrace();
                System.out.println("Buch mit falscher isbn? ungueltige url?\nMedium kann nicht hinzugefuegt werden! ");
                System.exit(0);
            }
        this.verfasser=verfasser;
    }

    /**
     * Ausgabe der Informationen ueber das Buch
     */

    @Override
    public String calculateRepresentation() {
        StringBuilder sb= new StringBuilder();
        sb.append("Titel: ");
        sb.append(getTitel());
        sb.append("\nErscheinungsjahr: ");
        sb.append(getErscheinungsjahr());
        sb.append("\nVerlag: ");
        sb.append(getVerlag());
        sb.append("\nISBN: ");
        sb.append(getIsbn());
        sb.append("\nVerfasser: ");
        sb.append(getVerfasser()+"\n");

        return sb.toString();
    }


    /**
     * Getter & Setter
     */
    public String getVerlag() {
        return verlag;
    }

    public void setVerlag(String verlag) {
        this.verlag = verlag;
    }

    public String getVerfasser() {
        return verfasser;
    }

    public void setVerfasser(String verfasser) {
        this.verfasser = verfasser;
    }

    public String getIsbn() {
        return isbn;
    }

    /**
     * Setter der Preufnummr (ISBN) des Buches enthaelt einen Algorythmus zuer Pruefung
     */


    /**
     * Vorbereitung der als String uebergebenen ISBN, Stricher entfernen und wegen der Laenge in einen Array aufteilen und mit den einzelnen Zeichen rechnen
     */
    public void setIsbn(String isbn)  {
        boolean isbn10 =false;
        boolean isbn13= false;
        isbn = isbn.replace("-", "");
        int[] isbnArr = new int[isbn.length()];
        for (int i = 0; i < isbn.length(); i++) {
            int tmp = Integer.valueOf(isbn.charAt(i));

            isbnArr[i] = tmp;
        }
/**
 *  ISBN 13 Pruefung
 */
        int sum13 = 0;
        for (int i = 1; i < isbnArr.length; i++) {
            if (i % 2 == 0) {
                sum13 += isbnArr[i - 1] * 3;
            } else {
                sum13 += isbnArr[i - 1];
            }
        }
        int lastDigit = sum13 % 10;
        int check = (10 - lastDigit) % 10;
        if (isbnArr[isbnArr.length - 1] == check) {
            this.isbn = isbn;
        } else {
           // System.out.print("isbn13 ungueltig!\n");
            isbn10=true;

        }
/**
 * ISBN 10 Pruefung
 */
        int sum10 = 0;
        for (int i = 1; i <= isbnArr.length; i++) {
            sum10 += i * isbnArr[i - 1];
        }
        if (sum10 % 11 == 0) {
            this.isbn = isbn;
        } else {
          //  System.out.print("isbn10 ungueltig!\n");
            isbn13=true;
        }
if(isbn13&&isbn10)
    throw new ISBNException("Isbn ungueltig! ");
        //this.isbn = isbn;
    }

    /**
     * Getter & Setter
     */
    public int getErscheinungsjahr() {
        return erscheinungsjahr;
    }

    public void setErscheinungsjahr(int erscheinungsjahr) {
        this.erscheinungsjahr = erscheinungsjahr;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
