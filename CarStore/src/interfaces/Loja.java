package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import imple.CarroImpl;

public interface Loja extends Remote {
    public void adicionarCarro(String renavan, String nome, String categoria, int ano, double preco) throws RemoteException;
    public void apagarCarro(String nomeCarro) throws RemoteException;
    public void listarCarros() throws RemoteException;
    public CarroImpl pesquisarCarro(String chave) throws RemoteException;
    public CarroImpl alterarAtributos(String chave, String renavanAlte, String nomeAlte, String categoriaAlte, int anoAlte, double precoAlte, int qauntAlte) throws RemoteException;

    public int exibirQuantidadeCarros() throws RemoteException;
    public boolean comprarCarro(String nomeCarro) throws RemoteException;

}

