package src;


import javax.swing.JTextField;
import java.io.IOException;
import java.io.FileWriter;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class RegistroGUI extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JTextField nicknameField;
    private JPasswordField passwordField;

    public RegistroGUI() {
        setTitle("Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(0.5);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.LIGHT_GRAY);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.LIGHT_GRAY);

        JLabel welcomeLabel = new JLabel("Bem vindo ao GAMETube");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setVerticalAlignment(JLabel.CENTER);

        Font font = new Font("Arial", Font.BOLD, 20);
        welcomeLabel.setFont(font);

        centerPanel.add(welcomeLabel, BorderLayout.CENTER);

        leftPanel.add(centerPanel, BorderLayout.CENTER);

        splitPane.setLeftComponent(leftPanel);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel nameLabel = new JLabel("Name:");
        rightPanel.add(nameLabel, gbc);

        gbc.gridy = 2;
        JLabel nicknameLabel = new JLabel("Username:");
        rightPanel.add(nicknameLabel, gbc);

        gbc.gridy = 3;
        JLabel emailLabel = new JLabel("Email:");
        rightPanel.add(emailLabel, gbc);

        gbc.gridy = 4;
        JLabel passwordLabel = new JLabel("Password:");
        rightPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        nameField = new JTextField(20);
        rightPanel.add(nameField, gbc);

        gbc.gridy = 2;
        nicknameField = new JTextField(20);
        rightPanel.add(nicknameField, gbc);

        gbc.gridy = 3;
        emailField = new JTextField(20);
        rightPanel.add(emailField, gbc);

        gbc.gridy = 4;
        passwordField = new JPasswordField(20);
        rightPanel.add(passwordField, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton registroButton = new JButton("Registrar");
        rightPanel.add(registroButton, gbc);

        gbc.gridy = 6;
        JLabel loginLabel = new JLabel("<html><u>Já possuo login</u></html>");
        loginLabel.setForeground(Color.BLUE);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        rightPanel.add(loginLabel, gbc);

        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Ação a ser executada quando o link for clicado
                // Criar uma nova instância da classe de interface desejada e torná-la visível
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.setVisible(true);
                dispose(); // Fecha a janela atual (RegistroGUI)

            }
        });

        registroButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Usuario temp = new Usuario(emailField.getText(), passwordField.getText(), nameField.getText(), nicknameField.getText());
                temp.registrarUsuario(temp);
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.setVisible(true);
                dispose(); // Fecha a janela atual (RegistroGUI)
            }
        });

        splitPane.setRightComponent(rightPanel);

        panel.add(splitPane, BorderLayout.CENTER);

        add(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistroGUI());
    }

}
