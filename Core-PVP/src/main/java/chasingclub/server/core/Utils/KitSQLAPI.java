package chasingclub.server.core.Utils;

public class KitSQLAPI {
    private final String uuid;
    private final String name;
    private final int kit;
    public KitSQLAPI(String uuid, String username, int kit) {
        this.uuid = uuid;
        this.name = username;
        this.kit = kit;
    }
    // UUID
    public String GetUUID() {
        return uuid;
    }
    // USERNAME
    public String GetUsername() {
        return name;
    }
    // KIT
    public int GetKit() {
        return kit;
    }
}
