package sample;

import com.sun.javafx.css.StyleCache;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.stream.EventFilter;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import static javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING;

public class WikibookController {

//<AnchorPane prefHeight="200.0" prefWidth="200.0" />
    public TextField textField;
    public Button seachButton;
    public static String suchbegriff;
    public WebView webView;
    public String link = "http://de.wikibooks.org";
    public Label label_bearbeiter;
    public Label label_aenderung;
    public Button hinzufuegenButton;
    public Button sortierenButton;
    public Button loeschenButton;
    public Button speichernButton;
    public Button ladenButton;

    public ListView getSynonymeListe() {
        return synonymeListe;
    }

    public ListView synonymeListe;
    public Button synonymeSuchenButton;
    public ListView titelListe;
    public Button zurueckButton;
    public Button vorwaertsButton;
    public ComboBox <String> comboBoxBegriffe;
    public MenuBar menuBar;
    public MenuButton menuBtn;
    int index = -1;
    boolean sortierung=false;
    public ArrayList<String> begriffshistorie = new ArrayList<>();
    WebEngine engine;

    //TODO Exception Handeling Book not found

    public Zettelkasten getZk() {
        this.zk = Bibliothek.zettelkasten;
        return zk;
    }


    Zettelkasten zk;

    private static Wikibuch wikibuch;
    //TODO Fehler beim Zugriff Abfangen und Dialogboxen erstellen
    //TODO Teilaufgabe 3 Welche Methode gehoert in welche Klasse? (Wikibooks, Wikibuch, WikibuchHandler, WikibuchController)

    public void btnSearch(ActionEvent actionEvent) {
        suchbegriff = textField.getCharacters().toString();
        //System.out.println("from button: "+ suchbegriff);
        historie(suchbegriff);
        navigateBrowser(suchbegriff);


    }


    public void labelBearbeitung() {
        //TODO Aktualisierung nicht Bearbeiter/ Aenderung von args sondern neues Wikibuch bei reload der Seite
      try {
          label_bearbeiter.setText("Urheber: " + WikibooksHandler.getWikibuch().getUrheber());
      } catch (NullPointerException e){
            System.err.println("Seite nicht gefunden");
        }

    }

    public void labelAenderung() {
       try{ label_aenderung.setText("Letzte Aenderung: " + WikibooksHandler.getWikibuch().getTimestamp().toString());}
       catch (NullPointerException e){
           System.err.println("Seite nicht gefunden");
       }

    }


    public void tfSearchTerm(ActionEvent actionEvent) {

        suchbegriff = textField.getCharacters().toString();
        // System.out.println("from text: "+ suchbegriff);


        final EventHandler<KeyEvent> enterEventHandler = new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    historie(suchbegriff);
                    // suchbegriff = suchbegriff + "123";
                    //System.out.println("eventhandler" + suchbegriff);
                    navigateBrowser(suchbegriff);


                } else {
                    // System.out.println(suchbegriff+"    -suchbefriff");
                    // System.out.println(" test\n");


                }

            }
        };

        textField.setOnKeyPressed(enterEventHandler);


    } //ende search term

    public void initialize() {


        engine = webView.getEngine();
        engine.load(link + "/wiki/" + Bibliothek.eingabe);
    }

    public void navigateBrowser(String searchTerm) {
        File filed = new File("xmlfile.xml");
        filed.delete();
        engine.load(link + "/wiki/" + searchTerm);
        textField.clear();
        File file = new File("xmlfile.xml");
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
        } catch (Exception e) {

            e.printStackTrace();
        }
        // Wikibooks wikibooks = new Wikibooks();
        // wikibooks.getURL(searchTerm);
        labelBearbeitung();
        labelAenderung();
        showzk();
        suchbegriff = "";
    }

    private void showzk() {
        //TODO show sorted!
        titelListe.getItems().clear();
        getZk();
        for (int i = 0; i < zk.medienarr.size(); i++)
            titelListe.getItems().add(zk.medienarr.get(i).getTitel());

    }


    /**
     * Fuegt das angezeigte Buch dem Zettelkasten hinzu
     *
     * @param actionEvent
     */
    public void btnAdd(ActionEvent actionEvent) {
        getZk().addMedium(WikibooksHandler.getWikibuch());
        Bibliothek.setZettelkasten(zk);
        showzk();

    }

    /**
     * TODO btnSort
     * Soll den Zettelkasten sortieren, erst von A-Z und dann nach erneutem klicken von Z-A
     *
     * @param actionEvent
     */
    public void btnSort(ActionEvent actionEvent) {
        if(sortierung)
        {
           getZk().sort("absteigend");
            Bibliothek.setZettelkasten(zk);
            showzk();
            sortierung=false;

        }else{
        getZk().sort("aufsteigend");
            Bibliothek.setZettelkasten(zk);
            showzk();
            sortierung=true;

        }



    }

    /**
     * TODO btnDelete richtig verstanden???
     * keine Funktion und deshalb inaktiv geschaltet
     *
     * @param actionEvent
     */
    public void btnDelete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Zurzeit inaktiv!");

        alert.showAndWait();
        loeschenButton.setDisable(true);
    }

    /**
     * TODO btnSave richtig verstanden???
     * Serialisieren
     *
     * @param actionEvent
     */
    public void btnSave(ActionEvent actionEvent) {
        System.out.println("save: /n");
        BinaryPersistency bp = new BinaryPersistency();
        bp.save(getZk(), "serialiesierung");

    }

    /**
     * TODO btnLoad richtig verstanden???
     * Deserialisieren
     *
     * @param actionEvent
     */
    public void btnLoad(ActionEvent actionEvent) {
        System.out.println("load: /n");
        BinaryPersistency bp = new BinaryPersistency();
        bp.load("serialiesierung");
    }

    public void btnSucheSynonyme(ActionEvent actionEvent) {
        //TODO Synonyme alphabetisch sortieren
        String synonymeingabe = textField.getCharacters().toString();
        // textField.clear();
        findSynonym(synonymeingabe);


        System.out.println("Synonymtest: " + synonymeingabe);

    }

    /**
     * Reads InputStream's contents into String
     *
     * @param is - the InputStream
     * @return String representing is' contents
     * @throws IOException
     */
    public static String streamToString(InputStream is) throws IOException {
        String result = "";
        // other options: https://stackoverflow.com/questions/309424/read-convertan-inputstream-to-a-string
        try (Scanner s = new Scanner(is)) {
            s.useDelimiter("\\A");
            result = s.hasNext() ? s.next() : "";
            is.close();
        }
        return result;
    }

    public void findSynonym(String synonymeingabe) {
        historie(synonymeingabe);
        textField.clear();
        textField.setText(synonymeingabe);
        synonymeListe.getItems().clear();
        String BasisUrl = "http://api.corpora.uni-leipzig.de/ws/similarity/";
        String Corpus = "deu_news_2012_1M";
        String Anfragetyp = "/coocsim/";
        String Suchbegriff = synonymeingabe;
        String Parameter = "?minSim=0.1&limit=50";
        URL myURL;
        try {
            myURL = new URL(BasisUrl + Corpus + Anfragetyp + Suchbegriff + Parameter);
            JSONParser jsonParser = new JSONParser();
            String jsonResponse = streamToString(myURL.openStream());
            // den gelieferten String in ein Array parsen
            JSONArray jsonResponseArr = (JSONArray) jsonParser.parse(jsonResponse);
            // das erste Element in diesem Array
            JSONObject firstEntry = (JSONObject) jsonResponseArr.get(0);
            // aus dem Element den Container für das Synonym beschaffen
            JSONObject wordContainer = (JSONObject) firstEntry.get("word");
            // aus dem Container das Synonym beschaffen
            String synonym = (String) wordContainer.get("word");
            // ausgeben
            // System.out.println(synonym);
            System.out.println(System.lineSeparator());

            // alle abgefragten Synonyme
            for (Object el : jsonResponseArr) {
                wordContainer = (JSONObject) ((JSONObject) el).get("word");
                synonym = (String) wordContainer.get("word");
                //  System.out.println(synonym);
                synonymeListe.getItems().add(synonym);
            }
        } catch (IndexOutOfBoundsException e) {
            synonymeListe.getItems().add("<keine>");
            synonymeSuchenButton.setDisable(true);
            synonymeListe.setDisable(true);


        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Fehler beim Zugriff auf den Wortschatzserver!");

            alert.showAndWait();
            textField.clear();
            System.err.println("no synonym found");
        }
    }


    public void SynonymSelection(MouseEvent mouseEvent) {
        try {
            if (mouseEvent.getClickCount() == 2) {
                System.out.println("test");

                findSynonym(synonymeListe.getSelectionModel()
                        .getSelectedItem().toString());

            } else {
                System.out.println("test2");
            }
        } catch (Exception e) {

            System.err.println("Kein Synonym gewaehlt!");
        }


    }

    public void enableActions(KeyEvent keyEvent) {
        synonymeSuchenButton.setDisable(false);
        synonymeListe.setDisable(false);

    }

    public void btnBack(ActionEvent actionEvent) {
        index--;
        textField.setText(begriffshistorie.get(index));
        comboBoxBegriffe.setValue(begriffshistorie.get(index));
        if(index<1)
            zurueckButton.setDisable(true);
        if(index+1<begriffshistorie.size()){
            vorwaertsButton.setDisable(false);
        }
        engine.load(link + "/wiki/" +begriffshistorie.get(index));
    }

    public void btnForward(ActionEvent actionEvent) {
        index++;
        textField.setText(begriffshistorie.get(index));
        comboBoxBegriffe.setValue(begriffshistorie.get(index));
        if(index+1==begriffshistorie.size()){
            vorwaertsButton.setDisable(true);}
        if(index>0){
                zurueckButton.setDisable(false);}

        //engine = webView.getEngine();
        engine.load(link + "/wiki/" +begriffshistorie.get(index));
    }

    private void historie(String begriff) {
            vorwaertsButton.setDisable(true);
    for (int i=1; i<begriffshistorie.size();i++){
       // System.out.println(i);
    if(i>index){
       // System.out.println(i + "index: " + index + "   bgh:   " +begriffshistorie.get(i));
        begriffshistorie.subList(i, begriffshistorie.size()).clear();
       comboBoxBegriffe.getItems().remove(i,comboBoxBegriffe.getItems().size());
                                    }
}
    index++;
        begriffshistorie.add(begriff);

        comboBoxBegriffe.getItems().add(begriff);
        comboBoxBegriffe.setValue(begriff);
        comboBoxBegriffe.getSelectionModel().select(index);

       // System.out.println("historie  + lenghst: " + begriff + "  " + begriffshistorie.size());

        if(index>0){
            zurueckButton.setDisable(false);}


}

    public void comboBoxAction(ActionEvent actionEvent) {
      String tmp =  comboBoxBegriffe.getValue();
      //TODO Index setzen, Buttons deaktivieren
     index = begriffshistorie.indexOf(tmp);
      textField.setText(tmp);
      engine.load(link + "/wiki/" +tmp);
                }


    public void menuAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText("Alle redaktionellen Inhalte stammen von den Internetseiten der Projekte Wikibooks und Wortschatz.");
        alert.setContentText("Die von Wikibooks bezogenen Inhalte unterliegen seit dem 22. Juni 2009 unter der Lizenz CC-BY-SA 3.0\n" +
                "Unported zur Verfügung. Eine deutschsprachige Dokumentation für Weiternutzer findet man in den\n" +
                "Nutzungsbedingungen der Wikimedia Foundation. Für alle Inhalte von Wikibooks galt bis zum 22. Juni\n" +
                "2009 standardmäßig die GNU FDL (GNU Free Documentation License, engl. für GNU-Lizenz für freie\n" +
                "Dokumentation). Der Text der GNU FDL ist unter\n" +
                "http://de.wikipedia.org/wiki/Wikipedia:GNU_Free_Documentation_License verfügbar.\n" +
                "Die von Wortschatz (http://wortschatz.uni-leipzig.de/) bezogenen Inhalte sind urheberrechtlich geschützt.\n" +
                "Sie werden hier für wissenschaftliche Zwecke eingesetzt und dürfen darüber hinaus in keiner Weise\n" +
                "genutzt werden.\n" +
                "Dieses Programm ist nur zur Nutzung durch den Programmierer selbst gedacht. Dieses Programm dient\n" +
                "der Demonstration und dem Erlernen von Prinzipien der Programmierung mit Java. Eine Verwendung\n" +
                "des Programms für andere Zwecke verletzt möglicherweise die Urheberrechte der Originalautoren der\n" +
                "redaktionellen Inhalte und ist daher untersagt.\n");

        alert.showAndWait();
    }

    public void traversieren(KeyEvent keyEvent) {


            }



}



