package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Funcionario extends Remote {

    //mesmas do cliente 
    public void listarCarros(String categoria) throws RemoteException;
    public void pesquisarCarro(String chave) throws RemoteException;
    public void exibirQuantidadeCarros() throws RemoteException;
    public void comprarCarro(String nomeCarro, int quantidade) throws RemoteException;

    // extras do funcionario
    public void adicionarCarro(String nome, String renavan, String categoria, int ano,
    double preco, boolean vendido, int quantidadeDisponivel) throws RemoteException;
    
    public void apagarCarro(String nome) throws RemoteException;
    
    public void alterarCarro(String nome, String renavan, String categoria, int ano,
    double preco, boolean vendido, int quantidadeDisponivel) throws RemoteException;

    public void atualizarListagemCarros() throws RemoteException;
}
