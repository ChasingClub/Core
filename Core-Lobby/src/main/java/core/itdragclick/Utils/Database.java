package core.itdragclick.Utils;

import core.itdragclick.Core;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class Database {
    public Core plugin;
    public Database(Core plugin){
        this.plugin = plugin;
    }
    public static Connection connection;

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
}
