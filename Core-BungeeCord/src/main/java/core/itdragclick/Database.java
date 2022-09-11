package core.itdragclick;

import core.itdragclick.Pack.GuildAPI;
import core.itdragclick.Pack.UserAPI;
import core.itdragclick.Pack.WhitelistAPI;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import static core.itdragclick.Core.*;

public class Database {
    private static Connection connection;
    private static Connection connection2;

    public Connection getConnection() throws SQLException {
        if (connection != null) {
            return connection;
        }
        String url = "jdbc:mysql://"+ip+"/"+database;
        String user = username;
        String password = Core.password;
        Connection connection = DriverManager.getConnection(url, user, password);
        Database.connection = connection;

        return connection;
    }
    public void initializeDatabase() throws SQLException {

        Statement statement = getConnection().createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS guild (name varchar(16), displayname varchar(36), owner varchar(36), PRIMARY KEY (name))");
        statement.execute("CREATE TABLE IF NOT EXISTS user (uuid varchar(36), username tinytext, guild varchar(16), rank varchar(20),PRIMARY KEY (uuid))");
        statement.close();
    }
    public static void addguild(String name, ProxiedPlayer p) throws SQLException {
        PreparedStatement guild = connection.prepareStatement("INSERT INTO guild(name,displayname,owner) VALUES (?,?,?)");
        guild.setString(1, name);
        guild.setString(2, name);
        guild.setString(3, p.getUUID());

        guild.executeUpdate();
        guild.close();
    }
    public static void adduserowner(String name, ProxiedPlayer p) throws SQLException {
        PreparedStatement user = connection.prepareStatement("UPDATE user SET username = ?, guild = ?, rank = ? WHERE uuid = ?");
        user.setString(1, p.getName());
        user.setString(2, name);
        user.setString(3, "owner");
        user.setString(4, p.getUUID());

        user.executeUpdate();
        user.close();
    }
    public static void adduser(String guildname, ProxiedPlayer target) throws SQLException {
        PreparedStatement user = connection.prepareStatement("UPDATE user SET username = ?, guild = ?, rank = ? WHERE uuid = ?");
        user.setString(1, target.getName());
        user.setString(2, guildname);
        user.setString(3, "default");
        user.setString(4, target.getUUID());

        user.executeUpdate();
        user.close();
    }
    public static void removeuser(ProxiedPlayer p) {
        PreparedStatement user;
        try {
            user = connection.prepareStatement("UPDATE user SET username = ?, guild = ?, rank = ? WHERE uuid = ?");
        user.setString(1, p.getName());
        user.setString(2, "null");
        user.setString(3, "null");
        user.setString(4, p.getUUID());

        user.executeUpdate();
        user.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void removeguild(String name) throws SQLException {
        PreparedStatement guild = connection.prepareStatement("DELETE FROM guild WHERE name = ?");
        guild.setString(1, name);

        guild.executeUpdate();
        guild.close();
    }
    public static void checkuser(ProxiedPlayer p) throws SQLException {
        PreparedStatement user = connection.prepareStatement("INSERT IGNORE INTO user(uuid,username,guild,rank) VALUES (?,?,?,?)");
        user.setString(1, p.getUUID());
        user.setString(2, p.getName());
        user.setString(3, "null");
        user.setString(4, "null");
        user.executeUpdate();
        user.close();
    }
    public static GuildAPI findguildbyname(String name) throws SQLException {

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM guild WHERE name = ?");
        statement.setString(1, name);

        ResultSet resultSet = statement.executeQuery();

        GuildAPI guild;

        if(resultSet.next()){

            guild = new GuildAPI(resultSet.getString("name"), resultSet.getString("displayname"), resultSet.getString("owner"));

            statement.close();

            return guild;
        }

        statement.close();

        return null;
    }
    public static UserAPI finduserbyuuid(String uuid) throws SQLException {

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE uuid = ?");
        statement.setString(1, uuid);

        ResultSet resultSet = statement.executeQuery();

        UserAPI user;

        if(resultSet.next()){

            user = new UserAPI(resultSet.getString("uuid"), resultSet.getString("username"), resultSet.getString("guild"), resultSet.getString("rank"));

            statement.close();

            return user;
        }

        statement.close();

        return null;
    }
    public static WhitelistAPI findwhitelistbyuuid(String uuid) throws SQLException {

        PreparedStatement statement = connection2.prepareStatement("SELECT * FROM whitelist WHERE uuid = ?");
        statement.setString(1, uuid);

        ResultSet resultSet = statement.executeQuery();

        WhitelistAPI whitelistAPI;

        if(resultSet.next()){

            whitelistAPI = new WhitelistAPI(resultSet.getString("uuid"), resultSet.getString("name"));

            statement.close();

            return whitelistAPI;
        }

        statement.close();

        return null;
    }
    public static void playerdatatable()throws SQLException{
        String url = "jdbc:mysql://"+ip+"/"+database2;
        String user = username2;
        String password = password2;
        Connection connection2 = DriverManager.getConnection(url, user, password);
        Database.connection2 = connection2;
        Statement statement = connection2.createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS player (uuid varchar(36), name varchar(16), ip varchar(20), country varchar(12), lastlogin varchar(20), lastlogout varchar(20), firstjoin varchar(20), PRIMARY KEY (uuid))");
        statement.execute("CREATE TABLE IF NOT EXISTS whitelist (uuid varchar(36), name varchar(16), PRIMARY KEY (uuid))");
        statement.close();
    }
    public static void playerdataonfirstjoin(ProxiedPlayer p, String geo)throws SQLException{
        PreparedStatement ps = connection2.prepareStatement("INSERT IGNORE INTO player(uuid,name,ip,country,firstjoin) VALUES (?,?,?,?,?)");
        ps.setString(1, p.getUUID());
        ps.setString(2, p.getName());
        ps.setString(3, p.getAddress().getAddress().getHostAddress());
        ps.setString(4, geo);
        ps.setString(5, getDate());
        ps.executeUpdate();
        ps.close();
    }
    public static void playerdataonjoin(ProxiedPlayer p, String geo)throws SQLException{
        PreparedStatement ps = connection2.prepareStatement("UPDATE player SET name=?,ip=?,country=?,lastlogin=? WHERE uuid=?");
        ps.setString(1, p.getName());
        ps.setString(2, p.getAddress().getAddress().getHostAddress());
        ps.setString(3, geo);
        ps.setString(4, getDate());
        ps.setString(5, p.getUUID());
        ps.executeUpdate();
        ps.close();
    }
    public static void playerdataonleave(ProxiedPlayer p, String geo)throws SQLException{
        PreparedStatement ps = connection2.prepareStatement("UPDATE player SET name=?,ip=?,country=?,lastlogout=?  WHERE uuid=?");
        ps.setString(1, p.getName());
        ps.setString(2, p.getAddress().getAddress().getHostAddress());
        ps.setString(3, geo);
        ps.setString(4, getDate());
        ps.setString(5, p.getUUID());
        ps.executeUpdate();
        ps.close();
    }
    public static void addwhitelist(String uuid, String name)throws SQLException{
        PreparedStatement ps = connection2.prepareStatement("INSERT INTO whitelist(uuid,name) VALUES (?,?)");
        ps.setString(1, uuid);
        ps.setString(2, name);
        ps.executeUpdate();
        ps.close();
    }
    public static void removewhitelist(String uuid)throws SQLException{
        PreparedStatement ps = connection2.prepareStatement("DELETE FROM whitelist WHERE uuid = ?");
        ps.setString(1, uuid);
        ps.executeUpdate();
        ps.close();
    }
    public static String getDate(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return sdf.format(cal.getTime());
    }
}