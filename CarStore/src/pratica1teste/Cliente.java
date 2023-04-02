package pratica1teste;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {
    
    private Cliente() {}
    
    public static void main(String[] args) {
    
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("Informe o nome/endereço do RMIRegistry:");
        String host = teclado.nextLine();
        
        try {
        
            /* Este cliente primeiro obtém a referência para o registro invocando 
            * o método static LocateRegistry.getRegistry com o nome do host. 
            */
            Registry registro = LocateRegistry.getRegistry(host);
            
            /* 
            * O cliente chama o método lookup (busca) do método remoto no stub do RMIregistry para 
            * obter um stub para o objeto remoto no RMIRegistry. 
            */
            
            Hello stubObjRemotoCliente = (Hello) registro.lookup("Hello");
            
            /* O cliente invoca o método oi() no stub do objeto remoto, provocando as seguintes ações 
            * 
            * Lado do cliente abre uma conexão com o servidor usando as informações de host e porta 
            * no stub do objeto remoto e, em seguida, serializa os dados da chamada.
            * 
            * Lado do servidor aceita a chamada recebida, despacha a chamada para o objeto remoto e 
            * serializa o resultado (a string "Oi, RMI!") para o cliente.
            * 
            * Lado do cliente recebe, desserializa e retorna o resultado para quem fez a chamada.
            * */
            
            String resposta = stubObjRemotoCliente.oi();
            
            System.out.println("Chamando método oi(): " + resposta);
            
            teclado.close();
        
        } catch (Exception e) {
            System.err.println("Cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}
