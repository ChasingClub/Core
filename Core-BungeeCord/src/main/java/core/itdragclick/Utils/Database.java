package core.itdragclick.Utils;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import static core.itdragclick.Core.*;
import static core.itdragclick.Utils.Utils.CheckGroupPlayer;

public class Database {
    private static Connection connection2;
    private static Connection connection3;
    private static Connection connection4;

    public Connection getConnection2() throws SQLException {
        String url = "jdbc:mysql://"+ip+"/"+database3+"?useSSL=true";
        String user = username3;
        String password = password3;
        Connection connection = DriverManager.getConnection(url, user, password);
        Database.connection3 = connection;

        return connection;
    }
    public Connection getConnection3() throws SQLException {
        String url = "jdbc:mysql://panel.chasingclub.net:3306/s2_bugreport?useSSL=true";
        String user = "u2_UAmgCY010D";
        String password = "BaY!!5LC^1jC@6X^!xJn0Jrr";
        Connection connection = DriverManager.getConnection(url, user, password);
        Database.connection4 = connection;

        return connection;
    }
    public static void addBugs(ProxiedPlayer p, String bugs) throws SQLException {
        PreparedStatement ps = connection4.prepareStatement("INSERT INTO bug_reports(uuid,name,server,rank,bugs) VALUES (?,?,?,?,?)");
        ps.setString(1, p.getUUID());
        ps.setString(2, p.getName());
        ps.setString(3, p.getServer().getInfo().getName());
        ps.setString(4, CheckGroupPlayer(p));
        ps.setString(5, bugs);

        ps.executeUpdate();
        ps.close();
    }
    public void initializeDatabase2() throws SQLException {

        Statement statement = getConnection2().createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS vanish (uuid VARCHAR(36), username VARCHAR(16), PRIMARY KEY (uuid));");
        statement.close();
    }
    public void initializeDatabase3() throws SQLException {

        Statement statement = getConnection3().createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS `bug_reports` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `uuid` varchar(36) DEFAULT NULL,\n" +
                "  `name` varchar(16) DEFAULT NULL,\n" +
                "  `server` varchar(20) DEFAULT NULL,\n" +
                "  `rank` varchar(20) DEFAULT NULL,\n" +
                "  `bugs` varchar(256) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ");");
//        statement.execute("CREATE TABLE IF NOT EXISTS bug_reports (id INT(11) PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(36), name VARCHAR(16), server VARCHAR(20), rank VARCHAR(20), bugs VARCHAR(256));");
        statement.close();
    }
    public static void GetBugsList(String uuid, CommandSender sender) throws SQLException {
        Statement statement = connection4.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        if(uuid == null) {

            ResultSet rs = statement.executeQuery("SELECT * FROM bug_reports");
            if(rs == null){
                sender.sendMessage(PLname+"No one is report bug yet.");
                return;
            }
            if (rs.isBeforeFirst()) {
                rs.next();
            }
            if (rs.isAfterLast()) {
                rs.previous();
            }
            for (rs.first(); !rs.isAfterLast(); rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String server = rs.getString("server");
                String rank = rs.getString("rank");
                String bugs = rs.getString("bugs");
                sender.sendMessage
                        (
                                "§6ID§8: §7"+id+
                                        "\n"+
                                        "§aName §8» §7"+name+
                                        "\n"+
                                        "§aServer §8» §7"+server+
                                        "\n"+
                                        "§aRank §8» §7"+rank+
                                        "\n"+
                                        "§aMessage §8» §7"+bugs
                        );
            }
        }else{
            String sql = "SELECT * FROM bug_reports WHERE uuid = '"+uuid+"'";
            ResultSet rs = statement.executeQuery(sql);
            if(rs == null){
                sender.sendMessage(PLname+"That player is not report bug yet.");
                return;
            }
            if (rs.isBeforeFirst()) {
                rs.next();
            }
            if (rs.isAfterLast()) {
                rs.previous();
            }
            for (rs.first(); !rs.isAfterLast(); rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String server = rs.getString("server");
                String rank = rs.getString("rank");
                String bugs = rs.getString("bugs");
                sender.sendMessage
                        (
                                "§6ID§8: §7"+id+
                                        "\n"+
                                        "§aName §8» §7"+name+
                                        "\n"+
                                        "§aServer §8» §7"+server+
                                        "\n"+
                                        "§aRank §8» §7"+rank+
                                        "\n"+
                                        "§aMessage §8» §7"+bugs
                        );
            }
        }
    }
    public static VanishAPI findvanishbyuuid(String uuid) throws SQLException {

        PreparedStatement statement = connection3.prepareStatement("SELECT * FROM vanish WHERE uuid = ?");
        statement.setString(1, uuid);

        ResultSet resultSet = statement.executeQuery();

        VanishAPI vanishAPI;

        if(resultSet.next()){

            vanishAPI = new VanishAPI(resultSet.getString("uuid"), resultSet.getString("username"));

            statement.close();

            return vanishAPI;
        }

        statement.close();

        return null;
    }
    public static playerdataAPI findplayerbyuuid(String uuid) throws SQLException {

        PreparedStatement statement = connection2.prepareStatement("SELECT * FROM player WHERE uuid = ?");
        statement.setString(1, uuid);

        ResultSet resultSet = statement.executeQuery();

        playerdataAPI playerdataAPI;

        if(resultSet.next()){

            playerdataAPI = new playerdataAPI(resultSet.getString("uuid"), resultSet.getString("name"), resultSet.getString("ip"), resultSet.getString("country"), resultSet.getString("lastlogin"), resultSet.getString("lastlogout"), resultSet.getString("firstjoin"));

            statement.close();

            return playerdataAPI;
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

    public Connection getConnectiondata() throws SQLException {
        String url = "jdbc:mysql://"+ip+"/"+database2+"?useSSL=true";
        String user = username2;
        String password = password2;
        Connection connection = DriverManager.getConnection(url, user, password);
        Database.connection2 = connection;

        return connection;
    }
    public void playerdatatable() throws SQLException{
        Statement statement = getConnectiondata().createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS player (uuid VARCHAR(36), name VARCHAR(16), ip VARCHAR(20), country VARCHAR(12), lastlogin VARCHAR(20), lastlogout VARCHAR(20), firstjoin VARCHAR(20), PRIMARY KEY (uuid));");
        statement.execute("CREATE TABLE IF NOT EXISTS whitelist (uuid VARCHAR(36), name VARCHAR(16), PRIMARY KEY (uuid));");
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