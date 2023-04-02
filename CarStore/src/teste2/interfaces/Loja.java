package teste2.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import teste2.imple.CarroImpl;

public interface Loja extends Remote {
    public ArrayList<CarroImpl> listarCarros() throws RemoteException;
    public CarroImpl pesquisarCarro(String chave) throws RemoteException;
    public int exibirQuantidadeCarros() throws RemoteException;
    public boolean comprarCarro(String carro, int quantidade) throws RemoteException;
}

