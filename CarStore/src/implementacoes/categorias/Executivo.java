package implementacoes.categorias;

import java.rmi.RemoteException;

import implementacoes.CarroImpl;

public class Executivo extends CarroImpl {
    private String nome;
    private String renavan;
    private String categoria = "Executivo";
    private int ano;
    private double preco;
    private boolean vendido;
    private int quantidadeDisponivel;

    public Executivo(){
      
    }

    public Executivo(String nome, String renavan, String categoria, int ano, double preco, 
    boolean vendido, int quantidadeDisponivel) {
        this.nome = nome;
        this.renavan = renavan;
        this.categoria = "Executivo";
        this.ano = ano;
        this.preco = preco;
        this.vendido = false;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    @Override
    public String toString() {
        return "Carro [nome=" + nome + ", renavan=" + renavan + ", categoria=" + categoria + 
        ", ano=" + ano + ", preco=" + preco + ", vendido=" + vendido + 
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
    public void setNome(String nome) throws RemoteException {
       this.nome = nome;
    }
    
    @Override
    public void setRenavan(String renavan) throws RemoteException {
       this.renavan = renavan;
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