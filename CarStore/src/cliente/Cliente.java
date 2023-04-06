package cliente;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import implementacoes.CarroImpl;
import implementacoes.categorias.Economico;
import implementacoes.categorias.Executivo;
import implementacoes.categorias.Intermediario;
import interfaces.Carro;
import interfaces.Loja;
import usuarios.Funcionario;
import usuarios.User;

public class Cliente {
    public static void main(String[] args) {
        try {            
            // pratica 2
            Registry registro = LocateRegistry.getRegistry("localhost", 20003); //Registry.REGISTRY_PORT
            Loja objetoRemoto = (Loja) registro.lookup("Loja");

            // verifica se o objeto registro foi encontrado corretamente
            if (objetoRemoto == null) {
                System.out.println("Não foi possível conectar ao servidor.");
                System.exit(1);
            }

            System.out.println("Bem-vindo à loja de carros!");

            // user e senha para autenticação
            User user;
            String login;

            // tenta autenticar
            do {
                System.out.println("Digite seu Login: ");
                login = System.console().readLine();
                System.out.println("Digite sua senha: ");
                String senha = System.console().readLine();
                
                // autenticar com login e senha => pegar retorno com isntance of e fazer o switch diferente
                user = objetoRemoto.autenticar(login, senha);
                if (user == null){
                    System.out.println("\nNão encontramos seu login ou senha no nosso sistema! Tente novamente: ");
                }
            } while (user == null); // retorna null se não for nada => se for sai do => do While
           

            // pega a instancia de quem está logando para mostrar os metodos corretos
            if (user instanceof usuarios.Cliente){
                System.out.println("Bem vindo, " + login);

                // somente metodos cliente
                int opcao = 0;
                do {
                    System.out.println("\nEscolha uma opção:");
                    System.out.println("1 - Listar carros disponiveis");
                    System.out.println("2 - Pesquisar carro por nome ou renavan");
                    System.out.println("3 - Exibir quantidade de carros disponíveis");
                    System.out.println("4 - Comprar carro");
                    System.out.println("0 - Sair");
    
                    opcao = Integer.parseInt(System.console().readLine());
    
                    Scanner scanner = new Scanner(System.in);
                    switch(opcao) {
                        case 1:  // listar por categoria em ordem alfabetica ou somente em ordem alfabetica
                            System.out.println("\nDigite 0 para listar por categoria ou digite 1 " +
                            "para listar todos em ordem alfabética");
                            int chaveListar = scanner.nextInt();
                            
                            List<Carro> listaRetornadaCliente = new ArrayList<>();
                            listaRetornadaCliente = objetoRemoto.listarCarros(chaveListar);
                            System.out.println("");

                            // mostra para o cliente 
                            for (Carro carro : listaRetornadaCliente) { // todos os atributos
                                System.out.println("Nome = " + carro.getNome() + ", Renavan = " + carro.getRenavan() + 
                                ", Categoria = " + carro.getCategoria() + ", Ano = " + carro.getAnoFabricacao() + 
                                ", Preço = " + carro.getPreco() + ", Quantidade disponível = " + carro.getQuantidadeDisponivel());
                            }
                            break;
                        case 2: // buscar carro por nome ou renavan 
                            System.out.println("\nDigite o nome ou o renavan do carro que deseja pesquisar:");
                            String chave = System.console().readLine();
                            CarroImpl carroPesquisadoCliente = objetoRemoto.pesquisarCarro(chave);

                            // testa se foi achadp mesmo => se não foi retorna null
                            if (carroPesquisadoCliente instanceof CarroImpl){
                                System.out.println("\nCarro achado = " + carroPesquisadoCliente.toString()); // imprimindo pro cliente 
                            }else{
                                System.out.println("\nCarro não encontrado"); // imprimindo pro cliente 
                            }
                            break;
                        case 3: // quantidade => com base na quantidade disponivel 
                            int quant = objetoRemoto.exibirQuantidadeCarros();
                            System.out.println("\nQuantidade de carros disponívies = " + quant);
                            break;
                        case 4: // comprar carro
                            System.out.println("\nDigite o nome do carro que deseja comprar:");
                            String nomeCarro = System.console().readLine();
                            boolean comprado = objetoRemoto.comprarCarro(nomeCarro);
                            if(comprado == true){ // testa se tem e se comprou
                                System.out.println("Carro " + nomeCarro + " comprado com sucesso, Parabéns!");
                            }else{
                                System.out.println("Carro " + nomeCarro + " não encontrado no nosso sistema!");
                            }
                            break;
                        case 0:
                            //terminou salva em outro arquivo
                            objetoRemoto.escreverCarrosEmArquivo("D:/Users/vitor/git/Car-Store-Client-Server/CarStore/src/novosCarros.txt");
                            System.out.println("\nObrigado por utilizar a loja de carros!");
                            break;
                        default:
                            System.out.println("\nOpção inválida. Tente novamente.");
                            break;
                    }
    
                } while(opcao != 0);
    
            } // metodos para funcionario => todos
            if(user instanceof Funcionario){
                System.out.println("Bem vindo, "+ login);

                int opcao = 0;
                do {
                    System.out.println("\nEscolha uma opção:");
                    System.out.println("1 - Adiconar carro");
                    System.out.println("2 - Apagar carro");
                    System.out.println("3 - Listar carros disponiveis");
                    System.out.println("4 - Pesquisar carro por nome ou renavan");
                    System.out.println("5 - Alterar atributos de um carro");
                    System.out.println("6 - Exibir quantidade de carros disponíveis");
                    System.out.println("7 - Comprar carro");
                    System.out.println("0 - Sair");

                    opcao = Integer.parseInt(System.console().readLine());

                    Scanner scanner = new Scanner(System.in);
                    switch(opcao) {
                        case 1: // adicionar carro
                            System.out.println("\nDigite o renavan do carro que deseja adicionar:");
                            String renavan = System.console().readLine();

                            System.out.println("\nDigite o nome do carro que deseja adicionar:");
                            String nome = System.console().readLine();

                            System.out.println("\nDigite a categoria do carro que deseja adicionar:");
                            String categoria = System.console().readLine();

                            System.out.println("\nDigite o ano do carro que deseja adicionar:");
                            int ano = scanner.nextInt();
                            
                            System.out.println("\nDigite o preço do carro que deseja adicionar:");
                            double preco = scanner.nextDouble();
                            
                            CarroImpl carroAdicionado =  objetoRemoto.adicionarCarro(renavan, nome, categoria, ano, preco);
                            System.out.println("\nCarro adicionado = " + carroAdicionado.toString()); // imprimindo pro cliente 
                            break;
                        case 2: // apagar carro
                            System.out.println("\nDigite o nome do carro que deseja apagar:");
                            String apagado = System.console().readLine();
                            CarroImpl carroRemovido = objetoRemoto.apagarCarro(apagado);

                            // testa se foi removido mesmo => se não foi retorna null
                            if (carroRemovido instanceof CarroImpl){
                                System.out.println("\nCarro removido = " + carroRemovido.toString()); // imprimindo pro cliente 
                            }else{
                                System.out.println("\nCarro não encontrado/removido"); // imprimindo pro cliente 
                            }
                            break;
                        case 3:  // listar (mesmo do cliente)
                            System.out.println("\nDigite 0 para listar por categoria ou digite 1 " +
                            "para listar todos em ordem alfabética");
                            
                            int chaveListar = scanner.nextInt();
                            List<Carro> listaRetornada = new ArrayList<>();
                            listaRetornada = objetoRemoto.listarCarros(chaveListar);
                            System.out.println("");

                            for (Carro carro : listaRetornada) { // todos os atributos
                                System.out.println("Nome = " + carro.getNome() + ", Renavan = " + carro.getRenavan() + 
                                ", Categoria = " + carro.getCategoria() + ", Ano = " + carro.getAnoFabricacao() + 
                                ", Preço = " + carro.getPreco() + ", Quantidade disponível = " + carro.getQuantidadeDisponivel());
                            }
                            break;
                        case 4: // pesquisar carro (mesmo do cliente)
                            System.out.println("\nDigite o nome ou o renavan do carro que deseja pesquisar:");
                            String chave = System.console().readLine();
                            CarroImpl carroPesquisadoFuncionario = objetoRemoto.pesquisarCarro(chave);

                            // testa se foi achadp mesmo => se não foi retorna null
                            if (carroPesquisadoFuncionario instanceof CarroImpl){
                                System.out.println("\nCarro achado = " + carroPesquisadoFuncionario.toString()); // imprimindo pro cliente 
                            }else{
                                System.out.println("\nCarro não encontrado"); // imprimindo pro cliente 
                            }
                            break;
                        case 5: // alterar carro
                            System.out.println("\nDigite o nome ou o renavan do carro que deseja pesquisar:");
                            String chaveAlterar = System.console().readLine();
                            CarroImpl carroAletarado = objetoRemoto.pesquisarCarro(chaveAlterar);

                            if (carroAletarado instanceof CarroImpl) // se for um carro => pode alterar
                            {
                                System.out.println("\nAltere os atributos do carro pesquisado: ");

                                System.out.println("\nDigite o novo renavan do carro:");
                                String renavanAlte = System.console().readLine();
        
                                System.out.println("\nDigite o novo nome do carro:");
                                String nomeAlte = System.console().readLine();
        
                                System.out.println("\nDigite a nova categoria do carro:");
                                String categoriaAlte = System.console().readLine();
        
                                System.out.println("\nDigite o novo ano do carro:");
                                int anoAlte = scanner.nextInt();
                                
                                System.out.println("\nDigite o novo preço do carro:");
                                double precoAlte = scanner.nextDouble();

                                System.out.println("\nDigite a nova quantidade disponivel:");
                                int qauntAlte = scanner.nextInt();

                                CarroImpl carroAlterReturn = objetoRemoto.alterarAtributos(chaveAlterar, renavanAlte, nomeAlte, categoriaAlte,anoAlte, precoAlte, qauntAlte);
                                if(carroAlterReturn instanceof CarroImpl){
                                    System.out.println("\nCarro alterado = " + carroAlterReturn.toString()); // imprimindo pro cliente 
                                }
                            else {
                                System.out.println("Carro pesquisado não encontrado");
                            }
                        }
                        break;
                        case 6: // quantidade de carros (mesmo de cliente)
                            int quant = objetoRemoto.exibirQuantidadeCarros();
                            System.out.println("\nQuantidade de carros disponívies = " + quant);
                            break;
                        case 7: // comprar carro (mesmo de cliente)
                            System.out.println("\nDigite o nome do carro que deseja comprar:");
                            String nomeCarro = System.console().readLine();
                            boolean comprado = objetoRemoto.comprarCarro(nomeCarro);
                           
                            if(comprado == true){
                                System.out.println("Carro " + nomeCarro + " comprado com sucesso, Parabéns!");
                            }else{
                                System.out.println("Carro " + nomeCarro + " não encontrado no nosso sistema!");
                            }
                            break;
                        case 0:
                        // salva em arquivo diferente
                            objetoRemoto.escreverCarrosEmArquivo("D:/Users/vitor/git/Car-Store-Client-Server/CarStore/src/novosCarros.txt");
                            System.out.println("\nObrigado por utilizar a loja de carros!");
                            break;
                        default:
                            System.out.println("\nOpção inválida. Tente novamente.");
                            break;
                    }
                } while(opcao != 0);
            } 
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
