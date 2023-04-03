package teste2.imple;

import java.rmi.RemoteException;

import teste2.interfaces.Carro;

public class CarroImpl implements Carro {
    private String nome;
    private String renavan;
    private String cor;
    private String categoria;
    private int ano;
    private double preco;
    private boolean vendido;
    private int quantidadeDisponivel;

    public CarroImpl(){
      
    }

    public CarroImpl(String nome, String renavan, String cor, String categoria, int ano, double preco, 
    boolean vendido, int quantidadeDisponivel) {
        this.nome = nome;
        this.renavan = renavan;
        this.cor = cor;
        this.categoria = categoria;
        this.ano = ano;
        this.preco = preco;
        this.vendido = false;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    @Override
    public String toString() {
        return "Carro [nome=" + nome + ", cor=" + cor + ", renavan=" + renavan + ", cor=" + cor + 
        ", categoria=" + categoria + ", ano=" + ano + ", preco=" + preco + ", vendido=" + vendido + 
        ", quantidade disponivel=" + quantidadeDisponivel + "]";
    }


    @Override
    public String getRenavan() throws RemoteException {
       return renavan;
    }


    @Override
    public String getNome() throws RemoteException {
        return nome;
    }


    @Override
    public String getCategoria() throws RemoteException {
        return categoria;
    }


    @Override
    public int getAnoFabricacao() throws RemoteException {
        return ano;
    }


    @Override
    public double getPreco() throws RemoteException {
       return preco;
    }


    @Override
    public int getQuantidadeDisponivel() throws RemoteException {
        return quantidadeDisponivel;
    }

    @Override
    public String getCor() throws RemoteException {
       return cor;
    }

    @Override
    public void setNome(String nome) throws RemoteException {
       this.nome = nome;
    }
    
    @Override
    public void setRenavan(String renavan) throws RemoteException {
       this.renavan = renavan;
    }

    @Override
    public void setCor(String cor) throws RemoteException {
       this.cor = cor;
    }

    @Override
    public void setCategoria(String categoria) throws RemoteException {
       this.categoria = categoria;
    }

    @Override
    public void setAno(int ano) throws RemoteException {
       this.ano = ano;
    }

    @Override
    public void setPreco(double preco) throws RemoteException {
       this.preco = preco;
    }

    @Override
    public void setVendido(boolean vendido) throws RemoteException {
       this.vendido = vendido;
    }

    @Override
    public void setQuantidadeDisponivel(int quantidadeDisponivel) throws RemoteException {
       this.quantidadeDisponivel = quantidadeDisponivel;
    }
}
