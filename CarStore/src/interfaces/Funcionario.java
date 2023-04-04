package interfaces;

import java.rmi.RemoteException;

public interface Funcionario extends Cliente {
    public void adicionarCarro(String nome, String renavan, String categoria, int ano,
     double preco, boolean vendido, int quantidadeDisponivel) throws RemoteException;
    public void apagarCarro(String nome) throws RemoteException;
    public void alterarCarro(String nome, String renavan, String categoria, int ano,
    double preco, boolean vendido, int quantidadeDisponivel) throws RemoteException;
    public void atualizarListagemCarros() throws RemoteException;
}
