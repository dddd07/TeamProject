package TeamDTO;

public class Reservation {
    private int rNum;
    private int payment;
    private String tId;
    private int hNUm;
    private int dNum;

    public int getrNum() {
        return rNum;
    }

    public void setrNum(int rNum) {
        this.rNum = rNum;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public int gethNUm() {
        return hNUm;
    }

    public void sethNUm(int hNUm) {
        this.hNUm = hNUm;
    }

    public int getdNum() {
        return dNum;
    }

    public void setdNum(int dNum) {
        this.dNum = dNum;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "rNum=" + rNum +
                ", payment=" + payment +
                ", tId='" + tId + '\'' +
                ", hNUm=" + hNUm +
                ", dNum=" + dNum +
                '}';
    }
}
