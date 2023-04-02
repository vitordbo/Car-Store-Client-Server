package teste2.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Carro extends Remote {
    public String getRenavan() throws RemoteException;
    public String getNome() throws RemoteException;
    public String getCategoria() throws RemoteException;
    public int getAnoFabricacao() throws RemoteException;
    public double getPreco() throws RemoteException;
    public int getQuantidadeDisponivel() throws RemoteException;
    public String getCor() throws RemoteException;

    public void setNome(String nome) throws RemoteException;
    public void setRenavan(String renavan) throws RemoteException;
    public void setCor(String cor) throws RemoteException;
    public void setCategoria(String categoria) throws RemoteException;
    public void setAno(int ano) throws RemoteException;
    public void setPreco(double preco) throws RemoteException;
    public void setVendido(boolean vendido) throws RemoteException;
    public void setQuantidadeDisponivel(int quantidadeDisponivel) throws RemoteException;

}
