package src;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameSpecificGUI extends JFrame {
    public GameSpecificGUI(JSONObject gameDetails) {
        String imagePath = gameDetails.getString("directory");
        String name = gameDetails.getString("name");
        String description = gameDetails.getString("description");

        setTitle(name);
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel gameDetailPanel = new JPanel();
        gameDetailPanel.setLayout(new BoxLayout(gameDetailPanel, BoxLayout.Y_AXIS));
        gameDetailPanel.setBackground(Color.DARK_GRAY);

        ImageIcon imageIcon = new ImageIcon(imagePath);

        int maxWidth = 200;
        int maxHeight = 200;
        Image scaledImage = imageIcon.getImage().getScaledInstance(maxWidth, maxHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledImageIcon);

        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setForeground(Color.WHITE);

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionLabel.setForeground(Color.WHITE);

        gameDetailPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        gameDetailPanel.add(imageLabel);
        gameDetailPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        gameDetailPanel.add(nameLabel);
        gameDetailPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        gameDetailPanel.add(descriptionLabel);

        getContentPane().add(gameDetailPanel);

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                dispose();
            }
        });
    }
}
