
/**
 * this class-structure is used for holding parameters of motherboard
 * @author  Steblyansky Anton
 * @version 1.0
 * @since   2018-02-27
 */

public class Motherboard {
    private String manufacturer;
    private String productName;
    private String chipsettype;
    private String number;
    private String usb;
    private String hdmi;
    private String pcie;
    private String price;
    private String date;

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setChipsettype(String chipsettype) {
        this.chipsettype = chipsettype;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setUsb(String usb) {
        this.usb = usb;
    }

    public void setHdmi(String hdmi) {
        this.hdmi = hdmi;
    }

    public void setPcie(String pcie) {
        this.pcie = pcie;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValueVariableByName(String variableName){
        switch (variableName){
            case Constans.MANUFACTURER:{
                return manufacturer;
            }
            case Constans.PRODUCT_NAME:{
                return productName;
            }
            case Constans.CHIPSET_TYPE:{
                return chipsettype;
            }
            case Constans.NUMBER:{
                return number;
            }
            case Constans.USB:{
                return usb;
            }
            case Constans.HDMI:{
                return hdmi;
            }
            case Constans.PSIe:{
                return pcie;
            }
            case Constans.DATE:{
                return date;
            }
            case Constans.PRICE:{
                return price;
            }
        }

        return "";
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }
}