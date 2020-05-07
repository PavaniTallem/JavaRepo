import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeScreen extends JFrame {

    private JButton peopleButton;
    private JButton disasterButton;
    private JButton countriesButton;
    private JButton deathButton;
    private JButton placeButton;
    private JPanel homePanel;

    public HomeScreen() {
        add(homePanel);
        setTitle("Home Screen");
        setSize(400, 500);
        this.setLocationRelativeTo(null);
        peopleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PeopleForm peopleForm = new PeopleForm();
                peopleForm.pack();
                peopleForm.setLocationRelativeTo(null);
                //peopleForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                peopleForm.setVisible(true);

            }
        });
        disasterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisasterForm disasterForm = new DisasterForm();
                disasterForm.pack();
                disasterForm.setLocationRelativeTo(null);
                //disasterForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                disasterForm.setVisible(true);
            }
        });
        countriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CountriesForm countriesForm = new CountriesForm();
                countriesForm.pack();
                countriesForm.setLocationRelativeTo(null);
                //countriesForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                countriesForm.setVisible(true);
            }
        });
        deathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeathForm deathForm= new DeathForm();
                deathForm.pack();
                deathForm.setLocationRelativeTo(null);
                //deathForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                deathForm.setVisible(true);

            }
        });
        placeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaceForm placeForm = new PlaceForm();
                placeForm.pack();
                placeForm.setLocationRelativeTo(null);
                //placeForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                placeForm.setVisible(true);

            }
        });
    }
}
