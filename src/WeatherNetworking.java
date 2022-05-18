import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class WeatherNetworking {

    private String baseUrl;
    private String apiKey;
    private String apiURL;

    public WeatherNetworking()
    {
        baseUrl = "http://api.weatherapi.com/v1";
        apiKey = "7b3d6740a6244ad28bd231310221705";
        apiURL = "";
    }

    public String makeAPICallForForecast(String zipCode)
    {
        String endPoint = "/forecast.json";
        String url = baseUrl + endPoint + "?q=" + zipCode + "&key=" + apiKey + "&days=14";

        try {
            URI myUri = URI.create(url); // creates a URI object from the url string
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

    public ArrayList<Weather> getNowShowing()
    {
        String endPoint = "/forecast.json";
        String urlNowShowing = baseUrl + endPoint + "?api_key=" + apiKey;

        // use the makeAPICall helper method to make a network request to the Now Playing API
        String response = makeAPICallForForecast(urlNowShowing);

        // call the parseNowPlayingJSON helper method, then return
        // the arraylist of Movie objects that gets returned by that helper method
        ArrayList<Weather> weatherNowShowing = parseForecastDay(response);
        return weatherNowShowing;
    }

    public ArrayList<Weather> parseForecastDay(String json)
    {
        ArrayList<Weather> reports = new ArrayList<Weather>();

        JSONObject jsonObj = new JSONObject(json);
        JSONObject forecastObj = jsonObj.getJSONObject("forecast");
        JSONArray forecaseArr = forecastObj.getJSONArray("forecastday");

        for (int i = 0; i < forecaseArr.length(); i++)
        {
            JSONObject forecast = forecaseArr.getJSONObject(i);
            String date = forecast.getString("date");
            JSONObject dayObj = forecast.getJSONObject("day");
            double minTemp = dayObj.getDouble("mintemp_f");
            double maxTemp = dayObj.getDouble("maxtemp_f");
            double avgTemp = dayObj.getDouble("avgtemp_f");
            double precip = dayObj.getDouble("totalprecip_in");
            double avgHum = dayObj.getDouble("avghumidity");

            JSONObject astroObj = forecast.getJSONObject("astro");
            String sunrise = astroObj.getString("sunrise");
            String sunset = astroObj.getString("sunset");

            Weather weather = new Weather(date, maxTemp, minTemp, avgTemp, precip, avgHum, sunrise, sunset);
            reports.add(weather);
        }
        return reports;
    }
}