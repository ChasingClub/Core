package core.itdragclick.Utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Utils {
    // CHECK GROUP
    //////////////////////////////////////////////////////
    public static String CheckGroupSender(CommandSender Sender){
        String group = null;
        if (Sender.hasPermission("pf.default")){
            group = "Default";
        }if (Sender.hasPermission("pf.vip")) {
            group = "VIP";
        }if (Sender.hasPermission("pf.premium")) {
            group = "Premium";
        }if (Sender.hasPermission("pf.legend")) {
            group = "Legend";
        }if (Sender.hasPermission("pf.contributor")) {
            group = "Contributor";
        }if (Sender.hasPermission("pf.social")) {
            group = "Social";
        }if (Sender.hasPermission("pf.beta")) {
            group = "Beta Tester";
        }if (Sender.hasPermission("pf.staff")) {
            group = "Staff";
        }if (Sender.hasPermission("pf.builder")) {
            group = "Builder";
        }if (Sender.hasPermission("pf.mod")) {
            group = "Moderator";
        }if (Sender.hasPermission("pf.admin")) {
            group = "Admin";
        }if (Sender.hasPermission("pf.owner")) {
            group = "Owner";
        }
        return group;
    }
    public static String CheckGroupPlayer(ProxiedPlayer p){
        String group = null;
        if (p.hasPermission("pf.default")){
            group = "Default";
        }if (p.hasPermission("pf.vip")) {
            group = "VIP";
        }if (p.hasPermission("pf.premium")) {
            group = "Premium";
        }if (p.hasPermission("pf.legend")) {
            group = "Legend";
        }if (p.hasPermission("pf.contributor")) {
            group = "Contributor";
        }if (p.hasPermission("pf.social")) {
            group = "Social";
        }if (p.hasPermission("pf.beta")) {
            group = "Beta Tester";
        }if (p.hasPermission("pf.staff")) {
            group = "Staff";
        }if (p.hasPermission("pf.builder")) {
            group = "Builder";
        }if (p.hasPermission("pf.mod")) {
            group = "Moderator";
        }if (p.hasPermission("pf.admin")) {
            group = "Admin";
        }if (p.hasPermission("pf.owner")) {
            group = "Owner";
        }
        return group;
    }
    //////////////////////////////////////////////////////
    public static String noperm = ChatColor.RED+"You don't have permission to do that!";
    public static String computeMD5Hash(String s){
        try{
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(),0,s.length());
            return (new BigInteger(1,m.digest()).toString(16)).toString();
        } catch (NoSuchAlgorithmException nsax){
            nsax.printStackTrace();
        }
        return null;

    }


    public boolean compareHashes(String hash1, String hash2) {
        return hash1.equals(hash2);
    }


    public static String SHA1(String s){
        try{
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-1");
            byte[] sha1hash = new byte[40];
            md.update(s.getBytes("iso-8859-1"), 0, s.length());
            sha1hash = md.digest();
            return convertToHex(sha1hash);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex){
            ex.printStackTrace();
        }
        return null;
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }


    public static String SHA256(String s) {
        try{
            String password = s;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            StringBuffer hexString = new StringBuffer();
            for (int i=0;i<byteData.length;i++) {
                String hex=Integer.toHexString(0xff & byteData[i]);
                if(hex.length()==1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }
        return null;
    }


    public static String SHA384(String s) {
        try{
            String password = s;
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            md.update(password.getBytes());

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            StringBuffer hexString = new StringBuffer();
            for (int i=0;i<byteData.length;i++) {
                String hex=Integer.toHexString(0xff & byteData[i]);
                if(hex.length()==1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static String SHA512(String s) {
        try{
            String password = s;
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes());

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            StringBuffer hexString = new StringBuffer();
            for (int i=0;i<byteData.length;i++) {
                String hex=Integer.toHexString(0xff & byteData[i]);
                if(hex.length()==1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }
        return null;
    }
    public static String HashByItDragClick(String text) {
        String s = text;
        if(s.contains("A")) s = s.replace("A", "&p*#%?@z$&p*#%?");
        if(s.contains("a")) s = s.replace("a", "$&p*#%?");
        if(s.contains("B")) s = s.replace("B", "&p*#%?z&p*#%?$a");
        if(s.contains("b")) s = s.replace("b", "$&p*#%?@a");
        if(s.contains("C")) s = s.replace("C", "&p*#%?z$&p*#%?b");
        if(s.contains("c")) s = s.replace("c", "&p*#%?$b");
        if(s.contains("D")) s = s.replace("D", "&p*#%?z$&p*#%?c");
        if(s.contains("d")) s = s.replace("d", "&p*#%?$c");
        if(s.contains("E")) s = s.replace("E", "&p*#%?z@&p*#%?$d");
        if(s.contains("e")) s = s.replace("e", "$&p*#%?d");
        if(s.contains("F")) s = s.replace("F", "&p*#%?$z&p*#%?e");
        if(s.contains("f")) s = s.replace("f", "&p*#%?$e");
        if(s.contains("G")) s = s.replace("G", "&p*#%?z$&p*#%?f");
        if(s.contains("g")) s = s.replace("g", "&p*#%?f");
        if(s.contains("H")) s = s.replace("H", "&p*#%?$z@&p*#%?g");
        if(s.contains("h")) s = s.replace("h", "&p*#%?g");
        if(s.contains("I")) s = s.replace("I", "&p*#%?z&p*#%?$h");
        if(s.contains("i")) s = s.replace("i", "$&p*#%?h");
        if(s.contains("J")) s = s.replace("J", "&p*#%?z&p*#%?$i");
        if(s.contains("j")) s = s.replace("j", "&p*#%?i@");
        if(s.contains("K")) s = s.replace("K", "$&p*#%?z&p*#%?j");
        if(s.contains("k")) s = s.replace("k", "&p*#%?j@$");
        if(s.contains("L")) s = s.replace("L", "$&p*#%?z&p*#%?k");
        if(s.contains("l")) s = s.replace("l", "$&p*#%?k");
        if(s.contains("M")) s = s.replace("M", "@&p*#%?z&p*#%?$l");
        if(s.contains("m")) s = s.replace("m", "&p*#%?$l");
        if(s.contains("N")) s = s.replace("N", "&p*#%?@z&p*#%?$m");
        if(s.contains("n")) s = s.replace("n", "&p*#%?$m");
        if(s.contains("O")) s = s.replace("O", "&p*#%?z&p*#%?$n");
        if(s.contains("o")) s = s.replace("o", "&p*#%?$n");
        if(s.contains("P")) s = s.replace("P", "&p*#%?@$z&p*#%?o");
        if(s.contains("p")) s = s.replace("p", "$&p*#%?o");
        if(s.contains("Q")) s = s.replace("Q", "$&p*#%?z@$&p*#%?p");
        if(s.contains("q")) s = s.replace("q", "$&p*#%?p");
        if(s.contains("R")) s = s.replace("R", "&p*#%?z&p*#%?$q");
        if(s.contains("r")) s = s.replace("r", "&p*#%?@$q");
        if(s.contains("S")) s = s.replace("S", "&p*#%?$z&p*#%?r");
        if(s.contains("s")) s = s.replace("s", "$&p*#%?@r");
        if(s.contains("T")) s = s.replace("T", "&p*#%?z&p*#%?$s");
        if(s.contains("t")) s = s.replace("t", "&p*#%?$s");
        if(s.contains("U")) s = s.replace("U", "&p*#%?z&p*#%?t$");
        if(s.contains("u")) s = s.replace("u", "$&p*#%?@t");
        if(s.contains("V")) s = s.replace("V", "&p*#%?z&p*#%?u$");
        if(s.contains("v")) s = s.replace("v", "$&p*#%?u");
        if(s.contains("W")) s = s.replace("W", "&p*#%?z@&p*#%?$v");
        if(s.contains("w")) s = s.replace("w", "$&p*#%?v");
        if(s.contains("X")) s = s.replace("X", "@&p*#%?z$&p*#%?w");
        if(s.contains("x")) s = s.replace("x", "&p*#%?$w");
        if(s.contains("Y")) s = s.replace("Y", "&p*#%?z$@&p*#%?x");
        if(s.contains("y")) s = s.replace("y", "&p*#%?$x");
        if(s.contains("Z")) s = s.replace("Z", "$@&p*#%?z&p*#%?y");
        if(s.contains("z")) s = s.replace("z", "&p*#%?y$");
        if(s.contains("0")) s = s.replace("0", "*g3G?^i");
        if(s.contains("1")) s = s.replace("1", "*gG%#3@Gi");
        if(s.contains("2")) s = s.replace("2", "*g#3G%i");
        if(s.contains("3")) s = s.replace("3", "*g3@^Gi");
        if(s.contains("4")) s = s.replace("4", "*g3%Gi");
        if(s.contains("5")) s = s.replace("5", "*g3G*@i");
        if(s.contains("6")) s = s.replace("6", "*g@3%G*i");
        if(s.contains("7")) s = s.replace("7", "*g3%Gi*");
        if(s.contains("8")) s = s.replace("8", "*g3G@%*i");
        if(s.contains("9")) s = s.replace("9", "*g3&Gi");

        return s;
    }
}
