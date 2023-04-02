package teste2;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import teste2.interfaces.Loja;

public class Cliente {
    public static void main(String[] args) {
        try {
            Registry registro = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
            Loja loja = (Loja) registro.lookup("Loja");

            System.out.println("Bem-vindo à loja de carros!");

            int opcao = 0;
            do {
                System.out.println("\nEscolha uma opção:");
                System.out.println("1 - Listar carros disponiveis");
                System.out.println("2 - Pesquisar carro por nome");
                System.out.println("3 - Exibir quantidade de carros disponíveis");
                System.out.println("4 - Comprar carro");
                System.out.println("0 - Sair");

                opcao = Integer.parseInt(System.console().readLine());

                switch(opcao) {
                    case 1:
                        System.out.println("\nDigite a categoria dos carros que deseja listar:");
                        String categoria = System.console().readLine();
                        loja.listarCarros();
                        break;
                    case 2:
                        System.out.println("\nDigite o nome do carro que deseja pesquisar:");
                        String chave = System.console().readLine();
                        loja.pesquisarCarro(chave);
                        break;
                    case 3:
                        loja.exibirQuantidadeCarros();
                        break;
                    case 4:
                        System.out.println("\nDigite o nome do carro que deseja comprar:");
                        String nomeCarro = System.console().readLine();
                        System.out.println("\nDigite a quantidade que deseja comprar:");
                        int quantidade = Integer.parseInt(System.console().readLine());
                        loja.comprarCarro(nomeCarro, quantidade);
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
