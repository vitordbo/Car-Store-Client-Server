package teste2.imple;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import teste2.interfaces.Cliente;

public class ClienteImpl extends UnicastRemoteObject implements Cliente {
    private static final long serialVersionUID = 1L;
    private Map<String, CarroImpl> carrosDisponiveis;
    private String nome;
    private String email;
    private String senha;
    private ArrayList<Cliente> listaClientes;

    public ClienteImpl(String nome, String email, String senha) throws RemoteException {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        listaClientes = new ArrayList<>();
    }

    public ClienteImpl() throws RemoteException {
        super();
        carrosDisponiveis = new HashMap<>();
    }  

    @Override
    public void listarCarros(String categoria) throws RemoteException {
        System.out.println("Listando carros da categoria " + categoria + ":");
        for (CarroImpl carro : carrosDisponiveis.values()) {
            if (carro.getCategoria().equals(categoria)) {
                System.out.println(carro.getNome() + " - " + carro.getPreco() + " - " + carro.getQuantidadeDisponivel() + " disponíveis");
            }
        }
    }

    @Override
    public void pesquisarCarro(String chave) throws RemoteException {
        System.out.println("Pesquisando carro com a chave " + chave + ":");
        for (CarroImpl carro : carrosDisponiveis.values()) {
            if (carro.getNome().contains(chave)) {
                System.out.println(carro.getNome() + " - " + carro.getPreco() + " - " + carro.getQuantidadeDisponivel() + " disponíveis");
            }
        }
    }

    @Override
    public void exibirQuantidadeCarros() throws RemoteException {
        int quantidadeTotal = 0;
        for (CarroImpl carro : carrosDisponiveis.values()) {
            quantidadeTotal += carro.getQuantidadeDisponivel();
        }
        System.out.println("Total de carros disponíveis na loja: " + quantidadeTotal);
    }

    @Override
    public void comprarCarro(String nomeCarro, int quantidade) throws RemoteException {
        CarroImpl carroSelecionado = carrosDisponiveis.get(nomeCarro);
        if (carroSelecionado != null && carroSelecionado.getQuantidadeDisponivel() >= quantidade) {
            carroSelecionado.setQuantidadeDisponivel(carroSelecionado.getQuantidadeDisponivel() - quantidade);
            System.out.println(quantidade + " unidades de " + nomeCarro + " compradas com sucesso!");
        } else {
            System.out.println("Não foi possível comprar " + quantidade + " unidades de " + nomeCarro + ".");
        }
    }

    public void setCarrosDisponiveis(Map<String, CarroImpl> carrosDisponiveis) {
        this.carrosDisponiveis = carrosDisponiveis;
    }
}
