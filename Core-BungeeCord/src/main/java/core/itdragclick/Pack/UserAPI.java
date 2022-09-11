package core.itdragclick.Pack;

public class UserAPI {

    private String uuid;
    private String name;
    private String guild;
    private String rank;
    public UserAPI(String uuid, String username, String guild, String rank) {
        this.uuid = uuid;
        this.name = username;
        this.guild = guild;
        this.rank = rank;
    }

    // UUID
    public String getuuid() {
        return uuid;
    }
    // USERNAME
    public String getusername() {
        return name;
    }
    public void setusername(String username){
        this.name = username;
    }
    // GUILD
    public String getguild() {
        return guild;
    }
    public void setguild(String guild2) {
        this.guild = guild2;
    }
    // RANK
    public String getrank() {
        return rank;
    }
    public void setrank(String rank2) {
        this.rank = rank2;
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
