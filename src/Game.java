package src;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Game {
    private String name;
    private String descricao;
    private Double price;
    private String directory;
            
    public Game(String name, String descricao, Double preco, String directory) {
        this.name = name;
        this.descricao = descricao;
        this.price = preco;
        this.directory = directory;
    }
    
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getDirectory() {
        return directory;
    }

    public Double getPrice() { return price; }

    public void setPrice(Double price) { this.price = price; }
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
    }
}
