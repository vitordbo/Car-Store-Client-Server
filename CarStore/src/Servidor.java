import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import implementacoes.LojaImpl;
import interfaces.Loja;

public class Servidor {
    public static void main(String args[]) {
 
        try {
            //criar objeto servidor
            LojaImpl refObjetoRemoto = new LojaImpl();
            
            Loja skeleton = (Loja) UnicastRemoteObject.exportObject(refObjetoRemoto, 0);

            LocateRegistry.createRegistry(20003); 
            
            Registry registro = LocateRegistry.getRegistry(20003);
            
            /* O método bind é então chamado no stub do registro para vincular
            * o stub do objeto remoto ao nome "Hello" no registro.*/
            registro.bind("Loja", skeleton);
            System.err.println("Servidor pronto:");

        } catch (Exception e) {
            System.err.println("Servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}

    
