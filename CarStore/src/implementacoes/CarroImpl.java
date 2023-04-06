package implementacoes;

import java.io.Serializable;
import java.rmi.RemoteException;

import interfaces.Carro;

public class CarroImpl implements Carro, Serializable {
   private String nome;
   private String renavan;
   private String categoria;
   private int ano;
   private double preco;
   private int quantidadeDisponivel;

   public CarroImpl(){
   
   }

   public CarroImpl(String nome, String renavan, String categoria, int ano, double preco, 
   int quantidadeDisponivel) {
      this.nome = nome;
      this.renavan = renavan;
      this.categoria = categoria;
      this.ano = ano;
      this.preco = preco;
      this.quantidadeDisponivel = quantidadeDisponivel;
   }

   @Override
   public String toString() {
      return "Nome=" + nome + ", renavan=" + renavan + ", categoria=" + categoria + 
      ", ano=" + ano + ", preco=" + preco + ", quantidade disponivel=" + quantidadeDisponivel;
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
   public void setQuantidadeDisponivel(int quantidadeDisponivel) throws RemoteException {
      this.quantidadeDisponivel = quantidadeDisponivel;
   }
}
