package src;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class gameAction {
    public static void addGame(Game game){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", game.getName());
        jsonObject.put("email", game.getDescricao());
        jsonObject.put("senha", game.getPrice());

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
    public static void deleteGame(){};
}
