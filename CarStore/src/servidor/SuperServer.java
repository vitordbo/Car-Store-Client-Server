package servidor;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import usuarios.Cliente;
import implementacoes.CarroImpl;
import implementacoes.LojaReplicadaImpl;
import interfaces.Carro;
import interfaces.Loja;
import usuarios.Funcionario;
import usuarios.User;

public class SuperServer implements Loja {
    private List<LojaReplicadaImpl> replicas;
    private int indiceAtual;
    private static final long serialVersionUID = 1L;
    private List<CarroImpl> carros;
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Funcionario> funcionarios = new ArrayList<>();
    private static String arquivo = "D:/Users/vitor/git/Car-Store-Client-Server/CarStore/src/arquivos/carros.txt";
    private int indiceLider;

    public SuperServer() {
        // adiocina clientes 
        clientes.add(new Cliente("Vitor", "12345"));
        clientes.add(new Cliente("Pedro", "senha"));

        // adiocina funcionarios 
        funcionarios.add(new Funcionario("Paulo", "senha"));
        funcionarios.add(new Funcionario("Joao", "12345"));
        
        carros = new ArrayList<CarroImpl>();
        lerCarrosDoArquivo();

        replicas = new ArrayList<>();
        replicas.add(new LojaReplicadaImpl("D:/Users/vitor/git/Car-Store-Client-Server/CarStore/src/arquivos/carros.txt"));
        replicas.add(new LojaReplicadaImpl("D:/Users/vitor/git/Car-Store-Client-Server/CarStore/src/arquivos/carros.txt"));
        replicas.add(new LojaReplicadaImpl("D:/Users/vitor/git/Car-Store-Client-Server/CarStore/src/arquivos/carros.txt"));
        indiceAtual = 0;
        indiceLider = -1;
    }

    public String obterProximaReplica() {
        LojaReplicadaImpl replica = replicas.get(indiceAtual);
        indiceAtual = (indiceAtual + 1) % replicas.size(); // Avança para a próxima réplica circularmente
        try {
            return replica.obterProximaReplica();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            LojaReplicadaImpl servidor = new LojaReplicadaImpl("D:/Users/vitor/git/Car-Store-Client-Server/CarStore/src/arquivos/carros.txt");
            Loja skeleton = (Loja) UnicastRemoteObject.exportObject(servidor, 0);

            LocateRegistry.createRegistry(20003);
            Registry registro = LocateRegistry.getRegistry(20003);
            
            // The bind method is called on the registry stub to bind the remote object stub to the name "LojaTeste" in the registry.
            registro.bind("LojaTeste", skeleton);
            System.err.println("Super servidor pronto!");

        } catch (Exception e) {
            System.err.println("Servidor: " + e.toString());
            e.printStackTrace();
        }
    }

    public int getIndiceReplicaAtual() {
        return indiceAtual;
    }    

    public void mostraReplica(){
        SuperServer servidor = new SuperServer();
        int indice = servidor.getIndiceReplicaAtual();
        System.out.println("Réplica atual: " + indice);        
    }
    

    private void lerCarrosDoArquivo() {
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
    public CarroImpl adicionarCarro(String renavan, String nome, String categoria, int ano, double preco) throws RemoteException{
        CarroImpl novoCarro = new CarroImpl(nome, renavan, categoria, ano, preco, 1);
        carros.add(novoCarro);
        
        System.out.println("\nCarro adicionado: " + novoCarro.getNome());
        exibirQuantidadeCarros();
        return novoCarro;
    }

    @Override
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

    @Override
    public List<Carro> listarCarros(int chave) throws RemoteException {
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

    @Override
    public CarroImpl pesquisarCarro(String chave) throws RemoteException {
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

    @Override
    public CarroImpl alterarAtributos(String chave, String renavanAlte, String nomeAlte, String categoriaAlte,
            int anoAlte, double precoAlte, int qauntAlte) throws RemoteException {
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

    @Override
    public User autenticar(String login, String senha) throws RemoteException {
        // Verifica se o login e a senha correspondem a um cliente
        for (Cliente cliente : clientes ) {
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