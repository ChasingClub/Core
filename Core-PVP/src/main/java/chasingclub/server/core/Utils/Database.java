package chasingclub.server.core.Utils;

import chasingclub.server.core.Core;
import org.bukkit.entity.Player;

import java.sql.*;

public class Database {
    public Core plugin;
    public Database(Core plugin){
        this.plugin = plugin;
    }
    public static Connection connection;
    public static Connection connection2;

    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://"+plugin.SQL_ip+"/"+plugin.SQL_database;
        String user = plugin.SQL_username;
        String password = plugin.SQL_password;
        Connection connection = DriverManager.getConnection(url, user, password);
        Database.connection = connection;

        return connection;
    }
    public void initializeDatabase() throws SQLException {

        Statement statement = getConnection().createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS vanish (uuid varchar(36), username varchar(16),PRIMARY KEY (uuid))");
        statement.close();
    }
    public Connection getConnection2() throws SQLException {
        String url = "jdbc:mysql://"+plugin.SQL_ip2+"/"+plugin.SQL_database2;
        String user = plugin.SQL_username2;
        String password = plugin.SQL_password2;
        Connection connection2 = DriverManager.getConnection(url, user, password);
        Database.connection2 = connection2;

        return connection2;
    }
    public void initializeDatabase2() throws SQLException {

        Statement statement = getConnection2().createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS kit (UUID varchar(36), Name varchar(16), Kit int(3), PRIMARY KEY (UUID))");
        statement.close();
    }
    public void initializeDatabase3() throws SQLException {

        Statement statement = getConnection2().createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS duel_stats (uuid varchar(36), kills bigint(255), deaths bigint(255), wins bigint(255), loses bigint(255), PRIMARY KEY (uuid))");
        statement.close();
    }
    public static Connection connection3;
    public Connection getConnection3() throws SQLException {
        String url = "jdbc:mysql://"+plugin.SQL_ip2+"/"+"s2_inventory_slot";
        String user = "u2_C3wNv28Udy";
        String password = "NKcQqhB.kUdYBRSJS8hSy6w+";
        Connection connection3 = DriverManager.getConnection(url, user, password);
        Database.connection3 = connection3;

        return connection3;
    }
    public void initializeDatabase4() throws SQLException {

        Statement statement = getConnection3().createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS default_kit (UUID varchar(36), first varchar(36), second varchar(36), third varchar(36), fourth varchar(36), fifth varchar(36), sixth varchar(36), seventh varchar(36), eighth varchar(36), ninth varchar(36), offhand varchar(36), PRIMARY KEY (UUID))");
        statement.execute("CREATE TABLE IF NOT EXISTS trident_kit (UUID varchar(36), first varchar(36), second varchar(36), third varchar(36), fourth varchar(36), fifth varchar(36), sixth varchar(36), seventh varchar(36), eighth varchar(36), ninth varchar(36), offhand varchar(36), PRIMARY KEY (UUID))");
        statement.execute("CREATE TABLE IF NOT EXISTS viking_kit (UUID varchar(36), first varchar(36), second varchar(36), third varchar(36), fourth varchar(36), fifth varchar(36), sixth varchar(36), seventh varchar(36), eighth varchar(36), ninth varchar(36), offhand varchar(36), PRIMARY KEY (UUID))");
        statement.execute("CREATE TABLE IF NOT EXISTS archer_kit (UUID varchar(36), first varchar(36), second varchar(36), third varchar(36), fourth varchar(36), fifth varchar(36), sixth varchar(36), seventh varchar(36), eighth varchar(36), ninth varchar(36), offhand varchar(36), PRIMARY KEY (UUID))");
        statement.execute("CREATE TABLE IF NOT EXISTS netherite_stack (UUID varchar(36), first varchar(36), second varchar(36), third varchar(36), fourth varchar(36), fifth varchar(36), sixth varchar(36), seventh varchar(36), eighth varchar(36), ninth varchar(36), offhand varchar(36), PRIMARY KEY (UUID))");
        statement.execute("CREATE TABLE IF NOT EXISTS classic_sword (UUID varchar(36), first varchar(36), second varchar(36), third varchar(36), fourth varchar(36), fifth varchar(36), sixth varchar(36), seventh varchar(36), eighth varchar(36), ninth varchar(36), offhand varchar(36), PRIMARY KEY (UUID))");
        statement.close();
    }
    public static void DeleteVanish(Player p)throws SQLException{
        PreparedStatement ps = connection.prepareStatement("DELETE FROM vanish WHERE uuid = ?");
        ps.setString(1, p.getUniqueId().toString());
        ps.executeUpdate();
        ps.close();
    }
    public static void AddVanish(Player p)throws SQLException{
        PreparedStatement ps = connection.prepareStatement("INSERT IGNORE INTO vanish(uuid,username) VALUES (?,?)");
        ps.setString(1, p.getUniqueId().toString());
        ps.setString(2, p.getName());
        ps.executeUpdate();
        ps.close();
    }
    public static void AddDuel(Player p)throws SQLException{
        PreparedStatement ps = connection2.prepareStatement("INSERT IGNORE INTO duel_stats(uuid,kills,deaths,wins,loses) VALUES (?,0,0,0,0)");
        ps.setString(1, p.getUniqueId().toString());
        ps.executeUpdate();
        ps.close();
    }
    public static void AddKit(Player p)throws SQLException{
        PreparedStatement ps = connection2.prepareStatement("INSERT IGNORE INTO kit(UUID,Name,Kit) VALUES (?,?,?)");
        ps.setString(1, p.getUniqueId().toString());
        ps.setString(2, p.getName());
        ps.setInt(3, 0);
        ps.executeUpdate();
        ps.close();
    }
    public static void UpdateKit(Player p, int kit)throws SQLException{
        PreparedStatement ps = connection2.prepareStatement("UPDATE kit SET Name = ?, Kit = ? WHERE UUID = ?");
        ps.setString(1, p.getName());
        ps.setInt(2, kit);
        ps.setString(3, p.getUniqueId().toString());
        ps.executeUpdate();
        ps.close();
    }
    public static void UpdateDuel(Player p, String column)throws SQLException{
        PreparedStatement ps = connection2.prepareStatement("UPDATE duel_stats SET `"+column+"` = "+column+"+1 WHERE UUID = ?");
        ps.setString(1, p.getUniqueId().toString());
        ps.executeUpdate();
        ps.close();
    }
    public static KitSQLAPI FindKitByPlayerUUID(String uuid) throws SQLException {

        PreparedStatement statement = connection2.prepareStatement("SELECT * FROM kit WHERE UUID = ?");
        statement.setString(1, uuid);

        ResultSet resultSet = statement.executeQuery();

        KitSQLAPI kitSQLAPI;

        if(resultSet.next()){

            kitSQLAPI = new KitSQLAPI(resultSet.getString("UUID"), resultSet.getString("Name"), resultSet.getInt("Kit"));

            statement.close();

            return kitSQLAPI;
        }

        statement.close();

        return null;
    }
    public static void AddSlot(String table, String uuid, String first, String second, String third, String fourth, String fifth, String sixth, String seventh, String eighth, String ninth, String offhand)throws SQLException{
        PreparedStatement ps = connection3.prepareStatement("INSERT IGNORE INTO "+table+"(UUID,first,second,third,fourth,fifth,sixth,seventh,eighth,ninth,offhand) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, uuid);
        ps.setString(2, first);
        ps.setString(3, second);
        ps.setString(4, third);
        ps.setString(5, fourth);
        ps.setString(6, fifth);
        ps.setString(7, sixth);
        ps.setString(8, seventh);
        ps.setString(9, eighth);
        ps.setString(10, ninth);
        ps.setString(11, offhand);
        ps.executeUpdate();
        ps.close();
    }
    public static void UpdateSlot(String table, String uuid, String first, String second, String third, String fourth, String fifth, String sixth, String seventh, String eighth, String ninth, String offhand)throws SQLException{
        PreparedStatement ps = connection3.prepareStatement("UPDATE "+table+" SET first = ?, second = ?, third = ?, fourth = ?, fifth = ?, sixth = ?, seventh = ?, eighth = ?, ninth = ?, offhand = ? WHERE UUID = ?");
        ps.setString(1, first);
        ps.setString(2, second);
        ps.setString(3, third);
        ps.setString(4, fourth);
        ps.setString(5, fifth);
        ps.setString(6, sixth);
        ps.setString(7, seventh);
        ps.setString(8, eighth);
        ps.setString(9, ninth);
        ps.setString(10, offhand);
        ps.setString(11, uuid);
        ps.executeUpdate();
        ps.close();
    }
    public static SlotSQL FindSlotByUUID(String uuid, String table) throws SQLException {

        PreparedStatement statement = connection3.prepareStatement("SELECT * FROM "+table+" WHERE UUID = ?");
        statement.setString(1, uuid);

        ResultSet resultSet = statement.executeQuery();

        SlotSQL slotSQL;

        if(resultSet.next()){

            slotSQL = new SlotSQL(resultSet.getString("UUID"), resultSet.getString("first"), resultSet.getString("second"), resultSet.getString("third"), resultSet.getString("fourth"), resultSet.getString("fifth"), resultSet.getString("sixth"), resultSet.getString("seventh"), resultSet.getString("eighth"), resultSet.getString("ninth"), resultSet.getString("offhand"));

            statement.close();

            return slotSQL;
        }

        statement.close();

        return null;
    }
    public static DuelStatsSQLAPI FindDuelStatsByPlayerUUID(String uuid) throws SQLException {

        PreparedStatement statement = connection2.prepareStatement("SELECT * FROM duel_stats WHERE uuid = ?");
        statement.setString(1, uuid);

        ResultSet resultSet = statement.executeQuery();

        DuelStatsSQLAPI duelStatsSQLAPI;

        if(resultSet.next()){

            duelStatsSQLAPI = new DuelStatsSQLAPI(resultSet.getString("uuid"), resultSet.getInt("kills"), resultSet.getInt("deaths"), resultSet.getInt("wins"),resultSet.getInt("loses"));

            statement.close();

            return duelStatsSQLAPI;
        }

        statement.close();

        return null;
    }
}
