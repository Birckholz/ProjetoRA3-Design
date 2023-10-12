package src;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class profileEditGUI extends JFrame {

    private JSONObject session;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField senhaField;
    private JTextField usernameField;
    private JTextField profilePicField;
    private JLabel profilePicLabel;
    private JButton selectFileButton;
    private String selectedFilePath;

    public profileEditGUI(JSONObject session) {
        if (!session.has("name")) {
            JOptionPane.showMessageDialog(null, "Por favor realize login", "No Session", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            return;
        }

        this.session = session;
        try {
            if (session.has("name")) {
                nameField = new JTextField(20);
                emailField = new JTextField(20);
                senhaField = new JTextField(20);
                usernameField = new JTextField(20);
                profilePicField = new JTextField(20);
                profilePicLabel = new JLabel("Foto de Perfil:");
                selectFileButton = new JButton("Select File");
                selectFileButton.setBackground(Color.DARK_GRAY);
                selectFileButton.setForeground(Color.BLACK);
                selectFileButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileChooser = new JFileChooser();
                        int result = fileChooser.showOpenDialog(null);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();
                            selectedFilePath = selectedFile.getAbsolutePath();
                        }
                    }
                });

                JLabel nameLabel = new JLabel("Nome:");
                JLabel emailLabel = new JLabel("Email:");
                JLabel senhaLabel = new JLabel("Senha:");
                JLabel usernameLabel = new JLabel("Username:");

                JButton saveButton = new JButton("Save");
                saveButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Click");
                        saveProfile();
                    }
                });

                saveButton.setBackground(Color.DARK_GRAY);
                saveButton.setForeground(Color.BLACK);

                JButton goBackButton = new JButton("Go Back");
                goBackButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (session.has("admin")) {
                            PerfilAdm perfilAdm = new PerfilAdm(session);
                            perfilAdm.setVisible(true);
                            dispose();
                        } else {
                            Perfil perfil = new Perfil(session);
                            perfil.setVisible(true);
                            dispose();
                        }
                    }
                });
                goBackButton.setBackground(Color.DARK_GRAY);
                goBackButton.setForeground(Color.BLACK);

                setLayout(new GridBagLayout());
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(400, 350);
                getContentPane().setBackground(Color.DARK_GRAY);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);

                gbc.gridx = 0;
                gbc.gridy = 0;
                add(nameLabel, gbc);

                gbc.gridx = 1;
                add(nameField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                add(emailLabel, gbc);

                gbc.gridx = 1;
                add(emailField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                add(senhaLabel, gbc);

                gbc.gridx = 1;
                add(senhaField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 3;
                add(usernameLabel, gbc);

                gbc.gridx = 1;
                add(usernameField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 4;
                add(profilePicLabel, gbc);

                gbc.gridx = 1;
                add(selectFileButton, gbc);

                gbc.gridx = 0;
                gbc.gridy = 5;
                gbc.gridwidth = 2;
                gbc.anchor = GridBagConstraints.CENTER;
                gbc.insets = new Insets(20, 0, 0, 0);
                add(saveButton, gbc);

                gbc.gridy = 6;
                gbc.insets = new Insets(10, 0, 0, 0);
                add(goBackButton, gbc);

                Color blackColor = Color.BLACK;
                nameLabel.setForeground(blackColor);
                emailLabel.setForeground(blackColor);
                senhaLabel.setForeground(blackColor);
                usernameLabel.setForeground(blackColor);
                profilePicLabel.setForeground(blackColor);
                nameField.setForeground(blackColor);
                emailField.setForeground(blackColor);
                senhaField.setForeground(blackColor);
                usernameField.setForeground(blackColor);

                setLocationRelativeTo(null);
                setVisible(true);

                nameField.setText(session.getString("name"));
                senhaField.setText(session.getString("senha"));
                usernameField.setText(session.getString("username"));
                emailField.setText(session.getString("email"));

            } else {
                throw new MyCustomException("Session undefined");
            }

        } catch (MyCustomException e) {
            System.out.println(e.getMessage());
            dispose();
        }
    }

    public void saveProfile() {
        String profilePic = "";
        String name = nameField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();
        String username = usernameField.getText();
        if (selectedFilePath != null) {
            profilePic = selectedFilePath;
        }

        Usuario temp = new Usuario(email, senha, name, username);

        try {
            String fileContent = new String(Files.readAllBytes(Paths.get("src/usuarios.json")));
            JSONArray jsonArray = new JSONArray(fileContent);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (temp.getUsername().equals(jsonObject.getString("username"))) {
                    if (!name.equals(session.getString("name"))) {
                        editarNome(temp, name);
                        jsonObject.put("name", temp.getName());
                        session.put("name", temp.getName());
                    }
                    if (!senha.equals(session.getString("senha"))) {
                        editarSenha(temp, senha);
                        jsonObject.put("senha", temp.getPassword());
                        session.put("senha", temp.getPassword());
                    }
                    if (!email.equals(session.getString("email"))) {
                        editarEmail(temp, email);
                        jsonObject.put("email", temp.getEmail());
                        session.put("email", temp.getEmail());
                    }
                    if (selectedFilePath != null && !selectedFilePath.equals(session.getString("imagePath"))) {
                        String destinationPath = "image/" + temp.getUsername() + ".jpg";

                        Path existingFilePath = Paths.get(destinationPath);
                        if (Files.exists(existingFilePath)) {
                            Files.delete(existingFilePath);
                        }

                        try {
                            Files.copy(Paths.get(selectedFilePath), Paths.get(destinationPath));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        setProfilePic(temp, destinationPath);
                        jsonObject.put("imagePath", destinationPath);
                        session.put("imagePath", destinationPath);
                    }

                    Files.write(Paths.get("src/usuarios.json"), jsonArray.toString().getBytes());
                    System.out.println("Dados Atualizados");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void descartar() {
        dispose();
    }

    public static void editarNome(Usuario user, String newName) {
        user.setName(newName);
    }

    ;

    public static void editarUsername(Usuario user, String newUsername) {
        user.setUsername(newUsername);
    }

    ;

    public static void editarEmail(Usuario user, String newEmail) {
        user.setEmail(newEmail);
    }

    ;

    public static void editarSenha(Usuario user, String newPasscode) {
        user.setPassword(newPasscode);
    }

    ;

    public static void setProfilePic(Usuario user, String pic) {
        user.setProfilePic(pic);
    }

    public static JSONObject findUser(JSONObject session) {
        String username = session.getString("username");
        String email = session.getString("email");
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get("src/usuarios.json")));
            JSONArray jsonArray;
            jsonArray = new JSONArray(fileContent);
            for (Object item : jsonArray) {
                if (item instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) item;

                    if (username.equals(jsonObject.getString("username")) || email.equals(jsonObject.getString("email"))) {
                        return jsonObject;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }
    public static boolean checkGame(JSONObject session, String name){
        JSONObject user = findUser(session);
        JSONArray biblioteca = user.getJSONArray("biblioteca");
        for (Object elemento : biblioteca) {
            String nameGame = (String) elemento;
            if (name.equals(nameGame)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        JSONObject session = new JSONObject();
        new profileEditGUI(session);
    }
}
