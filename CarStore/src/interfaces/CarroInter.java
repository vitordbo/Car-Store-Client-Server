package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CarroInter extends Remote {
    String getRenavan() throws RemoteException;
    String getNome() throws RemoteException;
    String getCategoria() throws RemoteException;
    int getAnoFabricacao() throws RemoteException;
    double getPreco() throws RemoteException;
    int getQuantidadeDisponivel() throws RemoteException;
    void setQuantidadeDisponivel(int quantidade) throws RemoteException;

}