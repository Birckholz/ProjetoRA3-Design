package src;

import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LoginAction {
    public static JSONObject realizarLogin(String identifier, String code) {
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get("src/usuarios.json")));
            JSONArray jsonArray;
            jsonArray = new JSONArray(fileContent);
            for (Object item : jsonArray) {
                if (item instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) item;

                    if (identifier.equals(jsonObject.getString("email")) || identifier.equals(jsonObject.getString("username"))) {
                        if (jsonObject.getString("senha").equals(code)) {
                            System.out.println("Logado");
                            return jsonObject;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

}
