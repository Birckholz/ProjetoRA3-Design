package src;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class UserBase{
    private String name;
    private String password;
    private String email;

    public UserBase(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSenha() {
        return password;
    }

    public void setSenha(String senha) {
        this.password = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public abstract void registrarUsuario(Usuario user);
}
