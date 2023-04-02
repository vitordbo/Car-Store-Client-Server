import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Loja {
    private List<Carro> carros;

    public Loja() {
        carros = new ArrayList<>();
    }

    public void adicionarCarro(Carro carro) {
        carros.add(carro);
    }

    public void apagarCarro(String nome) {
        carros.removeIf(carro -> carro.getNome().equalsIgnoreCase(nome) || carro.getQuantidadeDisponivel() == 0);
    }

    public List<Carro> listarCarros() {
        List<Carro> carrosOrdenados = new ArrayList<>(carros);
        Collections.sort(carrosOrdenados, Comparator.comparing(Carro::getNome));
        return carrosOrdenados;
    }

    public List<Carro> listarCarrosPorCategoria(Categoria categoria) {
        List<Carro> carrosPorCategoria = new ArrayList<>();
        for (Carro carro : carros) {
            if (carro.getCategoria() == categoria) {
                carrosPorCategoria.add(carro);
            }
        }
        return carrosPorCategoria;
    }

    public Carro pesquisarCarro(String nome) {
        for (Carro carro : carros) {
            if (carro.getNome().equalsIgnoreCase(nome) || carro.getRenavan().equalsIgnoreCase(nome)) {
                return carro;
            }
        }
        return null;
    }

    public void alterarAtributosCarro(Carro carro) {
        for (int i = 0; i < carros.size(); i++) {
            if (carros.get(i).getRenavan().equalsIgnoreCase(carro.getRenavan())) {
                carros.set(i, carro);
                break;
            }
        }
    }

    public int exibirQuantidadeCarros() {
        int quantidade = 0;
        for (Carro carro : carros) {
            quantidade += carro.getQuantidadeDisponivel();
        }
        return quantidade;
    }
}
