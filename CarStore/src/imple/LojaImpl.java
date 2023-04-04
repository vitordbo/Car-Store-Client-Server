package imple;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import interfaces.Carro;
import interfaces.Loja;

public class LojaImpl extends UnicastRemoteObject implements Loja {

    private List<CarroImpl> carros;
    private List<ClienteImpl> clientes;
    private List<FuncionarioImpl> funcionarios;

    public LojaImpl() throws RemoteException {
        carros = new ArrayList<CarroImpl>();
        clientes = new ArrayList<ClienteImpl>();
        funcionarios = new ArrayList<FuncionarioImpl>();

        // Adiciona 3 carros de cada tipo no início da execução
        carros.add(new CarroImpl("Fiat Novo Uno", "988911891", "Econômico", 2020, 35000.0, false , 2));
        carros.add(new CarroImpl("Chevrolet Onix", "987654321", "Econômico", 2019, 40000.0, false, 3));
        carros.add(new CarroImpl("Ford Ka", "567891234", "Econômico", 2010, 22000.0, false, 1));

        carros.add(new CarroImpl("Ford Ka Sedan", "432156789", "Intermediário", 2020, 45000.0, false, 5));
        carros.add(new CarroImpl("Chevrolet Onix Plus", "789123456", "Intermediário", 2019, 50000.0, false, 1));
        carros.add(new CarroImpl("Hyundai HB20S", "345678912", "Intermediário", 2021, 68000.0, false, 2));

        carros.add(new CarroImpl("Toyota Corolla", "456789123", "Executivo", 2023, 150000.0, false, 3));
        carros.add(new CarroImpl("Honda Civic", "321654987", "Executivo", 2021, 185000.0, false, 2));
        carros.add(new CarroImpl("Chevrolet Cruze", "987123654", "Executivo", 2019, 160000.0, false, 1));
    }


    /* 1.
     * Adicionar Carro
     *  Um usuário pode adicionar carros ao
     *  sistema da loja. Para adicionar, os
     *  seguintes atributos são fornecidos:
     *  renavan, nome, categoria, ano de
     *  fabricação e preço. 
     *  Atualizar quantidade disponível.
    */
    @Override
    public void adicionarCarro(String renavan, String nome, String categoria, int ano, double preco) throws RemoteException {
        CarroImpl novoCarro = new CarroImpl(nome, renavan, categoria, ano, preco, false, 1);
        carros.add(novoCarro);
        
        System.out.println("\nCarro adicionado: " + novoCarro.getNome());
        exibirQuantidadeCarros();
    }


    /* 2.
     * Apagar carro
     * Um usuário pode apagar registros de
     *  carros da loja. Todos os atributos são
     *  removidos a partir do nome do carro ou
     *  quando a quantidade disponível chegar
     *  em zero. 
    */    
    @Override
    public void apagarCarro(String nomeCarro) throws RemoteException {
        Iterator<CarroImpl> iter = carros.iterator(); // Iterator para não dar erro
        while (iter.hasNext()) {
            CarroImpl carro = iter.next();
            if (carro.getNome().equalsIgnoreCase(nomeCarro)) {
                iter.remove();
                System.out.println("Carro removido: " + nomeCarro);
                exibirQuantidadeCarros();
                return;
            }
        }
        System.out.println("\nNão foi encontrado nenhum carro com o nome " + nomeCarro + ".");
        System.out.println("-------------------------");
    }
    

    /* 3.
     * Listar carros
     * Um usuário pode listar os carros da loja
     *  (com todos os atributos). A listagem pode
     *  ocorrer por categoria ou de forma geral e
     *  deve ser apresentada em ordem
     *  alfabética dos nomes.  
    */
    @Override
    public void listarCarros() throws RemoteException { // de forma geral => ordem alfabetica dos nomes
        System.out.println("Carros disponiveis em ordem alfabetica = \n-------------------------");
        Collections.sort(carros, (c1, c2) -> { // collection para deixar em ordem alfabetica
            try {
                return c1.getNome().compareTo(c2.getNome());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return 0;
        });
        for (Carro carro : carros) { // todos os atributos
            System.out.println("Nome = " + carro.getNome() + ", Renavan = " + carro.getRenavan() + 
            ", Categoria = " + carro.getCategoria() + ", Ano = " + carro.getAnoFabricacao() + 
            ", Preço = " + carro.getPreco() + ", Quantidade disponível = " + carro.getQuantidadeDisponivel());
        }
        System.out.println("-------------------------");
    }

    /* 4.
     * Pesquisar (consultar) carro
     *  Um usuário pode realizar uma busca por
     *  carro a partir de seu nome ou do renavan.
    */
    @Override
    public CarroImpl pesquisarCarro(String chave) throws RemoteException { // nome ou Renavam
        for (CarroImpl carro : this.carros) {
            if (carro.getNome().equalsIgnoreCase(chave) || carro.getRenavan().equalsIgnoreCase(chave)) {
                System.out.println("Carro encontrado!!" + "\nNome = " + carro.getNome() + ", Renavan = " + carro.getRenavan() + 
                ", Categoria = " + carro.getCategoria() + ", Ano = " + carro.getAnoFabricacao() + 
                ", Preço = " + carro.getPreco() + ", Quantidade disponível = " + carro.getQuantidadeDisponivel());
                System.out.println("-------------------------");
                return carro;
            }
        }
        System.out.println("Carro não encontrado!");
        System.out.println("-------------------------");
        return null;
    }

    /* 5.
     * Alterar atributos de carros
     * Um usuário pode alterar atributos de
     * carros armazenados. Exemplo: um
     * cadastro pode ter sido feito de forma
     * errada (nome ou data de fabricação
     * incorretos, etc).
    */
    @Override 
    public CarroImpl alterarAtributos(String chave, String renavanAlte, String nomeAlte, String categoriaAlte, int anoAlte, double precoAlte, int qauntAlte) throws RemoteException { 
        for (CarroImpl carro : this.carros) {
            if (carro.getNome().equalsIgnoreCase(chave) || carro.getRenavan().equalsIgnoreCase(chave)) {
                carro.setNome(nomeAlte);
                carro.setAno(anoAlte);
                carro.setCategoria(categoriaAlte);
                carro.setPreco(precoAlte);
                carro.setQuantidadeDisponivel(qauntAlte);
                carro.setRenavan(renavanAlte);
                
                System.out.println("Carro alterado: " + nomeAlte);
                exibirQuantidadeCarros();
                return carro;
            }
        }
        return null;
    }


    /* 6.
     * Atualizar listagem de carros enviada aos clientes conectados
     * Adicionar, apagar e alterar atributos de
     * carros são operações que fazem com que
     * o servidor tenha que atualizar os clientes
    */

    /* 7.
     * Exibir quantidade de carros
     * Um usuário pode consultar o sistema
     * para saber quantos carros estão
     * armazenados em um dado momento. 
    */
    @Override
    public int exibirQuantidadeCarros() throws RemoteException {
        System.out.println("Quantidade de carros = " + carros.size());
        System.out.println("-------------------------");
        return carros.size();
    }

    /* 8.
     * Um usuário pode efetuar a compra de um
     * carro após consulta e análise de preço. 
    */
    @Override
    public boolean comprarCarro(String nomeCarro) throws RemoteException {
        Carro carro = null;
        for (Carro c : carros) {
            if (c.getNome().equals(nomeCarro)) {
                carro = c;
                break;
            }
        }
        if (carro == null) {
            System.out.println("Carro não encontrado na loja");
            return false; // carro não encontrado na loja
        }
        carro.setQuantidadeDisponivel(carro.getQuantidadeDisponivel() - 1); // diminui 1 da quant disponivel
        if (carro.getQuantidadeDisponivel() == 0) { // se diminuir 1 e ficar com 0 => remove da lista  
            carros.remove(carro); //=> se ficar maior que 0 => nao remove, só diminui
            System.out.println("Carro removido: " + carro.getNome());
        }
        System.out.println("Carro com mais de uma Unidade, Quantidade disponível de " + carro.getNome() + " Depois da remoção: " + carro.getQuantidadeDisponivel());
        System.out.println("-------------------------");
        return true;
    }

}
