package chasingclub.server.core.Utils;

public class DuelStatsSQLAPI {
    private final String uuid;
    private final int kills;
    private final int deaths;
    private final int wins;
    private final int loses;
    public DuelStatsSQLAPI(String uuid, int kills, int deaths, int wins, int loses) {
        this.uuid = uuid;
        this.kills = kills;
        this.deaths = deaths;
        this.wins = wins;
        this.loses = loses;
    }
    // UUID
    public String GetUUID() {
        return uuid;
    }
    // Kills
    public int GetKills() {
        return kills;
    }
    // Deaths
    public int GetDeaths() {
        return deaths;
    }
    // Wins
    public int GetWins() {
        return wins;
    }
    // Loses
    public int GetLoses() {
        return loses;
    }
}
