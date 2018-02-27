import com.google.gson.Gson;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * The main class in which the conversion is performed
 *
 * @author  Steblyansky Anton
 * @version 1.0
 * @since   2018-02-27
 */

public class Converter {

    /**
     * The input method to which the path to xml and json should be passed, as well as the parameters for comparison
     */

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XMLStreamException, ParseException {
        Filter filter = null;
        ArrayList<Motherboard> motherboards = new ArrayList<>();
        Motherboard motherboard = new Motherboard();
        String currentElement = "";
        String xmlPath;
        String jsonPath;
        try {
            xmlPath = args[0];
            jsonPath = args[1];
            if (args.length>2){
                filter = new Filter(args[2],args[3],args[4]);
            }
        }catch (Exception e){
            System.out.println("error reading parameters\n Check the correctness of the input parameters");
            return;
        }
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader parser = factory.createXMLStreamReader(new FileInputStream(xmlPath));
            while (parser.hasNext()) {
                int event = parser.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    currentElement = parser.getLocalName();
                    if (Constans.MOTHER_BOARD.equals(currentElement)) {
                        motherboard = new Motherboard();
                        parseAttributes(motherboard, parser);
                    }
                }
                if (event == XMLStreamConstants.CHARACTERS) {
                    if (parseParameters(currentElement, motherboard, parser)) {
                        if (filter == null) motherboards.add(motherboard);
                        else if (filter.filtration(motherboard))
                            motherboards.add(motherboard);
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Error read file \n Check path to file, and file name or check xml-file structure");
            return;
        }
        if (createJson(motherboards, jsonPath)){
            System.out.println("successful");
        } else {
            System.out.println("error create json");
        }

    }

    private static void parseAttributes(Motherboard motherboard, XMLStreamReader parser){
        try {
            motherboard.setNumber(parser.getAttributeValue(null, Constans.NUMBER));
        }catch (Exception e){
            System.out.println("Error parsing attribute number \n Check the structure of xml file");
        }
        try {
            motherboard.setManufacturer(parser.getAttributeValue(null, Constans.MANUFACTURER));
        }catch (Exception e){
            System.out.println("Error parsing attribute manufacture \n Check the structure of xml file");
        }

    }

    private static boolean parseParameters(String currentElement, Motherboard motherboard, XMLStreamReader parser){
        String currentText = "";
        try {
            currentText= parser.getText();
        }catch (Exception e){
            System.out.println("Error parsing parameters \n Check the structure of xml file");
        }
        if (currentText.charAt(0)!=Constans.LINE_FEED)
            switch (currentElement){
                case Constans.PRODUCT_NAME:{
                    motherboard.setProductName(currentText);
                }break;
                case Constans.CHIPSET_TYPE:{
                    motherboard.setChipsettype(currentText);
                }break;
                case Constans.USB:{
                    motherboard.setUsb(currentText);
                }break;
                case Constans.HDMI:{
                    motherboard.setHdmi(currentText);
                }break;
                case Constans.PSIe:{
                    motherboard.setPcie(currentText);
                }break;
                case Constans.DATE:{
                    motherboard.setDate(currentText);
                }break;
                case Constans.PRICE:{
                    motherboard.setPrice(currentText);
                    return true;
                }
            }
            return false;
    }

   private static boolean  createJson(ArrayList<Motherboard> motherboards, String jsonPath){
        Gson gson = new Gson();
        Convert convert = new Convert(motherboards);
        try  {
            String JSON  = gson.toJson(convert);
            FileWriter fileWriter = new FileWriter(jsonPath);
            fileWriter.write(JSON);
            fileWriter.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static class Convert {
        ArrayList<Motherboard> motherboards;
        Convert(ArrayList<Motherboard> motherboards) {
            this.motherboards = motherboards;
        }
    }
}
