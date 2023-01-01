package core.itdragclick.Utils;

public class VanishAPI {
    private final String uuid;
    private final String name;
    public VanishAPI(String uuid, String username) {
        this.uuid = uuid;
        this.name = username;
    }
    // UUID
    public String GetUUID() {
        return uuid;
    }
    // USERNAME
    public String GetUsername() {
        return name;
    }
}
