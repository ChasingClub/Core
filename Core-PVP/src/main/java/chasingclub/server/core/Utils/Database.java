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
}
