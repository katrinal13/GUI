public class Music
{
    private String album;
    private String artist;
    private String url;
    private int listeners;


    public Music(String album, String artist, String url, int listeners)
    {
        this.album = album;
        this.artist = artist;
        this.url = url;
        this.listeners = listeners;
    }

    public String getAlbum()
    {
        return album;
    }

    public String getArtist()
    {
        return artist;
    }

    public String getURL()
    {
        return url;
    }

    public int getListeners()
    {
        return listeners;
    }
}
