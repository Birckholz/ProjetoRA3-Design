package src;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RegistroGAMEAction {
    public static void registrarJogo(Game game) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("directory", game.getDirectory());
        jsonObject.put("name", game.getName());
        jsonObject.put("description", game.getDescricao());
        jsonObject.put("price", game.getPrice());

        String filePath = "src/games.json";

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
