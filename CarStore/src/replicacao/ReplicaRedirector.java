package replicacao;

import interfaces.Carro;
import interfaces.Loja;
import usuarios.Cliente;
import usuarios.Funcionario;
import usuarios.User;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import implementacoes.CarroImpl;

public class ReplicaRedirector implements Loja {
    private Loja[] replicas;
    private int currentReplicaIndex = 0;
    private List<CarroImpl> carros;
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Funcionario> funcionarios = new ArrayList<>();
    private String arquivo = "D:/Users/vitor/git/Car-Store-Client-Server/CarStore/src/arquivos/carros.txt";


    public ReplicaRedirector(Loja[] replicas) {
        this.replicas = replicas;
        this.currentReplicaIndex = 0;

         // adiocina clientes 
         clientes.add(new Cliente("Vitor", "12345"));
         clientes.add(new Cliente("Pedro", "senha"));
 
         // adiocina funcionarios 
         funcionarios.add(new Funcionario("Paulo", "senha"));
         funcionarios.add(new Funcionario("Joao", "12345"));
          
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
    public CarroImpl adicionarCarro(String renavan, String nome, String categoria, int ano, double preco) throws RemoteException{
        CarroImpl novoCarro = new CarroImpl(nome, renavan, categoria, ano, preco, 1);
        carros.add(novoCarro);
        
        CarroImpl teste = replicas[currentReplicaIndex].adicionarCarro(renavan, nome, categoria, ano, preco);
        
        System.out.println("\nCarro adicionado: " + teste.getNome());
        exibirQuantidadeCarros();
        
        // Atualiza o índice para a próxima réplica (Round Robin)
        currentReplicaIndex++;
        if (currentReplicaIndex >= replicas.length) {
            currentReplicaIndex = 0;
        }
   
        System.out.println("Replica Utilizada = " + currentReplicaIndex);

        return teste;
    }

    @Override
    public CarroImpl apagarCarro(String nomeCarro) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'apagarCarro'");
    }

    @Override
    public List<Carro> listarCarros(int chave) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarCarros'");
    }

    @Override
    public CarroImpl pesquisarCarro(String chave) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pesquisarCarro'");
    }

    @Override
    public CarroImpl alterarAtributos(String chave, String renavanAlte, String nomeAlte, String categoriaAlte,
            int anoAlte, double precoAlte, int qauntAlte) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'alterarAtributos'");
    }

    @Override
    public int exibirQuantidadeCarros() throws RemoteException {
      // Redireciona a chamada para a réplica adequada
      int teste = replicas[currentReplicaIndex].exibirQuantidadeCarros();

      // Atualiza o índice para a próxima réplica (Round Robin)
      currentReplicaIndex++;
      if (currentReplicaIndex >= replicas.length) {
          currentReplicaIndex = 0;
      }
      System.out.println("Replica Utilizada = " + currentReplicaIndex);
      return teste;
    }

    @Override
    public boolean comprarCarro(String nomeCarro) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'comprarCarro'");
    }

    @Override
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

    @Override
    public void escreverCarrosEmArquivo(String nomeArquivo) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'escreverCarrosEmArquivo'");
    }
}

