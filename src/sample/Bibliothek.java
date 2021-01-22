package sample;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.InputEvent;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING;

// Name Sarah Franke
// MN 17617
//Datum der Erstellung: ?
//Datum der Letzten Aenderung: 01/06/2020


public class Bibliothek extends Application {

    public static String eingabe = "";

    public static Zettelkasten getZettelkasten() {
        return zettelkasten;
    }

    public static void setZettelkasten(Zettelkasten zettelkasten) {
        Bibliothek.zettelkasten = zettelkasten;
    }

    public static Zettelkasten zettelkasten;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(Bibliothek.class.getResource("sample.fxml"));
        Parent root =  loader.load();
        primaryStage.setTitle("Mein Wikibooks-Browser");
        Scene scene = new Scene(root, 0, 0);
        primaryStage.setScene(scene);//weite hoehe

        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(500);
        primaryStage.getIcons().add(new Image("1200px-Wikibooks.png"));
        primaryStage.show();
       primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.TAB) {
                    event.consume();
                    System.out.println("test");



                }
            }
        });
    }


    public static void main(String[] args)  {

    /**Testfaelle fuer Variablen: Edit Configurations main: Bibliothek, Program arguments: Die Sprache der Mathematik, Die Kunst glücklich zu leben, Java Standard
    *
    * Achtung! Sollte beim ausführen eines der Bücher ein FATAL ERROR auftreten, bitte einfach nochmal genauso ausführen und dieser sollte verschwinden. Da er nur manchmal und gefühlt zufaellig auftritt habe ich nicht herausfinden, oder beheben können woher dieser kommt.
    *
    */


        zettelkasten = new Zettelkasten();
        zettelkasten.addMedium(new CD("Live At Wembley", "Queen", "Parlophone (EMI)"));
        zettelkasten.addMedium(new CD ("C",  "Apple (Bea (EMI))", "The Beatles"));
        zettelkasten.addMedium(new Zeitschrift ("Der Spiegel",  "0038-7452", 54, 6));
        zettelkasten.addMedium(new Buch("Duden 02. Die deutsche Rechtschreibung", 2004, "Bibliographisches Institut, Mannheim. ", "3-411-04013-0", "-"));
        zettelkasten.addMedium(new OnlineMedium("Hochschule Stralsund", "http://www.hochschule-stralsund.de"));
        zettelkasten.addMedium(new CD("Test", "Queen", "Parlophone (EMI)"));
        zettelkasten.addMedium(new CD("Test", "Rjksd", "Parlophone (EMI)"));
        zettelkasten.addMedium(new CD("Test", "Eins", "Parlophone (EMI)"));
        zettelkasten.addMedium(new Zeitschrift ("Test",  "0038-7452", 54, 6));
        zettelkasten.addMedium(new CD("Test", "Zwei", "Parlophone (EMI)"));
        zettelkasten.addMedium(new CD("Test", "Drei", "Parlophone (EMI)"));

        /**
         * Nimmt das Argument, welches beim Programmstart uebergeben wurde und wandelt es um, sodass es im WikibooksHandler genutzt werden kann
         */
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++){
            sb.append(args[i]);
            sb.append("_");
        }
        sb.deleteCharAt(sb.length()-1);
        String arguments=sb.toString();
        eingabe=arguments;

        launch(args);
      /*  File file = new File("xmlfile.xml");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
      //  System.out.println("--------------------------- ");
        try {

            // XMLReader erzeugen
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            xmlReader.setFeature(FEATURE_SECURE_PROCESSING, true);
            // PersonenContentHandler wird übergeben
            xmlReader.setContentHandler(new WikibooksHandler());

            // Pfad zur XML Datei
            FileReader reader = new FileReader("xmlfile.xml");
            InputSource inputSource = new InputSource(reader);
            // Parsen wird gestartet
            xmlReader.parse(inputSource);

         //   WikibooksHandler wikibooksHandler = new WikibooksHandler();

            /**
             * Wikibuch wird der Liste der Medien im Zettelkasten angefuegt und der Zettelkasten wird mit Hilfe iener Schleife ausgegeben

            Wikibuch wikibuch = WikibooksHandler.getWikibuch();

           // wikibuch.setTitel(arguments);

            zettelkasten.addMedium(wikibuch);



           //System.out.println("main:    ");*/
            System.out.println("Ausgabe aller Medien: ");
            for (int i = 0; i < zettelkasten.medienarr.size(); i++) {
                System.out.println(zettelkasten.medienarr.get(i).calculateRepresentation());
                System.out.println("---------------------------------");
            }
            /*
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
*/

        //TODO   WikiBooks Bücher anhand eines Schlagwortes zu suchen.
        //TODO  gleichzeitige Ausgabe der Synonyme des eingegebenen Suchbegriffs


    }
}
