package TeamDTO;

public class Member {
    private String tId;
    private String tPw;
    private String tName;
    private int tAge;
    private String tPhone;
    private String tEmail;
    private int tPoint;

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String gettPw() {
        return tPw;
    }

    public void settPw(String tPw) {
        this.tPw = tPw;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public int gettAge() {
        return tAge;
    }

    public void settAge(int tAge) {
        this.tAge = tAge;
    }

    public String gettPhone() {
        return tPhone;
    }

    public void settPhone(String tPhone) {
        this.tPhone = tPhone;
    }

    public String gettEmail() {
        return tEmail;
    }

    public void settEmail(String tEmail) {
        this.tEmail = tEmail;
    }

    public int gettPoint() {
        return tPoint;
    }

    public void settPoint(int tPoint) {
        this.tPoint = tPoint;
    }

    @Override
    public String toString() {
        return  "아이디:" + tId + " [" +
                "비밀번호: " + tPw +
                ", 이름: " + tName +
                ", 나이: " + tAge +
                ", 연락처: " + tPhone +
                ", 이메일: " + tEmail +
                ", 포인트: " + tPoint + "point" +
                ']';
    }
}
