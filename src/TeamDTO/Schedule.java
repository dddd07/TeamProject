package TeamDTO;

public class Schedule {
    private int dNum;
    private String dDate;
    private int sNUm;
    private int amount;

    public int getdNum() {
        return dNum;
    }

    public void setdNum(int dNum) {
        this.dNum = dNum;
    }

    public String getdDate() {
        return dDate;
    }

    public void setdDate(String dDate) {
        this.dDate = dDate;
    }

    public int getsNUm() {
        return sNUm;
    }

    public void setsNUm(int sNUm) {
        this.sNUm = sNUm;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "dNum=" + dNum +
                ", dDate='" + dDate + '\'' +
                ", sNUm=" + sNUm +
                ", amount=" + amount +
                '}';
    }
}
