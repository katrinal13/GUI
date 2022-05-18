public class Weather
{
    private String date;
    private double maxTemp;
    private double minTemp;
    private double avgTemp;
    private double precip;
    private double avgHum;
    private String sunrise;
    private String sunset;

    public Weather(String date, double maxTemp, double minTemp, double avgTemp, double precip, double avgHum, String sunrise, String sunset)
    {
        this.date = date;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.avgTemp = avgTemp;
        this.precip = precip;
        this.avgHum = avgHum;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public String getDate()
    {
        return date;
    }

    public double getMaxTemp()
    {
        return maxTemp;
    }

    public double getMinTemp()
    {
        return minTemp;
    }

    public double getAvgTemp()
    {
        return avgTemp;
    }

    public double getPrecip()
    {
        return precip;
    }

    public double getAvgHum()
    {
        return avgHum;
    }

    public String getSunrise()
    {
        return sunrise;
    }

    public String getSunset()
    {
        return sunset;
    }
}
