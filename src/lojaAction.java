package src;

import src.profileAction;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class lojaAction {
    public static boolean comprarGame(JSONObject session, Game game){
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get("src/usuarios.json")));
            JSONArray jsonArray;
            jsonArray = new JSONArray(fileContent);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (session.getString("name").equals(jsonObject.getString("name"))) {
                    JSONArray currentBiblioteca = jsonObject.getJSONArray("biblioteca");
                    if (currentBiblioteca.length() > 0){
                        for (int j = 0; j < currentBiblioteca.length(); j++) {
                            String element = currentBiblioteca.getString(j);
                            if (element.equals(game.getName())) {
                                return false;
                            }
                        }
                    }

                    currentBiblioteca.put(game.getName());
                    jsonObject.put("biblioteca", currentBiblioteca);

                    Files.write(Paths.get("src/usuarios.json"), jsonArray.toString().getBytes());

                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return false;
    }
}
