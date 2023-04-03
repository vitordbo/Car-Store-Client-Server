package teste2.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import teste2.imple.CarroImpl;

public interface Loja extends Remote {
    public void listarCarros() throws RemoteException;
    public CarroImpl pesquisarCarro(String chave) throws RemoteException;
    public int exibirQuantidadeCarros() throws RemoteException;
    public boolean comprarCarro(String nomeCarro) throws RemoteException;
}

