import java.util.Date;

public class Motherboard {
    private String manufacturer;
    private String productName;
    private String chipsettype;
    private int number;
    private int usb;
    private int hdmi;
    private int pcie;
    private float price;
    Date date;

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setChipsettype(String chipsettype) {
        this.chipsettype = chipsettype;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setUsb(int usb) {
        this.usb = usb;
    }

    public void setHdmi(int hdmi) {
        this.hdmi = hdmi;
    }

    public void setPcie(int pcie) {
        this.pcie = pcie;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}