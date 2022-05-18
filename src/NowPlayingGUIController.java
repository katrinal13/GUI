// lots of classes get used here!
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

// this class implements ActionListener interface, which allows for interactivity with JButtons
public class NowPlayingGUIController implements ActionListener
{
    // we set and use these across different methods
    // so we add them as instance variables
    private JTextArea weatherInfo;
    private JTextField weatherEntryField;
    private ArrayList<Weather> nowShowing;
    private WeatherNetworking client;

    // constructor, which calls helper methods
    // to setup the GUI then load the now playing list
    public NowPlayingGUIController()
    {
        weatherInfo = new JTextArea(20, 35);
        nowShowing = new ArrayList<>();
        client = new WeatherNetworking();  // our "networking client"

        // setup GUI and load Now Playing list
        setupGui();
        loadForecast();
    }

    // private helper method, called by constructor
    // to set up the GUI and display it
    private void setupGui()
    {
        //Creating a Frame
        JFrame frame = new JFrame("My Weather Forecast App!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // ends program when you hit the X

        // Creating an image from a jpg file stored in the src directory
        ImageIcon image = new ImageIcon("src/Logo.jpg");
        Image imageData = image.getImage(); // transform it
        Image scaledImage = imageData.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        image = new ImageIcon(scaledImage);  // transform it back
        JLabel pictureLabel = new JLabel(image);
        JLabel welcomeLabel = new JLabel("   Forecast For This Month!");
        welcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.blue);

        JPanel logoWelcomePanel = new JPanel(); // the panel is not visible in output
        logoWelcomePanel.add(pictureLabel);
        logoWelcomePanel.add(welcomeLabel);

        // middle panel with movie list
        JPanel weatherListPanel = new JPanel();
        weatherInfo.setText("forecast loading...");
        weatherInfo.setFont(new Font("Helvetica", Font.PLAIN, 16));
        weatherInfo.setWrapStyleWord(true);
        weatherInfo.setLineWrap(true);
        weatherListPanel.add(weatherInfo);

        //bottom panel with text field and buttons
        JPanel entryPanel = new JPanel(); // the panel is not visible in output
        JLabel weatherLabel = new JLabel("Which Date? (Enter 1-14): ");
        weatherEntryField = new JTextField(10); // accepts up to 10 characters
        JButton sendButton = new JButton("Send");
        JButton resetButton = new JButton("Reset");
        entryPanel.add(weatherLabel);
        entryPanel.add(weatherEntryField);
        entryPanel.add(sendButton);
        entryPanel.add(resetButton);

        //Adding Components to the frame
        frame.add(logoWelcomePanel, BorderLayout.NORTH);
        frame.add(weatherListPanel, BorderLayout.CENTER);
        frame.add(entryPanel, BorderLayout.SOUTH);

        // PART 2 -- SET UP EVENT HANDLING
        //setting up buttons to use ActionListener interface and actionPerformed method
        sendButton.addActionListener(this);
        resetButton.addActionListener(this);

        // showing the frame
        frame.pack();
        frame.setVisible(true);
    }

    // private helper method to load the Now Playing
    // movie list into the GUI by making a network call,
    // obtaining an arraylist of Movie objects, then
    // creating a string that gets displayed in a GUI label;
    // this method gets called by the constructor as part of
    // the initial set up of the GUI, and also when the user
    // clicks the "Reset" button
    private void loadForecast()
    {
        // use client to make network call to Now Playing, which returns an arraylist
        // which gets assigned to the nowPlaying instance variable
        nowShowing = client.getNowShowing();

        // build the string to display in the movieInfo label
        String labelStr = "";
        int count = 1;
        for (Weather weather : nowShowing)
        {
            labelStr += count + ". " + weather.getDate() + "\n";
            count++;
        }
        weatherInfo.setText(labelStr);
    }

    // private helper method to load the details for
    // a particular movie into the GUI by making a network call,
    // obtaining a DetailedMovie, then
    // creating a string that gets displayed in a GUI label;
    // this method gets called when the user clicks the "Send" button
    private void loadWeatherInfo(Weather weather)
    {
        // build the string with movie details
        String info = "Date: " + weather.getDate() +
                "\n\nMaxTemp: " + weather.getMaxTemp() +
                "\n\nMinTemp: " + weather.getMinTemp() +
                "\n\nAvgTemp: " + weather.getAvgTemp() +
                "\n\nPrecipitation: " + weather.getPrecip() +
                "\n\nAvgHumidity: " + weather.getAvgHum() +
                "\n\nSunrise: " + weather.getSunrise() +
                "\n\nSunrise: " + weather.getSunset();
        weatherInfo.setText(info);
    }

    // implement ActionListener interface method
    // this method gets invoked anytime either button
    // gets clicked; we need code to differentiate which
    // button sent was clicked, so we use the text of the
    // button ("Send" or "Reset") to determine this
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) (e.getSource());  // cast source to JButton
        String text = button.getText();

        if (text.equals("Send"))
        {
            // obtain the numerical value that the user typed into the text field
            // (getTest() returns a string) and convert it to an int
            String selectedMovieNum = weatherEntryField.getText();
            int movieNumInt = Integer.parseInt(selectedMovieNum);

            // obtain the movie in the nowPlaying arraylist that the number they
            // typed in corresponds to
            int movieIdx = movieNumInt - 1;
            Weather selectedWeather = nowShowing.get(movieIdx);

            // call private method to load movie info for that Movie object
            loadWeatherInfo(selectedWeather);
        }

        // if user clicked "Reset" button, set the text field back to empty string
        // and load the Now Playing list again
        else if (text.equals("Reset"))
        {
            weatherEntryField.setText("");
            loadForecast();
        }
    }
}