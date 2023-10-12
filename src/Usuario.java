package src;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Usuario extends UserBase{
    private String name;
    private String username;
    private String email;
    private String password;
    private JSONArray biblioteca;
    private String profilePic;

    public Usuario(String email, String password, String name, String username){
        super(name,email, password);
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.profilePic = profilePic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public JSONArray getBiblioteca() {
        return biblioteca;
    }
    @Override
    public void registrarUsuario(Usuario user) {
            JSONArray biblioteca = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", user.getName());
            jsonObject.put("email", user.getEmail());
            jsonObject.put("senha", user.getPassword());
            jsonObject.put("username", user.getUsername());
            jsonObject.put("biblioteca", biblioteca);
            jsonObject.put("imagePath", "");

            String filePath = "src/usuarios.json";

            try {
                String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
                JSONArray jsonArray;
                jsonArray = new JSONArray(fileContent);
                jsonArray.put(jsonObject);

                FileWriter escrever = new FileWriter(filePath);

                escrever.write(jsonArray.toString());

                escrever.close();

                System.out.println("Data written to the JSON file successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
}
