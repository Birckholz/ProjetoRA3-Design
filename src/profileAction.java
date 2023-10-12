package src;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class profileAction {
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
}