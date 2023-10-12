package src;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Adm extends UserBase{
    private final boolean admin;

    public Adm(boolean admin, String name, String email, String senha){
        super(name, email, senha);
        this.admin = admin;
    }
        public void registrarUsuario(Usuario user) {
            JSONArray biblioteca = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", user.getName());
            jsonObject.put("email", user.getEmail());
            jsonObject.put("senha", user.getPassword());
            jsonObject.put("username", user.getUsername());
            jsonObject.put("biblioteca", biblioteca);
            jsonObject.put("admin", true);
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
