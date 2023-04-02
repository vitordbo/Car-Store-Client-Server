import java.util.ArrayList;

public class Funcionario extends Usuario {

    public Funcionario(String nome, String cpf, String senha) {
        super(nome, cpf, senha);
    }

    public boolean adicionarCarro(Carro carro, ArrayList<Carro> carros) {
        if (carros.contains(carro)) {
            System.out.println("O carro já existe na loja.");
            return false;
        } else {
            carros.add(carro);
            System.out.println("Carro adicionado com sucesso.");
            return true;
        }
    }

    public boolean apagarCarro(String nome, ArrayList<Carro> carros) {
        boolean removed = false;
        for (Carro carro : carros) {
            if (carro.getNome().equals(nome)) {
                carros.remove(carro);
                System.out.println("Carro removido com sucesso.");
                removed = true;
                break;
            }
        }
        if (!removed) {
            System.out.println("Não foi possível remover o carro.");
        }
        return removed;
    }

    public boolean alterarAtributosCarro(String nome, String renavan, int anoFabricacao, double preco,
            ArrayList<Carro> carros) {
        boolean updated = false;
        for (Carro carro : carros) {
            if (carro.getNome().equals(nome)) {
                carro.setRenavan(renavan);
                carro.setAnoFabricacao(anoFabricacao);
                carro.setPreco(preco);
                System.out.println("Atributos do carro atualizados com sucesso.");
                updated = true;
                break;
            }
        }
        if (!updated) {
            System.out.println("Não foi possível atualizar os atributos do carro.");
        }
        return updated;
    }
}
