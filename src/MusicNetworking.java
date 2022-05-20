import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MusicNetworking {

    private String baseUrl;
    private String apiKey;
    private String apiURL;

    public MusicNetworking()
    {
        baseUrl = "http://ws.audioscrobbler.com/2.0/";
        apiKey = "ae1984a8b387844ffd7e00669182f0fc";
        apiURL = "";
    }

    public String makeAPICallForForecast(String artist, String album)
    {
        String endPoint = "&format=json";
        String url = baseUrl + "?method=album.getinfo&api_key=" + apiKey + "&artist=" + artist + "&album=" + album + endPoint;

        try {
            URI myUri = URI.create(url);
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            apiURL = response.body();
            return response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Music parseMusic(String json)
    {
        JSONObject jsonObj = new JSONObject(json);
        JSONObject albumObj = jsonObj.getJSONObject("album");
        Music music = new Music(albumObj.getString("name"), albumObj.getString("artist"), albumObj.getString("url"), albumObj.getInt("playcount"));
        return music;
    }
}