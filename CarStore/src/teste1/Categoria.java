import java.util.ArrayList;
import java.util.List;

public class Categoria {
    private String nome;
    private List<Carro> carros;

    public Categoria(String nome) {
        this.nome = nome;
        this.carros = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void adicionarCarro(Carro carro) {
        carros.add(carro);
    }

    public List<Carro> getCarros() {
        return carros;
    }
}
