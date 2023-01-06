package core.itdragclick.Utils;

public class WhitelistAPI {

    private final String uuid;
    private final String name;
    public WhitelistAPI(String uuid, String username) {
        this.uuid = uuid;
        this.name = username;
    }

    // UUID
    public String getuuid() {
        return uuid;
    }
    // USERNAME
    public String getusername() {
        return name;
    }
}
