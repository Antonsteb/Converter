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
                    if ("motherboard".equals(currentElement)) {
                        motherboard = new Motherboard();
                        motherboard.setNumber(Integer.parseInt(parser.getAttributeValue(null, "number")));
                        motherboard.setManufacturer(parser.getAttributeValue(null, "manufacturer"));
                    }
            }
            if (event == XMLStreamConstants.CHARACTERS){
                String str= new String(parser.getText());
                if (str.charAt(0)!=10)
                switch (currentElement){
                    case "productname":{
                        motherboard.setProductName(str);
                    }break;
                    case "chipsettype":{
                        motherboard.setChipsettype(str);
                    }break;
                    case "usb":{
                        motherboard.setUsb(Integer.parseInt(str));
                    }break;
                    case "hdmi":{
                        motherboard.setHdmi(Integer.parseInt(str));
                    }break;
                    case "pcie":{
                        motherboard.setPcie(Integer.parseInt(str));
                    }break;
                    case "date":{
                        Date date = dateFormat.parse(str);
                        motherboard.setDate(date);
                    }break;
                    case "price":{
                        str = str.replace(',', '.');
                        motherboard.setPrice(Float.parseFloat(str));
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
            FileWriter fileWriter = new FileWriter(jsonPath);
            String JSON  = gson.toJson(convert);
            fileWriter.write(JSON);
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
