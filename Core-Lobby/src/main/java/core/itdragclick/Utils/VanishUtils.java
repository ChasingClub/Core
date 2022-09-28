package core.itdragclick.Utils;

public class VanishUtils {

    private final String uuid;
    private final String name;
    private final Boolean isVanish;
    public VanishUtils(String uuid, String username, Boolean vanish) {
        this.uuid = uuid;
        this.name = username;
        this.isVanish = vanish;
    }

    // UUID
    public String getuuid() {
        return uuid;
    }
    // USERNAME
    public String getusername() {
        return name;
    }
    // USERNAME
    public Boolean getisVanish() {
        return isVanish;
    }
}
