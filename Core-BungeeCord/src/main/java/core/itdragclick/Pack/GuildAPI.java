package core.itdragclick.Pack;

public class GuildAPI {

    private String name;
    private String displayname;
    private String owner;
    public GuildAPI(String name, String displayname, String owner) {
        this.name = name;
        this.displayname = displayname;
        this.owner = owner;
    }

    public String getowner() {
        return owner;
    }

    public void setowner(String uuid) {
        this.owner = uuid;
    }

    public String getdisplayname() {
        return displayname;
    }

    public void setdisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getname() {
        return name;
    }

//    public void setKills(int kills) {
//        this.kills = kills;
//    }

//    public long getBlocksBroken() {
//        return blocksBroken;
//    }

//    public void setBlocksBroken(long blocksBroken) {
//        this.blocksBroken = blocksBroken;
//    }

//    public double getBalance() {
//        return balance;
//    }
//
//    public void setBalance(double balance) {
//        this.balance = balance;
//    }
//
//    public Date getLastLogin() {
//        return lastLogin;
//    }
//
//    public void setLastLogin(Date lastLogin) {
//        this.lastLogin = lastLogin;
//    }
//
//    public Date getLastLogout() {
//        return lastLogout;
//    }
//
//    public void setLastLogout(Date lastLogout) {
//        this.lastLogout = lastLogout;
//    }
}
