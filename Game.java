package src;

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
}
