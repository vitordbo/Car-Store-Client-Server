package imple;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import interfaces.Funcionario;

public class FuncionarioImpl extends ClienteImpl implements Funcionario {
    
    private List<CarroImpl> estoque = new ArrayList<>();

    public FuncionarioImpl(String nome, String email, String senha) throws RemoteException {
        super(nome, email, senha);
    }

    public FuncionarioImpl() throws RemoteException {
    }

    @Override
    public void adicionarCarro(String nome, String renavan, String categoria, int ano,
     double preco, boolean vendido, int quantidadeDisponivel) throws RemoteException {
        CarroImpl novoCarro = new CarroImpl(nome, renavan, categoria, ano, preco, vendido, quantidadeDisponivel);
        estoque.add(novoCarro);
        System.out.println("Novo carro adicionado: " + novoCarro.getNome());
    }

    @Override
    public void apagarCarro(String nome) throws RemoteException {
        for (CarroImpl carro : estoque) {
            if (carro.getNome().equals(nome)) {
                estoque.remove(carro);
                return;
            }
        }
        throw new RemoteException("Carro não encontrado no estoque.");
    }

    @Override
    public void alterarCarro(String nome, String renavan, String categoria, int ano,
    double preco, boolean vendido, int quantidadeDisponivel) throws RemoteException {
        for (CarroImpl carro : estoque) {
            if (carro.getNome().equals(nome)) {
                carro.setNome(nome);
                carro.setRenavan(renavan);
                carro.setCategoria(categoria);
                carro.setAno(ano);
                carro.setPreco(preco);
                carro.setVendido(vendido);
                carro.setQuantidadeDisponivel(quantidadeDisponivel);
                return;
            }
        }
        throw new RemoteException("Carro não encontrado no estoque.");
    }

    @Override
    public void atualizarListagemCarros() throws RemoteException {
        System.out.println("Lista de carros:");
        for (CarroImpl carro : estoque) {
            System.out.println(carro.getNome() + " - " + carro.getCategoria() + " - " + carro.getPreco() + " - " + carro.getQuantidadeDisponivel());
        }
    }
}



