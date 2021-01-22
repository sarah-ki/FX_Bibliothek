package sample;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

// Name Sarah Franke
// MN 17617
//Datum der Erstellung: Novemeber
//Datum der Letzten Aenderung: 01/06/2020

public class WikibooksHandler implements ContentHandler {
    /**
     * Objekt vom Typ Wikibuch, welches nach und nach it Informationen über den parser aus dem XML Dokument gefüllt wird
     */
    private static Wikibuch wikibuch;
    /**
     * sollte sich noch ein benutzer fnden, welcher kein urheber ist, so verhindert diese variable, dass der tag gezaehlt wird
     */
    boolean contributor = false;

    /**
     * enthaelt den Inhalt des Tags
     */
    String currentValue;

    /**
     *  Wird genutzt um einen Datentext über das Medium zu speichern, da der SAX parser eventuell nicht den gesamten Text auf einmal verarbeiten kann
     *  Quelle: Vorlesung/ Dokument auf Ilias
     */
    StringBuilder currentText = new StringBuilder();


   // private Wikibuch wikibuch;

    /**
     * Titelstring speichert den Titel des mediums zwischen, damit der Benutzer es nicht mehrmals durch den Aufruf von Scanner in suchbegriff(); eingeben muss
     */
    String titel = WikibookController.suchbegriff;

    public static Wikibuch getWikibuch() {
        return wikibuch;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getTitel() {
       this.titel=titel;
        return titel;
    }

    /**
     * startDocument() erstellt fuer jedes xml Wikidokument ein Objekt vom Typ Wikibook
     * mit dem vom Benutzer eingegebenen Namen und der URL, welcher aus der Export URL und
     * dem Namen zusammengesetzt besteht.
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        wikibuch = new Wikibuch(suchbegriff(), readURL());

    }

    /**
     * Die Methode suchbegriff behandelt den UserInput und formt ihn in eine gültige URL um
     * @return edit_suchbegriff ist ein String, so geformt dass er als URL durchgehen kann mit Unterstrichen statt Leerzeichen und deutschen Umlauten durch Utf-8 Hex Zahlen ersetzt.
     */
    public String suchbegriff(){
       // System.out.print("Suche nach: ");
       // Scanner in = new Scanner(System.in);
        String suchbegriff= WikibookController.suchbegriff;//in.nextLine();
        String ausgabeTitel = suchbegriff.replaceAll("_", " ");
        suchbegriff.trim();
        String edit_suchbegriff= suchbegriff.replaceAll("\\s+", "_");
        edit_suchbegriff= edit_suchbegriff.replaceAll("ü", "%C3%BC");
        edit_suchbegriff= edit_suchbegriff.replaceAll("ä", "%C3%A4");
        edit_suchbegriff= edit_suchbegriff.replaceAll("ö", "%C3%B6");
        edit_suchbegriff= edit_suchbegriff.replaceAll("ß", "%C3%9F");
        edit_suchbegriff= edit_suchbegriff.replaceAll("Ü", "%C3%9C");
        edit_suchbegriff= edit_suchbegriff.replaceAll("Ö", "%C3%96");
        edit_suchbegriff= edit_suchbegriff.replaceAll("Ä", "%C3%84");


        titel=edit_suchbegriff;
        return ausgabeTitel;
    }

    /**
     * nimmt den zuvor durch suchbegriff(); ermittelten titel und setzt ihn mit dem link zu einer ULR
     * zusammen, erstellt ein xmlfile.xml mit dem Inhalt des Exportlinks
     * @return gibt den zusammengesetzten Link zurück
     */

    public String readURL() {
        String link = "https://de.wikibooks.org/wiki/Spezial:Exportieren/"+titel;

        try {
            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            connection.setDoInput(true);
            InputStream inStream = connection.getInputStream();
            BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
            File myfile = new File("xmlfile.xml");
            BufferedWriter writer = new BufferedWriter(new FileWriter(myfile));
            String line = "";
            while ((line = input.readLine()) != null) {
                writer.write(line + "\n");
                //System.out.println(line);
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println(e.toString()+"\nungueltig! Bitte erneut versuchen");

        }
        return link;
    }

    /**
     * Nachdem das xml Dokument zuende geparsed hat, wird mit Hilfe der calculate Repraesentation Methode des Objektes eine Ausgabe auf der Konsole erzeugt
     * @throws SAXException
     */
    @Override
    public void endDocument() throws SAXException {

        //System.out.println(wikibuch.calculateRepresentation());



    }

    /**
     * Checkt, ob es sich um einen Urheber tag handelt, damit im spaeteren Verlauf festgestellt werden kann ob es sich um ip oder benutzernamen handelt
     *
     * Ausserdem wird der stringBuilder fuer den Datentext geleert, da die Aufnahme der Informationen aus dem Block beginnt
     * @param uri
     * @param localName
     * @param qName
     * @param atts
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        if(localName.equals("contributor")) {
          //  System.out.print("Urheber: ");
            contributor = true;
        }
        if(localName.equals("text")){
            currentText.setLength(0);
        }


    }

    /**
     * Aendert den Status der contributor Variablen, da nach einem close contributor tag kein urheber mehr gefunden werden kann
     *
     * Checkt ob es sich um einen Benutzer oder eine Bearbeitung durch ip beim Urheber handelt und uebergibt dem Wikibuch diese Information
     *
     * Wird ein Timestamp entdeckt wird der Inhalt des Tags an die Methode timestampAusgabe() uebergeben
     *
     * Wird das Ende des Informationsblocks erreicht mit dem text tag, so werden die findeX(); Methoden aufgerufen um Regal und Kapitel herauszufiltern
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(localName.equals("contributor")){
            contributor=false;
        // System.out.println(":/contributor");
        }
        if(localName.equals("ip")&&contributor==true){
            wikibuch.setUrheber(currentValue);
           // System.out.println(currentValue);
        }
        if(localName.equals("username")&&contributor==true){
            wikibuch.setUrheber(currentValue);
         //   System.out.println(currentValue);
        }
        if(localName.equals("timestamp")){
           // System.out.println(currentValue);
            timestampAusgabe(currentValue);
        }
        if (localName.equals("text")){
        final String content= currentText.toString().trim();
        findeKapitel(content);
        findeRegal(content);
        System.out.println("--------------------");
        //System.out.println(content);
            currentText.setLength(0);
}
    }

    /**
     * Methode schneidet die Informationen ueber das Regal aus dem Informationsblock heraus und uebergibt sie zum formatieren an toFormat
     * Uebergibt die formatierten Daten an das Objekt Wikibuch
     * @param content
     */
    private void findeRegal(String content) {
String regal = content.substring(content.indexOf("{{Regal|"),content.indexOf("}}"));
//System.out.println("Regal:    "+toFormat(regal));
wikibuch.setRegal(toFormat(regal));
    }

    /**
     * s. findeRegal(), nur statt Regal wird mit Kapiteln gearbeitet
     * @param content
     */
    private void findeKapitel(String content) {
//System.out.println();
        String[] alleKapitel =content.split("== ");
        String[] wikibuchstring = new String[alleKapitel.length];


       // System.out.println(alleKapitel[0]);
        for (int i = 1; i < alleKapitel.length; i++) {
            //System.out.println(xmlString.indexOf("<"+tag+">"));
            // System.out.println(xmlString.indexOf("</"+tag+">"));
           // System.out.println("Kapitel gefunden:   ");
           // System.out.println(alleKapitel[i]);
String chapter = alleKapitel[i].substring(0, alleKapitel[i].indexOf(" =="));
            // allePersonen[i].substring(allePersonen[i].indexOf("<"+tag+">"),((allePersonen[i].ind
 if (chapter.contains("|")){
        //    System.out.println(i+". Kapitel:" + toFormat(chapter));
            wikibuchstring[i]=i+". Kapitel:" + toFormat(chapter);


 }
 else{
    // System.out.println(i+". Kapitel: " + chapter);
     wikibuchstring[i]=i+". Kapitel:" + chapter;
 }
           // System.out.println("--------Kapitel--------");

        }


wikibuch.setKapitel(wikibuchstring);
    }

    /**
     * Methode um Informationen von unschoenen Zeichen und überfluessigen Worten zu befreien
     * @param edit zu formatierender String
     * @return String edit, als formatierter String
     */
    private String toFormat(String edit) {
        edit= edit.substring(edit.indexOf("|")+1);
        edit= edit.replaceAll("]", "");
        if(edit.contains("=")){
            edit=edit.substring(edit.indexOf("=")+1);
        }
      //  System.out.println("formatted:   "+ edit);
        return edit;


    }

    /**
     * Aendert den String, welcher das Datum in internationaler Zeit enthaelt in aktuelle Zeit um mit Hilfe
     * von SimpleDateFormat
     * @param currentValue enthaelt das Datum der Letzten Bearbeitung des Wikibuchs als String
     */
    private void timestampAusgabe(String currentValue) {
        //TODO Welche version? Wie ausgeben als ein Datum, wenn die Ausgabe aufgeteilt werden muss sont probleme bei tagesspruengen; Idee: Date zurueck in STring umwandeln und entsprechend ausgeben, allerdings kommt es mir unschoen vor String to Date zu parsen nur u es wieder zurueck in einen String zu wandeln

        String[] timestamp = new String[2];
        timestamp= currentValue.split("T");
        //   System.out.println(timestamp[0]+"   |    "+timestamp[1]+"   |    ");
        timestamp[1]= timestamp[1].replace("Z","");
       // System.out.println(timestamp[0]+"   |    "+timestamp[1]+"   |    ");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date= sdf.parse(timestamp[0]+" "+timestamp[1]);
            sdf.setTimeZone(TimeZone.getDefault());
            /**
             * Um ein "um" zwischen Datum und Zeit zu setzen, wieder in einen String umwandeln und diesen bearbeiten
             */
         //   System.out.println("Letzte Änderung: "+ sdf.format(date)    );
            wikibuch.setTimestamp(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * speichert den geparsden Ihalt in der Variable currentValue und den Text fuer den grossen Datenblock mit Hilfe eines StrinBuilders in currentText
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        currentValue = new String(ch, start, length);
        currentText.append(ch, start, length);
    }
    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {

    }
    @Override
    public void processingInstruction(String target, String data) throws SAXException {

    }
    @Override
    public void skippedEntity(String name) throws SAXException {

    }
    @Override
    public void setDocumentLocator(Locator locator) {
    }
    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {

    }
    @Override
    public void endPrefixMapping(String prefix) throws SAXException {

    }
}
