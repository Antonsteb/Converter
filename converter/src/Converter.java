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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Converter {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XMLStreamException, ParseException {
        ArrayList<Motherboard> motherboards = new ArrayList<>();
        Motherboard motherboard = new Motherboard();
        String currentElement="";
        SimpleDateFormat dateFormat =  new SimpleDateFormat("dd.MM.yyyy");
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader parser = factory.createXMLStreamReader(new FileInputStream("motherboards.xml"));
        while(parser.hasNext()){
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT){
                currentElement = parser.getLocalName();
                    if (Constans.MOTHER_BOARD.equals(currentElement)) {
                        motherboard = new Motherboard();
                        motherboard.setNumber(Integer.parseInt(parser.getAttributeValue(null, Constans.NUMBER)));
                        motherboard.setManufacturer(parser.getAttributeValue(null, Constans.MANUFACTURER));
                    }
            }
            if (event == XMLStreamConstants.CHARACTERS){
                String currentText= new String(parser.getText());
                if (currentText.charAt(0)!=Constans.LINE_FEED)
                switch (currentElement){
                    case Constans.PRODUCT_NAME:{
                        motherboard.setProductName(currentText);
                    }break;
                    case Constans.CHIPSET_TYPE:{
                        motherboard.setChipsettype(currentText);
                    }break;
                    case Constans.USB:{
                        motherboard.setUsb(Integer.parseInt(currentText));
                    }break;
                    case Constans.HDMI:{
                        motherboard.setHdmi(Integer.parseInt(currentText));
                    }break;
                    case Constans.PSIe:{
                        motherboard.setPcie(Integer.parseInt(currentText));
                    }break;
                    case Constans.DATE:{
                        Date date = dateFormat.parse(currentText);
                        motherboard.setDate(date);
                    }break;
                    case Constans.PRICE:{
                        currentText = currentText.replace(',', '.');
                        motherboard.setPrice(Float.parseFloat(currentText));
                        motherboards.add(motherboard);
                    }break;
                }
            }
        }
        if (createJson(motherboards, "motherboards.json")){
            System.out.println("successful");
        } else {
            System.out.println("error create json");
        }
    }

    static boolean  createJson(ArrayList<Motherboard> motherboards, String jsonPath){
        Gson gson = new Gson();
        Convert convert = new Convert(motherboards);
        try  {
            String JSON  = gson.toJson(convert);
            FileWriter fileWriter = new FileWriter(jsonPath);
            fileWriter.write(JSON);
            fileWriter.close();
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    public static class Convert {
        ArrayList<Motherboard> motherboards;
        public Convert(ArrayList<Motherboard> motherboards) {
            this.motherboards = motherboards;
        }
    }
}
