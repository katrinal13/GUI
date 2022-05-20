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

public class NowPlayingGUIController implements ActionListener
{
    private JTextArea musicInfo;
    private JTextField albumEntryField;
    private JTextField artistEntryField;
    private MusicNetworking client;

    public NowPlayingGUIController()
    {
        musicInfo = new JTextArea(20, 35);
        client = new MusicNetworking();

        setupGui();
    }

    private void setupGui()
    {
        JFrame frame = new JFrame("My Music App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon image = new ImageIcon("src/lastfm.jpg");
        Image imageData = image.getImage();
        Image scaledImage = imageData.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(scaledImage);  // transform it back
        JLabel pictureLabel = new JLabel(image);
        JLabel welcomeLabel = new JLabel("Data on albums!");
        welcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.blue);

        JPanel logoWelcomePanel = new JPanel();
        logoWelcomePanel.add(pictureLabel);
        logoWelcomePanel.add(welcomeLabel);

        JPanel entryPanel = new JPanel();
        JLabel albumLabel = new JLabel("Enter Album Name: ");
        JLabel artistLabel = new JLabel("Enter Artist Name: ");
        albumEntryField = new JTextField(10);
        artistEntryField = new JTextField(10);

        JButton sendButton = new JButton("Send");
        JButton resetButton = new JButton("Reset");
        entryPanel.add(albumLabel);
        entryPanel.add(albumEntryField);
        entryPanel.add(artistLabel);
        entryPanel.add(artistEntryField);
        entryPanel.add(sendButton);
        entryPanel.add(resetButton);

        frame.add(logoWelcomePanel, BorderLayout.NORTH);
        frame.add(musicInfo, BorderLayout.SOUTH);
        frame.add(entryPanel, BorderLayout.CENTER);

        sendButton.addActionListener(this);
        resetButton.addActionListener(this);

        frame.pack();
        frame.setVisible(true);
    }

    private void loadMusicInfo(Music music)
    {
        String info = "Name: " + music.getAlbum() + "\n\nArtist: " + music.getArtist() + "\n\nURL: " + music.getURL() + "\n\nListeners: " + music.getListeners();
        musicInfo.setText(info);
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) (e.getSource());  // cast source to JButton
        String text = button.getText();

        if (text.equals("Send"))
        {
            String album = albumEntryField.getText();
            String artist = artistEntryField.getText();
            String albumURL = "";
            if (album.indexOf(" ") != -1)
            {
                for (int i = 0; i < album.length(); i++)
                {
                    if (album.substring(i, i + 1).equals(" "))
                    {
                        albumURL += "+";
                    }
                    else
                    {
                        albumURL += album.substring(i, i + 1);
                    }
                }
                album = albumURL;
            }

            String artistURL = "";
            if (artist.indexOf(" ") != -1) {
                for (int i = 0; i < artist.length(); i++) {
                    if (artist.substring(i, i + 1).equals(" ")) {
                        artistURL += "+";
                    } else {
                        artistURL += artist.substring(i, i + 1);
                    }
                }
                artist = artistURL;
            }
            String response = client.makeAPICallForForecast(artist, album);
            loadMusicInfo(client.parseMusic(response));
        }

        else if (text.equals("Reset"))
        {
            albumEntryField.setText("");
            artistEntryField.setText("");
        }
    }
}