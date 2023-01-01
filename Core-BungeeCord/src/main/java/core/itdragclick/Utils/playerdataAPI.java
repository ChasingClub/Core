package core.itdragclick.Utils;

public class playerdataAPI {

    private String uuid;
    private String name;
    private String ip;
    private String country;
    private String lastlogin;
    private String lastlogout;
    private String firstjoin;
    public playerdataAPI(String uuid, String name, String ip, String country, String lastlogin, String lastlogout, String firstjoin) {
        this.uuid = uuid;
        this.name = name;
        this.ip = ip;
        this.country = country;
        this.lastlogin = lastlogin;
        this.lastlogout = lastlogout;
        this.firstjoin = firstjoin;
    }

    // UUID
    public String GetUUID() {
        return uuid;
    }
    // NAME
    public String GetName() {
        return name;
    }
    // IP
    public String GetIP() {
        return ip;
    }
    // COUNTRY
    public String GetCountry() {
        return country;
    }
    // LASTLOGIN
    public String GetLastLogin() {
        return lastlogin;
    }
    // LASTLOGOUT
    public String GetLastLogout() {
        return lastlogout;
    }
    // FIRSTJOIN
    public String GetFirstJoin() {
        return firstjoin;
    }
}
