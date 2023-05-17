package implementacoes;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import implementacoes.categorias.Economico;
import implementacoes.categorias.Executivo;
import implementacoes.categorias.Intermediario;
import interfaces.Carro;
import interfaces.Loja;
import usuarios.Cliente;
import usuarios.Funcionario;
import usuarios.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class LojaImpl implements Loja, Serializable {

    private static final long serialVersionUID = 1L;
    private List<CarroImpl> carros;
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Funcionario> funcionarios = new ArrayList<>();
    private String arquivo;

    public LojaImpl(String arquivo){ // passa o arquivo la no servidor
        // adiocina clientes 
        clientes.add(new Cliente("Vitor", "12345"));
        clientes.add(new Cliente("Pedro", "senha"));

        // adiocina funcionarios 
        funcionarios.add(new Funcionario("Paulo", "senha"));
        funcionarios.add(new Funcionario("Joao", "12345"));
        
        this.arquivo = arquivo;
        carros = new ArrayList<CarroImpl>();
        lerCarrosDoArquivo();
    }

    private void lerCarrosDoArquivo(){
        try {
            File arquivoCarros = new File(arquivo);
            Scanner leitor = new Scanner(arquivoCarros);
            while (leitor.hasNextLine()){
                String linha = leitor.nextLine();
                String[] campos = linha.split(",");
                String nome = campos[0];
                String renavan = campos[1];
                String categoria = campos[2];
                int ano = Integer.parseInt(campos[3]);
                double preco = Double.parseDouble(campos[4]);
                int quantidade = Integer.parseInt(campos[5]);
                CarroImpl carro = new CarroImpl(nome, renavan, categoria, ano, preco, quantidade);
                carros.add(carro);
            }
            leitor.close();
        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo");
        }
    }

    @Override
    public void escreverCarrosEmArquivo(String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Carro carro : carros) {
                writer.write(carro.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever carros no arquivo: " + e.getMessage());
        }
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
    @Override // ok pro funcionario 
    public CarroImpl adicionarCarro(String renavan, String nome, String categoria, int ano, double preco) throws RemoteException {
        CarroImpl novoCarro = new CarroImpl(nome, renavan, categoria, ano, preco, 1);
        carros.add(novoCarro);
        
        System.out.println("\nCarro adicionado: " + novoCarro.getNome());
        exibirQuantidadeCarros();
        return novoCarro;
    }

    /* 2.
     * Apagar carro
     * Um usuário pode apagar registros de
     *  carros da loja. Todos os atributos são
     *  removidos a partir do nome do carro ou
     *  quando a quantidade disponível chegar
     *  em zero. 
    */    
    @Override // ok so para funcionario
    public CarroImpl apagarCarro(String nomeCarro) throws RemoteException {
        Iterator<CarroImpl> iter = carros.iterator(); // Iterator para não dar erro
        while (iter.hasNext()) {
            CarroImpl carro = iter.next();
            if (carro.getNome().equalsIgnoreCase(nomeCarro)) {
                iter.remove();
                System.out.println("Carro removido: " + nomeCarro);
                exibirQuantidadeCarros();
                return carro;
            }
        }
        System.out.println("\nNão foi encontrado nenhum carro com o nome " + nomeCarro + ".");
        System.out.println("-------------------------");
        return null;
    }
    

    /* 3.
     * Listar carros
     * Um usuário pode listar os carros da loja
     *  (com todos os atributos). A listagem pode
     *  ocorrer por categoria ou de forma geral e
     *  deve ser apresentada em ordem
     *  alfabética dos nomes.  
    */
    @Override // ok para func e cliente
    public List<Carro> listarCarros(int chave) throws RemoteException { // de forma geral => ordem alfabetica dos nomes
        List<Carro> carrosRetorno = new ArrayList<>();
        if(chave == 0){
            System.out.println("Carros disponiveis por categoria = \n-------------------------");
            Collections.sort(carros, (c1, c2) -> { // collection para deixar em ordem alfabetica
                try {
                    return c1.getCategoria().compareTo(c2.getCategoria());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return 0;
            });
            for (Carro carro : carros) { // todos os atributos
                carrosRetorno.add(carro);
                System.out.println("Nome = " + carro.getNome() + ", Renavan = " + carro.getRenavan() + 
                ", Categoria = " + carro.getCategoria() + ", Ano = " + carro.getAnoFabricacao() + 
                ", Preço = " + carro.getPreco() + ", Quantidade disponível = " + carro.getQuantidadeDisponivel());
            }
            System.out.println("-------------------------");
            return carrosRetorno;
        }
        
        if (chave == 1){
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
                carrosRetorno.add(carro);
                System.out.println("Nome = " + carro.getNome() + ", Renavan = " + carro.getRenavan() + 
                ", Categoria = " + carro.getCategoria() + ", Ano = " + carro.getAnoFabricacao() + 
                ", Preço = " + carro.getPreco() + ", Quantidade disponível = " + carro.getQuantidadeDisponivel());
            }

            System.out.println("-------------------------");
            return carrosRetorno;
        }
        return null; // caso de erro 
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
     * * faz automaticamente
    */

    /* 7.
     * Exibir quantidade de carros
     * Um usuário pode consultar o sistema
     * para saber quantos carros estão
     * armazenados em um dado momento. 
    */
    @Override
    public int exibirQuantidadeCarros() throws RemoteException {
        int quantidadeTotal = 0;

        for (CarroImpl carro : carros){
            quantidadeTotal += carro.getQuantidadeDisponivel();
        }
        System.out.println("Quantidade de carros disponívies = " + quantidadeTotal);
        System.out.println("-------------------------");
        return quantidadeTotal;
    }

    /* 8.
     * Um usuário pode efetuar a compra de um
     * carro após consulta e análise de preço. 
    */
    @Override // ok para funcionario e cliente
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

    @Override
    // metodo autenticar 
    public User autenticar(String login, String senha) throws RemoteException {
        // Verifica se o login e a senha correspondem a um cliente
        for (Cliente cliente : clientes) {
            if (cliente.getLogin().equals(login) && cliente.getSenha().equals(senha)) {
                System.out.println("Autenticação concluida, você é um cliente");
                return cliente;
            }
        }
    
        // Verifica se o login e a senha correspondem a um funcionário
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getLogin().equals(login) && funcionario.getSenha().equals(senha)) {
                System.out.println("Autenticação concluida, você é um funcionario");
                return funcionario;
            }
        }
    
        // Caso não encontre nenhum usuário correspondente
        System.out.println("Seu login ou senha não foi encontrado no nosso sistema");
        return null;
    }
}
