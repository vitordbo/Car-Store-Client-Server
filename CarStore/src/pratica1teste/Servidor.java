package pratica1teste;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/* 
 * Servidor cria instância da implementação 
 * do objeto remoto, exporta-o e 
 * vincula essa instância a um nome no 
 * RMI Registry. O objeto remoto pode ser 
 * implementado no próprio servidor ou em 
 * alguma outra classe 
 * 
*/

public class Servidor implements Hello {
 
    public Servidor() {}
        // implementação do método oi()
        public String oi() {
        return "Oi, RMI!";
    }
    
    public static void main(String args[]) {
        
        try {
            //criar objeto servidor
            Servidor refObjetoRemoto = new Servidor();
            
            // cria um objeto stub do servidor

            /* O método estático UnicastRemoteObject.exportObject exporta o objeto remoto 
            * fornecido para receber invocações de método remoto em uma porta 
            * TCP anônima e retorna o stub para o objeto remoto para passar aos clientes. 
            * 
            * Como resultado da chamada de exportObject, o RMI pode começar a escutar em um 
            * novo server socket ou pode usar um server socket compartilhado para aceitar 
            * chamadas remotas de entrada para o objeto remoto. 
            * 
            * RemoteException em caso de falha*/
            
            Hello skeleton = (Hello) UnicastRemoteObject.exportObject(refObjetoRemoto, 0);
            
            // Acionar RMIRegistry na porta padrão(1099) 
            /* 
            * Sem essa linha é preciso lançar o RMiRegistry via linha de comando
            */
            LocateRegistry.createRegistry( Registry.REGISTRY_PORT ); 

            // associa o stub do servidor (objeto remoto) no rmiregistry
            
            /* O método estático LocateRegistry.getRegistry, que não recebe 
            * nenhum argumento, retorna um stub que implementa a interface 
            * remota java.rmi.registry.Registry e envia invocações para o 
            * registro (rmiregistry) no host do servidor na porta de registro padrão de 1099. 
            */

            Registry registro = LocateRegistry.getRegistry();
            /* O método bind é então chamado no stub do registro para vincular 
            * o stub do objeto remoto ao nome "Hello" no registro.*/

            registro.bind("Hello", skeleton);
            System.err.println("Servidor pronto:");
           
        } catch (Exception e) {
            System.err.println("Servidor: " + e.toString());
            e.printStackTrace();
        }
        }
}