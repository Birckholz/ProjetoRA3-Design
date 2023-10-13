package src;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

                JButton undoButton = new JButton("Desfazer");
                undoButton.setBackground(Color.DARK_GRAY);
                undoButton.setForeground(Color.BLACK);
                undoButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Clicka");
                        undoPreviousChange(session.getInt("mementoId"));
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
                setSize(500, 500);
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
                gbc.gridy = 6;
                gbc.insets = new Insets(10, 0, 0, 0);
                add(goBackButton, gbc);

                gbc.gridx = 1;
                gbc.gridy = 6;
                gbc.insets = new Insets(10, 10, 0, 0);
                add(saveButton, gbc);

                gbc.gridx = 2;
                gbc.insets = new Insets(10, 10, 0, 0);
                add(undoButton, gbc);

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
                    findUserAndUpdate(session.getInt("mementoId"), session);
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
    public void undoPreviousChange(int targetMementoId) {
        try {
            String fileContentUser = new String(Files.readAllBytes(Paths.get("src/usuarios.json")));
            String fileContentMemento = new String(Files.readAllBytes(Paths.get("src/usuarioMemento.json")));
            JSONArray userListMemento = new JSONArray(fileContentMemento);
            JSONArray userListUser = new JSONArray(fileContentUser);
            JSONObject temp = null;
            int currentMementoId = 0;
            for (int i = 0; i < userListMemento.length(); i++) {
                JSONObject userMemento = userListMemento.getJSONObject(i);
                int mementoId = userMemento.getInt("mementoId");
                currentMementoId = i;
                if (mementoId == targetMementoId) {
                    for (int j = 0; j < userListUser.length(); j++) {
                        JSONObject user = userListUser.getJSONObject(j);
                        temp = new JSONObject(user.toString());;
                        int mementoIdNew = user.getInt("mementoId");
                        if (mementoIdNew == targetMementoId) {
                            user.put("name", userMemento.getString("name"));
                            user.put("senha", userMemento.getString("senha"));
                            user.put("biblioteca", userMemento.getJSONArray("biblioteca"));
                            user.put("imagePath", userMemento.getString("imagePath"));
                            user.put("mementoId", userMemento.getInt("mementoId"));
                            user.put("username", userMemento.getString("username"));
                            user.put("email", userMemento.getString("email"));
                            userListMemento.put(i, new JSONObject(temp.toString()));
                            String updatedDataM = userListMemento.toString(4);
                            Files.write(Paths.get("src/usuarioMemento.json"), updatedDataM.getBytes());
                            String updatedData = userListUser.toString(4);
                            Files.write(Paths.get("src/usuarios.json"), updatedData.getBytes());
                            updateSession(user);
                            updateFieldPlaceholders();

                            break;
                        }
                    }
                }
            }
//            findUserAndUpdate(targetMementoId, temp);
//            userListMemento.remove(currentMementoId);
//            String updateDataM = userListMemento.toString(4);
//            Files.write(Paths.get("src/usuarioMemento.json"), updateDataM.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void findUserAndUpdate(int targetMementoId, JSONObject Atual) {
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get("src/usuarioMemento.json")));
            JSONArray userList = new JSONArray(fileContent);

            boolean userFound = false;
            for (int i = 0; i < userList.length(); i++) {
                JSONObject user = userList.getJSONObject(i);
                int mementoId = user.getInt("mementoId");
                if (mementoId == targetMementoId) {
                    user.put("name", Atual.getString("name"));
                    user.put("senha", Atual.getString("senha"));
                    user.put("biblioteca", Atual.getJSONArray("biblioteca"));
                    user.put("imagePath", Atual.getString("imagePath"));
                    user.put("mementoId", Atual.getInt("mementoId"));
                    user.put("username", Atual.getString("username"));
                    user.put("email", Atual.getString("email"));
                    String updatedData = userList.toString(4);
                    userFound = true;
                    Files.write(Paths.get("src/usuarioMemento.json"), updatedData.getBytes());
                    break;
                }
            }
            if (!userFound) {
                userList.put(Atual);
                String updatedData = userList.toString(4);
                Files.write(Paths.get("src/usuarioMemento.json"), updatedData.getBytes());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void updateFieldPlaceholders() {
        nameField.setText(session.getString("name"));
        senhaField.setText(session.getString("senha"));
        usernameField.setText(session.getString("username"));
        emailField.setText(session.getString("email"));
    }

    public void updateSession(JSONObject temp) {
        session.put("name", temp.getString("name"));
        session.put("username", temp.getString("username"));
        session.put("senha", temp.getString("senha"));
        session.put("mementoId", temp.getInt("mementoId"));
        session.put("imagePath", temp.getString("imagePath"));
        session.put("email", temp.getString("email"));
        session.put("biblioteca", temp.getJSONArray("biblioteca"));
    }
    public static void main(String[] args) {
        JSONObject session = new JSONObject();
        new profileEditGUI(session);
    }
}
