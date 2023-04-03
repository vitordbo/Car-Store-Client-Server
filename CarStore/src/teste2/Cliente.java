package teste2;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import teste2.imple.CarroImpl;
import teste2.interfaces.Loja;

public class Cliente {
    public static void main(String[] args) {
        try {            
            
            // localhost aq => up linux pc  = 192.168.1.16
            Registry registro = LocateRegistry.getRegistry("localhost", 20003); //Registry.REGISTRY_PORT
            Loja objetoRemoto = (Loja) registro.lookup("Loja");

            // verifica se o objeto registro foi encontrado corretamente
            if (objetoRemoto == null) {
                System.out.println("Não foi possível conectar ao servidor.");
                System.exit(1);
            }

            System.out.println("Bem-vindo à loja de carros!");

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
                    case 1: // adicionar carro => ok
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

                        objetoRemoto.adicionarCarro(renavan, nome, categoria, ano, preco);
                        break;
                    case 2: // ok
                        System.out.println("\nDigite o nome do carro que deseja apagar:");
                        String apagado = System.console().readLine();
                        objetoRemoto.apagarCarro(apagado);
                        break;
                    case 3:  // ok
                        objetoRemoto.listarCarros();
                        break;
                    case 4: //ok
                        System.out.println("\nDigite o nome ou o renavan do carro que deseja pesquisar:");
                        String chave = System.console().readLine();
                        objetoRemoto.pesquisarCarro(chave);
                        break;
                    case 5:
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

                            objetoRemoto.alterarAtributos(chaveAlterar, renavanAlte, nomeAlte, categoriaAlte,anoAlte, precoAlte, qauntAlte);
                        }
                        else {
                            System.out.println("Carro pesquisado não encontrado");
                        }
                    break;
                    case 6:
                        objetoRemoto.exibirQuantidadeCarros();
                        break;
                    case 7:
                        System.out.println("\nDigite o nome do carro que deseja comprar:");
                        String nomeCarro = System.console().readLine();
                        objetoRemoto.comprarCarro(nomeCarro);
                        break;
                    case 0:
                        System.out.println("\nObrigado por utilizar a loja de carros!");
                        break;
                    default:
                        System.out.println("\nOpção inválida. Tente novamente.");
                        break;
                }

            } while(opcao != 0);

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
