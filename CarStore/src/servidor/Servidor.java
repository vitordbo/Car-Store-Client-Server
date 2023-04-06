package servidor;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import implementacoes.LojaImpl;
import interfaces.Loja;

public class Servidor {
    public static void main(String args[]) {
 
        // pratica 3
        try {
            //criar objeto servidor
            LojaImpl refObjetoRemoto = new LojaImpl("D:/Users/vitor/git/Car-Store-Client-Server/CarStore/src/carros.txt");
            
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

    
