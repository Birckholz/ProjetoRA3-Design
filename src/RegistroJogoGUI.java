package src;

import org.json.JSONObject;
import src.PerfilAdm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class RegistroJogoGUI extends JFrame {
    private JTextField nameField;
    private JTextField descricaoField;
    private JTextField priceField;
    private JTextField imageField;
    private String selectedFilePath;

    public RegistroJogoGUI(JSONObject session) {
        setTitle("Registrar Jogo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel nameLabel = new JLabel("Name:");
        rightPanel.add(nameLabel, gbc);

        gbc.gridy = 1;
        JLabel descricaoLabel = new JLabel("Descrição:");
        rightPanel.add(descricaoLabel, gbc);

        gbc.gridy = 2;
        JLabel priceLabel = new JLabel("Price:");
        rightPanel.add(priceLabel, gbc);

        gbc.gridy = 3;
        JLabel imageLabel = new JLabel("Image:");
        rightPanel.add(imageLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        nameField = new JTextField(20);
        rightPanel.add(nameField, gbc);

        gbc.gridy = 1;
        descricaoField = new JTextField(20);
        rightPanel.add(descricaoField, gbc);

        gbc.gridy = 2;
        priceField = new JTextField(20);
        rightPanel.add(priceField, gbc);

        gbc.gridy = 3;
        JPanel imagePanel = new JPanel(new BorderLayout());
        imageField = new JTextField(15);
        JButton selectFileButton = new JButton("Select File");
        selectFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedFilePath = selectedFile.getAbsolutePath();
                imageField.setText(selectedFilePath);
            }
        });
        imagePanel.add(imageField, BorderLayout.CENTER);
        imagePanel.add(selectFileButton, BorderLayout.EAST);
        rightPanel.add(imagePanel, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton registroButton = new JButton("Registrar");
        rightPanel.add(registroButton, gbc);

        gbc.gridy = 5;
        JButton goBackButton = new JButton("Go Back");
        rightPanel.add(goBackButton, gbc);

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PerfilAdm perfilAdmGUI = new PerfilAdm(session);
                RegistroJogoGUI.this.dispose();
            }
        });

        registroButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String name = nameField.getText();
                String description = descricaoField.getText();
                Double price = Double.parseDouble(priceField.getText());

                if (selectedFilePath != null) {
                    File selectedFile = new File(selectedFilePath);
                    String destinationPath = "image/" + selectedFile.getName();

                    try {
                        Files.copy(selectedFile.toPath(), Path.of(destinationPath), StandardCopyOption.REPLACE_EXISTING);
                        Game temp = new Game(name, description, price, destinationPath);
                        RegistroGAMEAction.registrarJogo(temp);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        panel.add(rightPanel, BorderLayout.CENTER);

        add(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


}
