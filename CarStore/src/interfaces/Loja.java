package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import implementacoes.CarroImpl;
import usuarios.User;

public interface Loja extends Remote {
    public CarroImpl adicionarCarro(String renavan, String nome, String categoria, int ano, double preco) throws RemoteException;
    public CarroImpl apagarCarro(String nomeCarro) throws RemoteException;
    public List<Carro> listarCarros(int chave) throws RemoteException;
    public CarroImpl pesquisarCarro(String chave) throws RemoteException;
    public CarroImpl alterarAtributos(String chave, String renavanAlte, String nomeAlte, String categoriaAlte, int anoAlte, double precoAlte, int qauntAlte) throws RemoteException;

    public int exibirQuantidadeCarros() throws RemoteException;
    public boolean comprarCarro(String nomeCarro) throws RemoteException;

    // autenticar
    public User autenticar(String login, String senha) throws RemoteException;
    public void escreverCarrosEmArquivo(String nomeArquivo) throws RemoteException;


}

