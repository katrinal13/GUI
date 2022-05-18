import java.util.Scanner;
public class GUIRunner
{
    public static void main(String[] args)
    {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter zip code for weather: ");
        String zip = s.nextLine();
        WeatherNetworking networker = new WeatherNetworking();
        String response = networker.makeAPICallForForecast(zip);
        networker.parseForecastDay(response);
        NowPlayingGUIController gui = new NowPlayingGUIController();
    }
}