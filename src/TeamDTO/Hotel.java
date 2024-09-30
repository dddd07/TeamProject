package TeamDTO;

public class Hotel {

    private int hNum;
    private String hName;
    private int hPrice;
    private String hLocation;

    public int gethNum() {
        return hNum;
    }

    public void sethNum(int hNum) {
        this.hNum = hNum;
    }

    public String gethName() {
        return hName;
    }

    public void sethName(String hName) {
        this.hName = hName;
    }

    public int gethPrice() {
        return hPrice;
    }

    public void sethPrice(int hPrice) {
        this.hPrice = hPrice;
    }

    public String gethLocation() {
        return hLocation;
    }

    public void sethLocation(String hLocation) {
        this.hLocation = hLocation;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hNum=" + hNum +
                ", hName='" + hName + '\'' +
                ", hPrice=" + hPrice +
                ", hLocation='" + hLocation + '\'' +
                '}';
    }
}
