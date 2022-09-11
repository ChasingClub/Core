package core.itdragclick.Pack;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class OfflineplayerAPI {

//    public static final String uuidURL = "https://sessionserver.mojang.com/session/minecraft/profile/";
//    public static final String nameURL = "https://api.mojang.com/users/profiles/minecraft/";

    public static String getUUID(String name) throws IOException {
        String apiURL = String.format("https://api.mojang.com/users/profiles/minecraft/%s", name);
        URL url = new URL(apiURL);
        BufferedReader stream = new BufferedReader(new InputStreamReader(url.openStream()));
        String websiteResponse = stream.readLine();
        stream.close();

        JsonObject resp = new Gson().fromJson(websiteResponse, JsonObject.class);

        if (resp != null && resp.has("id")) return resp.get("id").getAsString();

        return null;
    }
}