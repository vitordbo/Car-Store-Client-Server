package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Cliente extends Remote {
    public void listarCarros(String categoria) throws RemoteException;
    public void pesquisarCarro(String chave) throws RemoteException;
    public void exibirQuantidadeCarros() throws RemoteException;
    public void comprarCarro(String nomeCarro, int quantidade) throws RemoteException;
}

